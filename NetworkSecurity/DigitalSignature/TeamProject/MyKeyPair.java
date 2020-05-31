import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MyKeyPair {
	private static final String keyAlgorithm = "RSA";
//	private static final String publicFilename = "publicFilename.txt";
//	private static final String privateFilename = "privateFilename.txt";
//	private static final String keyFilename = "keyFilename.txt";
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public static MyKeyPair getInstance(int keylength) 
			throws NoSuchAlgorithmException, NoSuchProviderException{
		MyKeyPair rslt = new MyKeyPair();
		
		rslt.keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
		rslt.keyGen.initialize(keylength);
		
		return rslt;
	}
	
	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}
	
	public KeyPair getKeyPair() {
		return this.pair;
	}
	
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}
	
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
	
	public void saveKey(KeyPair keyPair, String fname) {
		
		try(FileOutputStream fstream = new FileOutputStream(fname)){
			try(ObjectOutputStream ostream = new ObjectOutputStream(fstream)){
				ostream.writeObject(keyPair);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public KeyPair restoreKey(String fname) {
		KeyPair keyPair = null;
		try (FileInputStream fis = new FileInputStream(fname)) {
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			keyPair = (KeyPair) obj;
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyPair;
	}
	


}
