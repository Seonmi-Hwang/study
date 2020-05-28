// 20170995 컴퓨터학과 4학년 황선미

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Practice08 {

	final static String keyAlgorithm = "RSA"; 
	
	public static void main(String[] args) {
		// 직렬화로 저장될 파일 이름
		String publicfname = "RSA_publicKey.txt";
		String privatefname = "RSA_privateKey.txt";
		
		KeyPair pair = generateKeyPair();
		Cipher cipher = getCipher();
		
		// 암호화에 사용될 test text
		String testTxt = "Java Serialization";
		byte[] input = testTxt.getBytes();
		
		// Get publicKey, privateKey from KeyPair 
		PublicKey publicKey = pair.getPublic();
		PrivateKey privateKey = pair.getPrivate();
		
		System.out.println("<< Before Serialization >>");
		byte[] cipherText = encryptData(publicKey, cipher, input);
		String decVal = decryptData(privateKey, cipher, cipherText);
		printData(cipherText, decVal);
		
		// Serialization
		savePublicKey(publicKey, publicfname);
		savePrivateKey(privateKey, privatefname);

		// Deserialization		
		PublicKey restorePublicKey = restorePublicKey(publicfname);
		PrivateKey restorePrivateKey = restorePrivateKey(privatefname);
		
		System.out.println("<< After Serialization >>");
		cipherText = encryptData(restorePublicKey, cipher, input);
		decVal = decryptData(restorePrivateKey, cipher, cipherText);
		printData(cipherText, decVal);
		
		System.out.println("Is equal?");
		System.out.println("public key : " + publicKey.equals(restorePublicKey));
		System.out.println("private key : " + privateKey.equals(restorePrivateKey));
	}
	
	public static void printData(byte[] cipherText, String decVal) {
		System.out.print("[Encrypted Data]");
		for (int i = 0; i < cipherText.length; i++) {
			if (i % 15 == 0) { System.out.println(); }
			System.out.print(String.format("%02x", cipherText[i]) + "\t");
		}
		System.out.println("\n");
	
		System.out.println("[Decrypted Data]");
		System.out.println(decVal); // byte 배열을 문자열로 변환 후 출력
		System.out.println("\n");
	}
	
	public static void savePublicKey(PublicKey publicKey, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(publicKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PublicKey restorePublicKey(String filename) {
		PublicKey publicKey = null;
		try (FileInputStream fis = new FileInputStream(filename)) {
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {
				Object obj = ois.readObject();
				publicKey = (PublicKey) obj;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return publicKey;
	}
	
	public static void savePrivateKey(PrivateKey privateKey, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(privateKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PrivateKey restorePrivateKey(String filename) {
		PrivateKey privateKey = null;
		try (FileInputStream fis = new FileInputStream(filename)) {
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {
				Object obj = ois.readObject();
				privateKey = (PrivateKey) obj;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return privateKey;
	}
	
	public static KeyPair generateKeyPair() {
		final int keysize = 2048; 
		KeyPair pair = null;
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
			keyPairGen.initialize(keysize);
			pair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pair;
	}

	public static Cipher getCipher() {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return cipher;
	}
	
	/* Encrypting Data */
	public static byte[] encryptData(PublicKey publicKey, Cipher cipher, byte[] input) {
		byte[] cipherText = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey); // Public Key로 암호화
			cipher.update(input);
			cipherText = cipher.doFinal();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return cipherText;
	}
	
	/* Decrypting Data */
	public static String decryptData(PrivateKey privateKey, Cipher cipher, byte[] cipherText) {
		byte[] decipheredText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey); // Private Key로 복호화
			decipheredText = cipher.doFinal(cipherText);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return (new String(decipheredText));
	}
	
}
