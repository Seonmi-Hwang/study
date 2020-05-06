// 20170995 ��ǻ���а� 4�г� Ȳ����
/* Practice07���� ����ϱ� ���� Ŭ���� */

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
	
//	private PrivateKey privateKey;
//	private PublicKey publicKey;
	
	public static MyKeyPair getInstance(int keylength) 
			throws NoSuchAlgorithmException, NoSuchProviderException {
		MyKeyPair rslt = new MyKeyPair();
		
		rslt.keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
		rslt.keyGen.initialize(keylength);
		
		return rslt;
	}
	
	public KeyPair createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		return pair;
	}
	
	public PrivateKey getPrivateKey() {
		return pair.getPrivate();
	}
	
	public PublicKey getPublicKey() {
		return pair.getPublic();
	}
}
