// Update 19.10.29 by Seonmi-Hwang
// �ڵ� �׽�Ʈ���� ���� ���� (�ߺ�����)
import java.util.Scanner;

public class changes {
	static int count = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int change = scan.nextInt();
		int[] items = {10, 50, 100, 500};
		int bucket_size = change / 10; // �� �ϱ�?
		int bucket[] = new int[bucket_size];
		solution(items, bucket, bucket_size, change);
		
		System.out.println(count);
	}
	
	static void solution(int[] items, int[] bucket, int k, int change) { // �ߺ�����
		int lastIndex = bucket.length - k - 1; // ���� �ֱٿ� ���� ���� ����� ��ġ index
		
		// if (k == 0) { // �� ���� �̹� ����� �ؾ��Ѵ�! k == 0 �ɶ����� ���� ���ÿ�. }
		
		int sum = 0;
		for (int i = 0; i <= lastIndex; i++)
			sum += bucket[i];
		
		if (sum == change) { count++; return;}
		else if (sum > change) { return; }
		
		for (int i = 0; i < items.length; i++) {
			if (bucket.length == k) { // ���� ����
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
