// Update 19.09.15 by seonmi-Hwang
public class simpleEncoding {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array = {1, 2, 3};
		
		System.out.println(getEncodedNumber(array));
	}
	
	public static int getEncodedNumber(int arr[]) {
		int max = 1;
		int min_idx = 0;
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[min_idx] > arr[i]) {
				min_idx = i;
			}
		}
		
		arr[min_idx]++;
		for (int i = 0; i < arr.length; i++) {
			max = max * arr[i];
		}
		arr[min_idx]--;
		return max;
	}
}

