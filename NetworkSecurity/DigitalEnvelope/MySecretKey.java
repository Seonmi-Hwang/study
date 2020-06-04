import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MySecretKey extends KeyHandler {
   private static final String ALGO = "AES";
   private static final byte[] keyValue = { 'T', 'h', 'e', 'B', 'e', 's', 't', 
                                 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
   
   public Key generateKey() throws Exception {
      return (new SecretKeySpec(keyValue, ALGO));
   }
   

   public byte[] encrypt(byte[] data, Key skey) throws Exception {
      Cipher c = Cipher.getInstance(ALGO);
      c.init(Cipher.ENCRYPT_MODE, skey);
      byte[] encVal = c.doFinal(data);
      return encVal;
   }

   public byte[] decrypt(byte[] encryptedData, Key skey) throws Exception {
      Cipher c = Cipher.getInstance(ALGO);
      c.init(Cipher.DECRYPT_MODE, skey);
      return c.doFinal(encryptedData);
   }
   
}