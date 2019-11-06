// Dual pivot quicksort + Insertion sort algorithm
import java.util.Random;
import java.util.Scanner;

public class vladimirAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis(); // 수행시간 측정 시작

		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		
		final int k = 1000;
		
		int L = 17;
		int n = 10;
		
		int[] A = new int[n * k];
		
		for (int i = 0; i < n * k; i++)
			A[i] = rand.nextInt(32000000); // random값 input (중복 발생 가능)
		
		vladimirSort(A, 0, A.length - 1, L); // sorting
		
		long end = System.currentTimeMillis(); // 수행시간 측정 끝
		
		System.out.println((end - start) / 1000.0);
	}
	
	
	public static void insertionSort(int[] A, int left, int right) {
		for (int i = left + 1; i <= right; i++) {
			int item, j;
			
			for (j = i - 1; j >= left; j--)
				if (A[j] < A[i]) 
					break;
			item = A[i];
			
			j++;
			for (int k = i ; k > j; k--)
				A[k] = A[k - 1];
			A[j] = item;
		}
	}
	
	public static void vladimirSort(int A[], int left, int right, int L) {
		int len = right - left;
		
		if (len < L) { // L = 17, 33, 65, 129, 257, 513
            insertionSort(A, left, right);
            return;
        }

		if (left > right) return;
		
		int lpiv, rpiv;
		int l, g; // l : lpiv보다 작은 값들의 index, g : rpiv보다 큰 값들의 index
		
		if (A[left] > A[right])
			swap(A, left, right);
		
		lpiv = A[left];
		rpiv = A[right];
		
		l = left + 1;
		g = right - 1;
		
		for (int k = left + 1; k <= g; k++) {
			if (A[k] <= lpiv) {
				swap(A, l++, k);
			} else if (A[k] >= rpiv) {
				while (A[g] >= rpiv && g > k) g--; // A[g]가 rpiv보다 크다면 움직일 필요X (단, g > k 인 경우까지만)
				swap(A, g--, k);
				
				if (A[k] <= lpiv) { // 방금 바뀐 A[k]가 lpiv보다 작은지 확인
					swap(A, l++, k);
				}
			}
		}
		l--; g++;
		
		swap(A, l, left);
		swap(A, g, right);
		
		// 재귀호출
		vladimirSort(A, left, l - 1, L);
		vladimirSort(A, l + 1, g - 1, L);
		vladimirSort(A, g + 1, right, L);
	}
	
    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
