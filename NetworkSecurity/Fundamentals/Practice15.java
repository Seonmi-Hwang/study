// 20170995 ��ǻ���а� 4�г� Ȳ����

public class Practice15 {

	public static void calcSum(int... is) {
		int total = 0;
		for (int i : is) {
			total += i;
		}
		System.out.println(total);
	}
	
	public static void main(String[] args) {
		calcSum(10, 20);
		calcSum(10, 20, 30);
		calcSum(10, 20, 30, 40, 50);
	}

}
