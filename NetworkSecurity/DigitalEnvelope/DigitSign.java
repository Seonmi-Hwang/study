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

	// receiver�� public private key
	static final String receiver_privatefname = "receiver_privateKey.txt";
	static final String receiver_publicfname = "receiver_publicKey.txt";

	// ��ȣ��
	static final String envelope_sigfname = "envelope_signing.txt";
	static final String envelope_publicfname = "envelope_publicKey.txt";
	static final String envelope_datafname = "envelope_datafname.txt";

	// ��ȣ�� �����Ͽ� ����
	static final String restore_sigfname = "restore_signing.txt";
	static final String restore_publicfname = "restore_publicKey.txt";
	static final String restore_datafname = "restore_datafname.txt";

	// ���ں���
	static final String envelopefname = "envelopment.txt"; // saveFile
	static final String decrypted_envelopefname = "decrypted_envelopement.txt"; // saveFile

	static MyKeyPair myKeyPair;
	static MySecretKey mySecretKey;

	// �߽��ڿ� �������� public/private key & secret key ����
	static void createAndSaveKeys() {
		
		try {
			myKeyPair = MyKeyPair.getInstance(2048);
			mySecretKey = new MySecretKey();

			// �߽�����  public/private key ����
			myKeyPair.createKeys();
			myKeyPair.savePublicKey(myKeyPair.getPublicKey(), sender_publicfname);
			myKeyPair.savePrivateKey(myKeyPair.getPrivateKey(), sender_privatefname);

			// ��������  public/private key ����
			myKeyPair.createKeys();
			myKeyPair.savePublicKey(myKeyPair.getPublicKey(), receiver_publicfname);
			myKeyPair.savePrivateKey(myKeyPair.getPrivateKey(), receiver_privatefname);

			// secret key ����
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
	 * ���ں��� ���� �޼���
	 * �������� public key�� secret key�� ��ȣȭ -> file�� ����(saveFile)
	 *  */
	static void encryptEnvelope(String secretFilename, String receiver_publicFilename) { 
		Key secretKey = mySecretKey.restoreSecretKey(secretFilename);
		PublicKey publicKey = myKeyPair.restorePublicKey(receiver_publicFilename);

		Cipher cipher = getCipher();

		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey); // Public Key�� ��ȣȭ
			cipher.update(serializeKey(secretKey)); // secret key�� ����ȭ�� �� ��ȣȭ
			saveFile(envelopefname, cipher.doFinal()); // ������� ���ں����� ���Ͽ� ����
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	/* 
	 * ���ں��� ��ü �޼��� 
	 * �������� private key�� secret key�� ��ȣȭ -> secret key ��ȯ
	 * */
	static Key decryptEnvelop(String envelopeFilename, String receiver_privateFilename) {
		PrivateKey privateKey = myKeyPair.restorePrivateKey(receiver_privateFilename);
		byte[] encryptedEnvelope = readFile(envelopeFilename);

		Cipher cipher = getCipher();

		Key secretKey = null;

		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey); // Private Key�� ��ȣȭ
			secretKey = deserializeKey(cipher.doFinal(encryptedEnvelope)); // envelope�� ��ȣȭ�� �� ������ȭ�Ͽ� secret key�� ������
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
	 * ����ȭ �޼ҵ�
	 * public / private / secret key�� ����ȭ ����  */
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
	 * ������ȭ �޼ҵ�
	 * public / private / secret key�� ������ȭ ����  */
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
	 * ��ȣ�� ���� �޼��� 
	 * ����, ���ڼ���, �߽����� public key�� secret key�� ��ȣȭ �ϰ� ���Ϸ� ���� */
	static void createEncryption(String dataFilename, String sigFilename, String publicFilename,
			String secretFilename) {
		Key secretKey = mySecretKey.restoreSecretKey(secretFilename); // createAndSaveKeys()���� �����Ͽ� ����ȭ�� ������ secret key�� ������

		byte[] data = readFile(dataFilename);
		byte[] signature = readFile(sigFilename);

		PublicKey publicKey = myKeyPair.restorePublicKey(publicFilename);
		byte[] bytePublicKey = serializeKey(publicKey); // PublicKey�� ����ȭ

		try {
			saveFile(envelope_datafname, mySecretKey.encrypt(data, secretKey));
			saveFile(envelope_sigfname, mySecretKey.encrypt(signature, secretKey));
			saveFile(envelope_publicfname, mySecretKey.encrypt(bytePublicKey, secretKey));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (��ȣ�� + ���ں���) ���� �޼���
	 * ��ȣ���� �����ϴ� createEncryption �޼����
	 * ���ں����� �����ϴ� encryptEnvelope �޼��带 ȣ��
	 * */
	static void signWithEnvelope(String dataFilename, String sigFilename, String publicFilename,
			String receiver_publicFilename, String secretFilename) {
		createEncryption(dataFilename, sigFilename, publicFilename, secretFilename); // ��ȣ�� ����
		encryptEnvelope(secretFilename, receiver_publicFilename); // ���ں��� ����
	}

	/*
	 * ���ں����� �����ϴ� �޼���
	 * */
	static boolean verifyEnvelope(String envelope_dataFilename, String envelope_sigFilename,
			String envelope_publicFilename, String receiver_privateFilename, String envelopeFilename) { 

//	  1. ���ں����� 'receiver�� privatekey'�� ��ü�� Secretkeyȹ��
		Key secretKey = decryptEnvelop(envelopeFilename, receiver_privateFilename); // ���ں����� ��ü
		
//	  2. ����� ��ȣ���� �ҷ��´� 
		byte[] data = readFile(envelope_dataFilename);
		byte[] signature = readFile(envelope_sigFilename);
		byte[] env_bytePublicKey = readFile(envelope_publicFilename);

		try {
//    3. 2���� ���� ��ȣ���� '1���� ���� secretKey'�� �̿��� �ص� �� ����������/���ڼ���/sender�� PublicKey ȹ�� �� ���Ͽ� ����
			saveFile(restore_datafname, mySecretKey.decrypt(data, secretKey));
			saveFile(restore_sigfname, mySecretKey.decrypt(signature, secretKey));

			byte[] dec_bytePublicKey = mySecretKey.decrypt(env_bytePublicKey, secretKey);

//	  	PublicKey ��ü�� ��ȯ
			PublicKey publicKey = (PublicKey) deserializeKey(dec_bytePublicKey);

			myKeyPair.savePublicKey(publicKey, restore_publicfname);
//    	  byte[] data = mySecretKey.decrypt(data, secretKey);
//    	  byte[] signature = mySecretKey.decrypt(signature, secretKey);

		} catch (Exception e) {
			e.printStackTrace();
		}

//    4. '2���� ���� ����������'�� �ؽ��� ���� '���ڼ����� "2���� ���� sender�� PublicKey"�� �ص��� �ؽ� ��'�� ��ġ�� �� ��
		return verify(restore_datafname, restore_sigfname, restore_publicfname);

	}

	/* ���ڼ��� ���� �޼��� */
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