// Update 19.10.29 by Seonmi-Hwang
// 코딩 테스트에서 많이 나옴 (중복순열)
import java.util.Scanner;

public class changes {
	static int count = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int change = scan.nextInt();
		int[] items = {10, 50, 100, 500};
		int bucket_size = change / 10; // 왜 일까?
		int bucket[] = new int[bucket_size];
		solution(items, bucket, bucket_size, change);
		
		System.out.println(count);
	}
	
	static void solution(int[] items, int[] bucket, int k, int change) { // 중복조합
		int lastIndex = bucket.length - k - 1; // 가장 최근에 뽑힌 수가 저장된 위치 index
		
		// if (k == 0) { // 이 전에 이미 계산을 해야한다! k == 0 될때까지 하지 마시오. }
		
		int sum = 0;
		for (int i = 0; i <= lastIndex; i++)
			sum += bucket[i];
		
		if (sum == change) { count++; return;}
		else if (sum > change) { return; }
		
		for (int i = 0; i < items.length; i++) {
			if (bucket.length == k) { // 최초 실행
				bucket[0] = items[i];
				solution(items, bucket, k - 1, change);
			}
			else if (bucket[lastIndex] <= items[i]) {
				bucket[lastIndex + 1] = items[i];
				solution(items, bucket, k - 1, change);
			}
		}
	}
}
