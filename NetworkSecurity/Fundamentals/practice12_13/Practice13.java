// 20170995 ��ǻ���а� 4�г� Ȳ����

package practice12_13;

public class Practice13 {
	public static void main(String[] args) {
		MyInterface hello = new MyInterface() {
			public void calculate(int x, int y) {
				System.out.println("���� : " + (x + y));
				System.out.println("���� : " + (x - y));
				System.out.println("���� : " + (x * y));
				System.out.println("������ : " + (x / y));
			}
		}; // Ŭ���� ���� + ��ü����
		
		hello.calculate(10, 20);
	}
}
