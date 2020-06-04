import java.util.Scanner;

public class Practice09 {

	static final String sigFilename = "signing.txt";
	static final String privatefname = "privateKey.txt";
	static final String publicfname = "publicKey.txt";

	static final String datafname = "data.txt";

	public static void main(String[] args) {
		System.out.println("[Practice09] Digital Signature\n");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		// 발신자
		System.out.println("- Sender");
		System.out.print("Send data: ");
		String data = scanner.nextLine();

		// data 저장
		byte[] b = data.getBytes();
		DigitSign.saveFile(datafname, b);

		// key 생성
		DigitSign.createAndSaveKeys();

		// 발신자의 private key로 전자 서명 진행
		DigitSign.sign(datafname, privatefname);

		// 수신자
		System.out.println("\n- Receiver");
		// 발신자의 public key로 검증
		boolean isTrue = DigitSign.verify(datafname, sigFilename, publicfname);

		// 위조 여부
		System.out.print("Is it from the real sender? ");
		System.out.println(isTrue + "!");

		if (isTrue) {
			System.out.println("\n[Received Data]");
			printData(datafname);
		}
	}

	public static void printData(String fname) {
		byte[] data = DigitSign.readFile(fname);
		System.out.println(new String(data));
	}
}