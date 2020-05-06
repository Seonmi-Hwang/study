// 20170995 컴퓨터학과 4학년 황선미

import java.security.Key;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Practice05 {
	private static final String ALGO = "AES";
	private static final byte[] keyValue = { 'T', 'h', 'e', 'B', 'e', 's', 't', 
											'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		System.out.print("메시지를 입력하세요: ");
		String message = scan.nextLine();
		
		try {
			Key skey = generateKey();
			byte[] encVal = encrypt(message, skey);
			String decVal = decrypt(encVal, skey);
			
			System.out.print("암호화 결과 : ");
			for (byte value : encVal) {
			 System.out.print(String.format("%02x", value) + "-");
			}
			System.out.println();
			System.out.print("복호화 결과 : " + decVal + "\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Generate a new encryption key.
	 * */
	public static Key generateKey() throws Exception {
		return (new SecretKeySpec(keyValue, ALGO));
	}
	
	/*
	 * Encrypt a string with AES algorithm.
	 * 
	 * @param data is a string
	 * @return the encrypted string
	 * */
	public static byte[] encrypt(String data, Key skey) throws Exception {
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, skey);
		byte[] encVal = c.doFinal(data.getBytes());
		return encVal;
	}
	
	/*
	 * Decrypt a string with AES algorithm.
	 * 
	 * @param encryptedData is a string
	 * @return the decrypted string
	 * */
	public static String decrypt(byte[] encryptedData, Key skey) throws Exception {
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, skey);
		byte[] decValue = c.doFinal(encryptedData);
		return (new String(decValue));
	}

}
