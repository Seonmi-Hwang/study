import java.util.Scanner;

public class Practice10 {

//   static final String datafname = "data.txt";
   
   public static void main(String[] args) {
       System.out.println("[Practice09] Digital Signature\n");

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
         
//        �߽���
        System.out.println("- Sender");
        System.out.print("Send data: ");
        String data = scanner.nextLine();
        
        // data ����
        byte[] b = data.getBytes();
        DigitSign.saveFile(DigitSign.datafname, b);
         
        // key ����
        DigitSign.createAndSaveKeys();
         
        // �߽����� private key�� sign
        DigitSign.sign(DigitSign.datafname, DigitSign.sender_privatefname);
        
        // �߰�!!!!!!
//        dataFilename, String sigFilename, String publicFilename, String r_publicFilename, String secretFilename
        DigitSign.signWithEnvelope(DigitSign.datafname, DigitSign.sigfname, DigitSign.sender_publicfname, DigitSign.receiver_publicfname, DigitSign.sender_secretfname);
        
//        String envelope_dataFilename, String envelope_sigFilename, String envelope_publicFilename, String receiver_privateFilename, String envelopementFilename
        boolean envTrue = DigitSign.verifyEnvelope(DigitSign.envelope_datafname, DigitSign.envelope_sigfname, DigitSign.envelope_publicfname, DigitSign.receiver_privatefname, DigitSign.envelopefname);
        System.out.println("verifyEnvelope ���: " + envTrue);

//        ������
        System.out.println("\n- Receiver");
//        // �߽����� public key�� verify
        boolean sigTrue = DigitSign.verify(DigitSign.datafname, DigitSign.sigfname, DigitSign.sender_publicfname);
        
       // ���� ����
        System.out.print("Is it from the real sender? ");
        System.out.println(sigTrue + "!");
        
        if (envTrue) {
           System.out.println("\n[Received Data]");
           printData(DigitSign.datafname);
        }
      }
   
   public static void printData(String fname) {
      byte[] data = DigitSign.readFile(fname);
      
      System.out.println(new String(data));
   }

}