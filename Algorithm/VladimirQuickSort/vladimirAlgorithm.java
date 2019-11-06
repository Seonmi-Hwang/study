// Dual pivot quicksort + Insertion sort algorithm
import java.util.Random;
import java.util.Scanner;

public class vladimirAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis(); // ����ð� ���� ����

		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		
		final int k = 1000;
		
		int L = 640000000;
		int n = 800;
		
		int[] A = new int[n * k];
		
		for (int i = 0; i < n * k; i++)
			A[i] = rand.nextInt(32000000); // random�� input (�ߺ� �߻� ����)
		
		vladimirSort(A, 0, A.length - 1, L); // sorting
		
		long end = System.currentTimeMillis(); // ����ð� ���� ��
		
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
		int l, g; // l : lpiv���� ���� ������ index, g : rpiv���� ū ������ index
		
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
				while (A[g] >= rpiv && g > k) g--; // A[g]�� rpiv���� ũ�ٸ� ������ �ʿ�X (��, g > k �� ��������)
				swap(A, g--, k);
				
				if (A[k] <= lpiv) { // ��� �ٲ� A[k]�� lpiv���� ������ Ȯ��
					swap(A, l++, k);
				}
			}
		}
		l--; g++;
		
		swap(A, l, left);
		swap(A, g, right);
		
		// ���ȣ��
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
