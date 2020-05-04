// 20170995 ��ǻ���а� 4�г� Ȳ����
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Practice1_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data = "This is a message to be digested using MD5.";
		
		int idx = 1;
		while (idx != 10) {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idx++;
		}
		
	}

}
