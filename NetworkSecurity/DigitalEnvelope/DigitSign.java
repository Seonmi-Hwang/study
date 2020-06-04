import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DigitSign {
	public static final String signAlgorithm = "SHA1withRSA";

	// sender
	static final String datafname = "data.txt";
	static final String sigfname = "signing.txt";
	static final String sender_privatefname = "privateKey.txt";
	static final String sender_publicfname = "publicKey.txt";

	// sender secret key
	static final String sender_secretfname = "secretKey.txt";

	// receiver의 public private key
	static final String receiver_privatefname = "receiver_privateKey.txt";
	static final String receiver_publicfname = "receiver_publicKey.txt";

	// 암호문
	static final String envelope_sigfname = "envelope_signing.txt";
	static final String envelope_publicfname = "envelope_publicKey.txt";
	static final String envelope_datafname = "envelope_datafname.txt";

	// 암호문 복구하여 저장
	static final String restore_sigfname = "restore_signing.txt";
	static final String restore_publicfname = "restore_publicKey.txt";
	static final String restore_datafname = "restore_datafname.txt";

	// 전자봉투
	static final String envelopefname = "envelopment.txt"; // saveFile
	static final String decrypted_envelopefname = "decrypted_envelopement.txt"; // saveFile

	static MyKeyPair myKeyPair;
	static MySecretKey mySecretKey;

	// 발신자와 수신자의 public/private key & secret key 생성
	static void createAndSaveKeys() {
		
		try {
			myKeyPair = MyKeyPair.getInstance(2048);
			mySecretKey = new MySecretKey();

			// 발신자의  public/private key 생성
			myKeyPair.createKeys();
			myKeyPair.savePublicKey(myKeyPair.getPublicKey(), sender_publicfname);
			myKeyPair.savePrivateKey(myKeyPair.getPrivateKey(), sender_privatefname);

			// 수신자의  public/private key 생성
			myKeyPair.createKeys();
			myKeyPair.savePublicKey(myKeyPair.getPublicKey(), receiver_publicfname);
			myKeyPair.savePrivateKey(myKeyPair.getPrivateKey(), receiver_privatefname);

			// secret key 생성
			mySecretKey.saveSecretKey(mySecretKey.generateKey(), sender_secretfname); 

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* 
	 * 전자봉투 생성 메서드
	 * 수신자의 public key로 secret key를 암호화 -> file로 저장(saveFile)
	 *  */
	static void encryptEnvelope(String secretFilename, String receiver_publicFilename) { 
		Key secretKey = mySecretKey.restoreSecretKey(secretFilename);
		PublicKey publicKey = myKeyPair.restorePublicKey(receiver_publicFilename);

		Cipher cipher = getCipher();

		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey); // Public Key로 암호화
			cipher.update(serializeKey(secretKey)); // secret key를 직렬화한 후 암호화
			saveFile(envelopefname, cipher.doFinal()); // 만들어진 전자봉투를 파일에 저장
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	/* 
	 * 전자봉투 해체 메서드 
	 * 수신자의 private key로 secret key를 복호화 -> secret key 반환
	 * */
	static Key decryptEnvelop(String envelopeFilename, String receiver_privateFilename) {
		PrivateKey privateKey = myKeyPair.restorePrivateKey(receiver_privateFilename);
		byte[] encryptedEnvelope = readFile(envelopeFilename);

		Cipher cipher = getCipher();

		Key secretKey = null;

		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey); // Private Key로 복호화
			secretKey = deserializeKey(cipher.doFinal(encryptedEnvelope)); // envelope를 복호화한 후 역직렬화하여 secret key를 가져옴
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return secretKey;
	}

	/* 
	 * 직렬화 메소드
	 * public / private / secret key를 직렬화 해줌  */
	static byte[] serializeKey(Key key) { // 

		byte[] serializeKey = null;

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(key);
				serializeKey = baos.toByteArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return serializeKey;
	}

	/* 
	 * 역직렬화 메소드
	 * public / private / secret key를 역직렬화 해줌  */
	static Key deserializeKey(byte[] byteKey) {

		Key key = null;

		try (ByteArrayInputStream bais = new ByteArrayInputStream(byteKey)) {
			try (ObjectInputStream ois = new ObjectInputStream(bais)) {
				Object obj = ois.readObject();
				key = (Key) obj;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return key;
	}

	public static Cipher getCipher() {
		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

		return cipher;
	}

	/* 
	 * 암호문 생성 메서드 
	 * 원문, 전자서명, 발신자의 public key를 secret key로 암호화 하고 파일로 저장 */
	static void createEncryption(String dataFilename, String sigFilename, String publicFilename,
			String secretFilename) {
		Key secretKey = mySecretKey.restoreSecretKey(secretFilename); // createAndSaveKeys()에서 생성하여 직렬화로 저장한 secret key를 가져옴

		byte[] data = readFile(dataFilename);
		byte[] signature = readFile(sigFilename);

		PublicKey publicKey = myKeyPair.restorePublicKey(publicFilename);
		byte[] bytePublicKey = serializeKey(publicKey); // PublicKey를 직렬화

		try {
			saveFile(envelope_datafname, mySecretKey.encrypt(data, secretKey));
			saveFile(envelope_sigfname, mySecretKey.encrypt(signature, secretKey));
			saveFile(envelope_publicfname, mySecretKey.encrypt(bytePublicKey, secretKey));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (암호문 + 전자봉투) 생성 메서드
	 * 암호문을 생성하는 createEncryption 메서드와
	 * 전자봉투를 생성하는 encryptEnvelope 메서드를 호출
	 * */
	static void signWithEnvelope(String dataFilename, String sigFilename, String publicFilename,
			String receiver_publicFilename, String secretFilename) {
		createEncryption(dataFilename, sigFilename, publicFilename, secretFilename); // 암호문 생성
		encryptEnvelope(secretFilename, receiver_publicFilename); // 전자봉투 생성
	}

	/*
	 * 전자봉투를 검증하는 메서드
	 * */
	static boolean verifyEnvelope(String envelope_dataFilename, String envelope_sigFilename,
			String envelope_publicFilename, String receiver_privateFilename, String envelopeFilename) { 

//	  1. 전자봉투를 'receiver의 privatekey'로 해체해 Secretkey획득
		Key secretKey = decryptEnvelop(envelopeFilename, receiver_privateFilename); // 전자봉투를 해체
		
//	  2. 저장된 암호문을 불러온다 
		byte[] data = readFile(envelope_dataFilename);
		byte[] signature = readFile(envelope_sigFilename);
		byte[] env_bytePublicKey = readFile(envelope_publicFilename);

		try {
//    3. 2에서 얻은 암호문을 '1에서 얻은 secretKey'를 이용해 해독 → 원본데이터/전자서명/sender의 PublicKey 획득 및 파일에 저장
			saveFile(restore_datafname, mySecretKey.decrypt(data, secretKey));
			saveFile(restore_sigfname, mySecretKey.decrypt(signature, secretKey));

			byte[] dec_bytePublicKey = mySecretKey.decrypt(env_bytePublicKey, secretKey);

//	  	PublicKey 객체로 변환
			PublicKey publicKey = (PublicKey) deserializeKey(dec_bytePublicKey);

			myKeyPair.savePublicKey(publicKey, restore_publicfname);
//    	  byte[] data = mySecretKey.decrypt(data, secretKey);
//    	  byte[] signature = mySecretKey.decrypt(signature, secretKey);

		} catch (Exception e) {
			e.printStackTrace();
		}

//    4. '2에서 얻은 원본데이터'를 해시한 값과 '전자서명을 "2에서 받은 sender의 PublicKey"로 해독한 해시 값'이 일치한 지 비교
		return verify(restore_datafname, restore_sigfname, restore_publicfname);

	}

	/* 전자서명 생성 메서드 */
	static void sign(String dataFilename, String keyFilename) { // private key file name

		try {
			byte[] data = readFile(dataFilename); // sample.txt
			PrivateKey privateKey = myKeyPair.restorePrivateKey(keyFilename);
			Signature sig = Signature.getInstance(signAlgorithm);

			// Signing
			sig.initSign(privateKey);
			sig.update(data);

			byte[] signature = sig.sign();
			saveFile(sigfname, signature); // signing.txt

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	static boolean verify(String dataFilename, String sigFilename, String keyFilename) { // public key file name

		boolean rslt = false;

		try {
			byte[] data = readFile(dataFilename); // sample.txt
			PublicKey publicKey = myKeyPair.restorePublicKey(keyFilename);
			Signature sig = Signature.getInstance(signAlgorithm);

			// Verifying
			sig.initVerify(publicKey);
			sig.update(data);

			rslt = sig.verify(readFile(sigFilename));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return rslt;
	}

	public static byte[] readFile(String fname) {
		byte[] data = null;

		try {
			File file = new File(fname);
			FileInputStream fis = new FileInputStream(file);
			data = fis.readAllBytes();
			fis.read(data);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	public static void saveFile(String fname, byte[] data) {
		if (data == null) {
			return;
		}

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(fname);
			fos.write(data);
			fos.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}