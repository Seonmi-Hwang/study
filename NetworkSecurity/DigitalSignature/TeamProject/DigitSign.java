import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Scanner;

public class DigitSign {
	public static final String signAlgorithm = "SHA1withRSA";
	
	static final String sigFilename = "signing.txt";
	static final String keyFilename = "keyFilename.txt";
	static MyKeyPair myKeyPair;
	
	public static void main(String[] args) {
		System.out.println("[Practice09] Digital Signature\n");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Input data file name: ");
		String dataFilename = scanner.next();
		
		createAndSaveKeys();
		
		// call function
		sign(dataFilename, keyFilename);
		verify(dataFilename, sigFilename, keyFilename);
		
	}
	
	static void createAndSaveKeys() {
		// create KeyPair by RSA
		try {
			myKeyPair = MyKeyPair.getInstance(1024);
			myKeyPair.createKeys();	
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		myKeyPair.saveKey(myKeyPair.getKeyPair(), keyFilename);
	}
	
	static void sign(String dataFilename, String keyFilename) {
		
		try {
			byte[] data = readFile(dataFilename); // sample.txt
			KeyPair keyPair = myKeyPair.restoreKey(keyFilename);
			Signature sig = Signature.getInstance(signAlgorithm); 
			
			// Signing
			sig.initSign(keyPair.getPrivate());
			sig.update(data);
			
			byte[] signature = sig.sign();
			saveFile(sigFilename, signature); // signing.txt
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	}
	
	static void verify(String dataFilename, String sigFilename, String keyFilename) {
		
		try {
			byte[] data = readFile(dataFilename); // sample.txt
			KeyPair keyPair = myKeyPair.restoreKey(keyFilename);
			Signature sig = Signature.getInstance(signAlgorithm); 
			
			// Verifying		
			sig.initVerify(keyPair.getPublic());
			sig.update(data);
			
			boolean rslt = sig.verify(readFile(sigFilename));		
			
			// print
			System.out.println("Verification : " + rslt);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] readFile(String fname) {
		byte[] data = new byte[128];
		
		try {
			FileInputStream fis = new FileInputStream(fname);
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
