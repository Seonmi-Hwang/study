// 20170995 컴퓨터학과 4학년 황선미
/* Practice07에서 사용하기 위한 클래스 */

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
