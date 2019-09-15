// Update 19.09.15 by seonmi-Hwang
import java.util.Scanner;

public class simpleEncoding {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int[] array = new int[3];
		
		for (int i = 0; i < 3; i++) {
			array[i] = scan.nextInt();
		}
		
		System.out.println(getEncodedNumber(array));
	}
	
	public static int getEncodedNumber(int arr[]) {
		int enc = 1;
		int min_idx = 0;
		
		for (int i = 0; i < arr.length; i++) { // 작은 수 찾기
			if (arr[min_idx] > arr[i]) {
				min_idx = i;
			}
		}
		
		arr[min_idx]++;
		for (int i = 0; i < arr.length; i++) {
			enc = enc * arr[i];
		}
		arr[min_idx]--;
		
		return enc;
	}
}

