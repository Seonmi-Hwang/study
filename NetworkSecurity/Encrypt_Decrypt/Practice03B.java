// 20170995 컴퓨터학과 4학년 황선미

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

public class Practice03B {

	final static String keyAlgorithm = "RSA";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyPair pair = generateKeyPair();
		Cipher cipher = getCipher();
		
		String plainTxt = "Software Security of Dongduk";
		byte[] input = plainTxt.getBytes();
		
		byte[] cipherText = encryptData(pair, cipher, input);	
		String decVal = decryptData(pair, cipher, cipherText);
		
		System.out.println("Length of Input: " + input.length);
		System.out.println("Length of Encrypted Data: " + cipherText.length);
		
		System.out.print("[Encrypted Data]");
		for (int i = 0; i < cipherText.length; i++) {
			if (i % 15 == 0) { System.out.println(); }
			System.out.print(String.format("%02x", cipherText[i]) + "\t");
		}
		System.out.println();
	
		System.out.println("[Decrypted Data]");
		System.out.println(decVal); // byte 배열을 문자열로 변환 후 출력
	}
	
	public static Cipher getCipher() {
		// 5. Creating a Cipher object
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipher;
	}
	
	public static KeyPair generateKeyPair() {
		final int keysize = 2048; 
		KeyPair pair = null;
		try {
			// 1. Creating KeyPair generator object
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
	
			// 2. Initializing the KeyPairGenerator
			keyPairGen.initialize(keysize);
		
			// 3. Generate the pair of keys
			pair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pair;
	}
	
	/* Encrypting Data */
	public static byte[] encryptData(KeyPair pair, Cipher cipher, byte[] input) {
		byte[] cipherText = null;
		try {
			// 4. Getting the private key from the key pair
			PrivateKey privateKey = pair.getPrivate(); 
			
			// 6. Initializing a Cipher object
			cipher.init(Cipher.ENCRYPT_MODE, privateKey); // Private Key로 암호화
				
			// 7. Adding data to the cipher
			cipher.update(input);
			
			// 8. Encrypting the data
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
	public static String decryptData(KeyPair pair, Cipher cipher, byte[] cipherText) {
		byte[] decipheredText = null;
		try {
			// 9. Get the public key
			PublicKey publicKey = pair.getPublic();
			
			// 9. Initializing the same cipher for decryption
			cipher.init(Cipher.DECRYPT_MODE, publicKey); // Public Key로 복호화
			
			// 10. Decrypt the data
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
