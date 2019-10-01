import java.util.Scanner;

public class contNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int[] items = { 1, -1 };
		int k = 0;
		int target;
		int[] bucket;
		
		target = scan.nextInt();
		
		while (sum(k) < target) // k가 4일 때 부터
			k++;
		
		while (true) {
			bucket = new int[k];
			if (solution(items, bucket, k, target))
				break;
			k++;
		}

	}
	
	public static int sum(int n) {
		return n * (n + 1) / 2; // n개의 숫자를 가지고 만들 수 있는 최대 수
	}
		
	public static boolean solution(int[] items, int[] bucket, int k, int target) { // 조합
		if (k == 0) {
			int sum = 0;
			for (int i = 0; i < bucket.length; i++)
				sum += bucket[i] * (i + 1);
			
			if (sum == target) {
				for (int i = 0; i < bucket.length; i++) {
					if (bucket[i] == 1)
						System.out.print("+");
					else
						System.out.print("-");
					System.out.print(i + 1);
				}
				System.out.println();
				return true;
			}
			return false;
		}
		
		int lastIndex = bucket.length - k - 1;
		boolean ret = false;
		for (int i = 0; i < items.length; i++) {
			bucket[lastIndex + 1] = items[i];
			ret = solution(items, bucket, k - 1, target) || ret; // 둘 중 하나만 true면 true ('or'을 의미)
		}
		return ret;
	}
}
