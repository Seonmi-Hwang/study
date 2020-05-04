// 20170995 컴퓨터학과 4학년 황선미
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Practice2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String f1 = "C:\\work\\copy_ver_1.unknown";
		String f2 = "C:\\work\\copy_ver_2.unknown";
		String f3 = "C:\\work\\copy_ver_3.unknown";
		String f4 = "C:\\work\\copy_ver_4.unknown"; // Copied from copy_ver_1.unknown
		
		byte[] data1 = readFile(f1);
		byte[] data2 = readFile(f2);
		byte[] data3 = readFile(f3);
		byte[] data4 = readFile(f4);
		
		System.out.println("[checkIdentity]\t[compareMessageDigest]");
		
		/* 확인할 특성1 : 이클래스에 첨부된 파일 중 해시 값이 같은 파일이 있는가? */
		System.out.print(checkIdentity(f1, f2) + "\t\t");
		System.out.println(compareMessageDigest(data1, data2));
		
		System.out.print(checkIdentity(f1, f3) + "\t\t");
		System.out.println(compareMessageDigest(data1, data3));
		
		System.out.print(checkIdentity(f2, f3) + "\t\t");
		System.out.println(compareMessageDigest(data2, data3));

		System.out.println("--------------------------------------");
		
		/* 확인할 특성2 : (붙여 넣기를 이용하여 생성한) 다른 이름, 같은 내용의 파일은 같은 해쉬 값을 가지는가? */
		System.out.print(checkIdentity(f1, f4) + "\t\t");
		System.out.println(compareMessageDigest(data1, data4));
	}
	
	public static byte[] readFile(String fname) {
		FileInputStream fin = null;
		ByteArrayOutputStream bao = null;
		byte[] data1 = null;
		
		try {
			int bytesRead = 0;
			byte[] buff = new byte[1024];
			
			File file = new File(fname);
			fin = new FileInputStream(file);
			bao = new ByteArrayOutputStream();
			
			while ((bytesRead = fin.read(buff)) > 0) {
				bao.write(buff, 0, bytesRead);
			}
			data1 = bao.toByteArray();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());		
		} finally {
			try { 
				fin.close();
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data1;
	}
	
	public static boolean checkIdentity(String fname1, String fname2) {
		try {
			File file1 = new File(fname1); // 파일 객체 생성
			FileReader freader1 = new FileReader(file1); // 입력 스트림 생성
			
			File file2 = new File(fname2);
			FileReader freader2 = new FileReader(file2);
			
			int singleCh = 0;
			while ((singleCh = freader1.read()) != -1) {
				if (freader2.read() != (char)singleCh) {
					return false;
				}
			}
			freader1.close();
			freader2.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return true;
	}
	
	public static boolean compareMessageDigest(byte[] data1, byte[] data2) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(data1);
			byte[] mdData1 = messageDigest.digest();

			messageDigest.update(data2);
			byte[] mdData2 = messageDigest.digest();
				
//			System.out.println();
//			for (byte bytes : mdData1) {
//				System.out.print(String.format("%02x", bytes) + "\t");
//			}
//			System.out.println();
//			for (byte bytes : mdData2) {
//				System.out.print(String.format("%02x", bytes) + "\t");
//			}
//			System.out.println();
			
			for (int i = 0; i < mdData1.length; i++) {
				if (mdData1[i] != mdData2[i]) {
					return false;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return true;
	}

}
