// Update 19.11.13 by Seonmi-Hwang
import java.util.Scanner;

public class synchronizing_clock {

	static int[] clocks = new int[16];
	static int min = 100000;
	static int[][] buttons = new int[10][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int[] items = {0, 1, 2, 3};
		int[] bucket = new int[10];
		
		int idx, num; 
		
		for (int i = 0; i < 10; i++) {
			idx = scan.nextInt();
			num = scan.nextInt();
			
			buttons[idx] = new int[num];
			
			for (int j = 0; j < num; j++)
				buttons[idx][j] = scan.nextInt();
		}
		
		for (int i = 0; i < 16; i++)
			clocks[i] = scan.nextInt();
		
		solution(items, bucket, 10);
		System.out.println(min);
	}
	
	public static void solution (int[] items, int[] bucket, int k) {
		if (k == 0) {
			int cnt = moveClock(bucket);
			
			if (cnt != -1 && cnt < min)
				min = cnt;
			return;
		}
		
		int lastIdx = bucket.length - k - 1;
		
		for (int i = 0; i < items.length; i++) {
			bucket[lastIdx + 1] = items[i];
			solution(items, bucket, k - 1);
		}
	}
	
	public static int moveClock(int[] bucket) {
		int cnt = 0;
		int[] tmpClocks = new int[16];
		
		for (int i = 0; i < clocks.length; i++) // clocks 배열 복사
			tmpClocks[i] = clocks[i];
		
		for (int i = 0; i < bucket.length; i++) { // button 0부터 9까지
			if (bucket[i] == 0) continue; // 버튼을 누르지 않았으면 확인하지 않기
			
			for (int j = 0; j < buttons[i].length; j++)  // buttons 0 ~ 9까지의 포함한 시계
				tmpClocks[buttons[i][j]] += bucket[i] * 3; // clock의 값
		}
		
		for (int i = 0; i < tmpClocks.length; i++)
			if (tmpClocks[i] % 12 != 0) // 12가 아닌 경우가 한 번이라도 나오면
				return -1;
		
		for (int i = 0; i < bucket.length; i++)
			cnt += bucket[i];	
		
		return cnt;
	}

}

