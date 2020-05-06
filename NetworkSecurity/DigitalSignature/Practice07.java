// 20170995 컴퓨터학과 4학년 황선미

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;

public class Practice07 {

//	final static String keyAlgorithm = "RSA";
	final static String signAlgorithm = "SHA1withRSA";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname = "C:\\work\\sample_text_signing.txt";
		byte[] data = readFile(fname);
		
		KeyPair keyPair = getKeyPair();
		
		byte[] signature = signDocument(data, keyPair); // 서명
		System.out.println(verifySignature(data, signature, keyPair));
	}
	
	public static KeyPair getKeyPair() {
		final int keylength = 1024;
		KeyPair keyPair = null;
		
		try {
			keyPair = MyKeyPair.getInstance(keylength).createKeys();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return keyPair;
	}
	
	public static byte[] signDocument(byte[] data, KeyPair keyPair) {
		byte[] signature = null;
		
		try {
			Signature sig = Signature.getInstance(signAlgorithm);
			
			sig.initSign(keyPair.getPrivate());
			sig.update(data);
			
			signature = sig.sign();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signature;
	}
	
	public static boolean verifySignature(byte[] data, byte[] signature, KeyPair keyPair) {
		boolean rslt = false; // initialize
		
		try {
			Signature sig = Signature.getInstance(signAlgorithm);
			
			sig.initVerify(keyPair.getPublic());
			sig.update(data);
			
			rslt = sig.verify(signature);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rslt;
	}

	
	public static byte[] readFile(String fname) {
		FileInputStream fin = null;
		ByteArrayOutputStream bao = null;
		byte[] data = null;
		
		try {
			int bytesRead = 0;
			byte[] buff = new byte[1024];
			
			File file = new File(fname);
			fin = new FileInputStream(file);
			bao = new ByteArrayOutputStream();
			
			while ((bytesRead = fin.read(buff)) > 0) {
				bao.write(buff, 0, bytesRead);
			}
			data = bao.toByteArray();
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
		return data;
	}

}
