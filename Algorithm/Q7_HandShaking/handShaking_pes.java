import java.util.Scanner;

public class handShaking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int n = scan.nextInt();
		
		System.out.println(getHandShaking(n));
	}
	
	public static long getHandShaking(int n) { // memoization을 사용하여 아래를 다시 구현해라! HW!!!!!!!!! 10/29 10AM 까지.
		int k = n / 2;
		
		if (n == 0 || n == 2) return 1; // H0은 없으나 H0을 1로 만듦면 규칙이 성립한다.

		long count = 0;
		for (int i = 0; i < k; i++) {
			count += getHandShaking(2 * i) * getHandShaking(2 * (k - 1 - i));
		}
		
		return count; 
	}

}