import java.util.Random;

public class quickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random rand = new Random();

		final int k = 1000;
		int[] length = {10, 200, 400, 800, 1600, 3200, 6400};

		for (int j = 0; j < length.length; j++) {
			int[] A = new int[length[j]*k];
			for (int i = 0; i < length[j]*k; i++) {
				A[i] = rand.nextInt(32000); // random값 input (중복 발생 가능)
//				System.out.print(A[i] );
			}
			long start = System.currentTimeMillis(); // 수행시간 측정 시작
			quickSort(A);
			long end = System.currentTimeMillis(); // 수행시간 측정 끝

			System.out.println((end - start) / 1000.0);
		}
	}

	public static void quickSort(int[] arr) {
		sort(arr, 0, arr.length - 1);
	}

	private static void sort(int[] arr, int low, int high) {
		if (low >= high)
			return;

		int mid = partition(arr, low, high);
		sort(arr, low, mid - 1);
		sort(arr, mid, high);
	}

	private static int partition(int[] arr, int low, int high) {
		int pivot = arr[(low + high) / 2];
		while (low <= high) {
			while (arr[low] < pivot)
				low++;
			while (arr[high] > pivot)
				high--;
			if (low <= high) {
				swap(arr, low, high);
				low++;
				high--;
			}
		}
		return low;
	}

	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
