// 20170995 ��ǻ���а� 4�г� Ȳ����

package practice12_13;

public class MyInterfaceImpl implements MyInterface {
	public void calculate(int x, int y) {
		System.out.println("���� : " + (x + y));
		System.out.println("���� : " + (x - y));
		System.out.println("���� : " + (x * y));
		System.out.println("������ : " + (x / y));
	}
}
