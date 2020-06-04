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

		// �߽���
		System.out.println("- Sender");
		System.out.print("Send data: ");
		String data = scanner.nextLine();

		// data ����
		byte[] b = data.getBytes();
		DigitSign.saveFile(datafname, b);

		// key ����
		DigitSign.createAndSaveKeys();

		// �߽����� private key�� ���� ���� ����
		DigitSign.sign(datafname, privatefname);

		// ������
		System.out.println("\n- Receiver");
		// �߽����� public key�� ����
		boolean isTrue = DigitSign.verify(datafname, sigFilename, publicfname);

		// ���� ����
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