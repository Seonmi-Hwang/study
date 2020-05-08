// 20170995 컴퓨터학과 4학년 황선미

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
