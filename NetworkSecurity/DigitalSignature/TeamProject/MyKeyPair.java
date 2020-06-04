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

	private KeyPairGenerator keyGen;
	private KeyPair pair;

	private PrivateKey privateKey;
	private PublicKey publicKey;

	public static MyKeyPair getInstance(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
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

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void savePublicKey(PublicKey publicKey, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(publicKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PublicKey restorePublicKey(String filename) {
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

	public void savePrivateKey(PrivateKey privateKey, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(privateKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PrivateKey restorePrivateKey(String filename) {
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
}