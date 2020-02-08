import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bucket {

	static int[] arr;
	static int[] group;
	static int minError = Integer.MAX_VALUE;
	static int[][] memo;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // ����
		StringTokenizer st = new StringTokenizer(reader.readLine()); // String
		while (!st.hasMoreTokens())
			st = new StringTokenizer(reader.readLine());
		
		int n = Integer.parseInt(st.nextToken()); // �Ϲ� ������ ����
		int k = Integer.parseInt(st.nextToken()); // bucket�� ��
		
		arr = new int[n]; // �Ϲ� ���� �迭
		int[] bucket = new int[k]; // �� bucket�� �� ���� ���� ������ 
		
		for (int i = 0; i < arr.length; i++) {
			while (!st.hasMoreTokens())
				st = new StringTokenizer(reader.readLine());
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		memo = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				memo[i][j] = -1;
		
		solution(arr.length, bucket, k);
		
		System.out.println(minError);
	}
	
	public static void solution(int len, int[] bucket, int k) { // �ߺ�����
		if (k == 0) {
			if (len != 0) return;
			int curError = Integer.MAX_VALUE;

			curError = 0;
			int minIdx = 0;
			for (int i = 0 ; i < bucket.length; i++) { // 0 1 2
				int maxIdx = minIdx + bucket[i] - 1; // 2 3 5 
				if (memo[minIdx][maxIdx] == -1)
					memo[minIdx][maxIdx] = getError(bucket[i], minIdx);
					
				curError += memo[minIdx][maxIdx];
				minIdx += bucket[i];
			}
			
			
			if (curError < minError)
				minError = curError;
			
			return;
		}
		int lastIndex = bucket.length - k - 1; // ���� �ֱٿ� ���� ���� ����� ��ġ index
		
		for (int i = 1; i <= len; i++) { // candidate items
			bucket[lastIndex + 1] = i;
			solution(len - i, bucket, k - 1);
		}
	}
	
	public static int getError(int len, int pnt) {
		int[] bucket = new int[len];

		int i;
		int sum = 0;
		for (i = 0; i < len; i++) { // �� bucket ���� (2, 3, 5)
			bucket[i] = arr[pnt + i];
			sum += bucket[i];
		}

		int represent = Math.round((float)sum / len);
		
		int error = 0;
		for (int j = 0; j < bucket.length; j++)
			error += Math.pow((bucket[j] - represent), 2);
		return error;
	}
}
