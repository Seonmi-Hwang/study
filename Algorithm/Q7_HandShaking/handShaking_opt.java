// Update 19.10.26 by Seonmi-Hwang
import java.util.Scanner;

public class handShaking_opt { // memoization 사용

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		long[] memo = new long[n];
		
		for (int i = 0; i < n; i++)  // memo 초기화
			memo[i] = 0;
		
		System.out.println(getHandShaking(n, memo));
	}

	public static long getHandShaking(int n, long[] memo) {
		int k = n / 2;
		
		if (n == 0 || n == 2) 
			return 1;

		long count = 0;
		for (int i = 0; i < k; i++) { // n / 2 - 1 번 반복
			if (memo[i] == 0)
				memo[i] = getHandShaking(2 * i, memo);
			
			if (memo[k - 1 - i] == 0)
				memo[k - 1 - i] = getHandShaking(2 * (k - 1 - i), memo);
			
			count += memo[i] * memo[k - 1 - i]; 
		}
		
		return count; 
	}
}
