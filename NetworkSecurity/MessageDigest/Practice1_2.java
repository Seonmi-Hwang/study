// 20170995 컴퓨터학과 4학년 황선미
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Practice1_2 { 

	public static void main(String[] args) { 
		// TODO Auto-generated method stub
		String data = "This is a message to be digested using MD5."; 
		
		getHashValue(data);
		data = "this is a message to be digested using MD5."; // 대문자를 소문자로
		getHashValue(data);
		data = "This is a message to be digested using MD5"; // 마침표를 생략
		getHashValue(data);
		data = "This is a massage to be digested using MD5."; // message -> massage
		getHashValue(data);
	}
	
	public static void getHashValue(String data) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(data.getBytes());
			byte[] messageDigestMD5 = messageDigest.digest();

			System.out.println("length of digest (bytes): " + messageDigestMD5.length);

			System.out.println("data: " + data);
			System.out.println("digestedMD5(hex): ");
			for (byte bytes : messageDigestMD5) {
				System.out.print(String.format("%02x", bytes) + "\t");
			}
			System.out.println();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	


}
