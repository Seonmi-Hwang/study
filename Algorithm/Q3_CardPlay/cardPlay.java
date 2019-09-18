import java.util.Scanner;

public class cardPlay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
//		int[] card1 = {1, 2, 3, 4, 5, 6, 7, 8};
//		int[] card2 = {1, 2, 3, 4, 9, 10, 11, 12};
//		int[] card3 = {1, 2, 5, 6, 9, 10, 13, 14};
//		int[] card4 = {1, 3, 5, 7, 9, 11, 13, 15};
		String[] arr = new String[4];
		
		for (int i = 0; i < 4; i++) {
			arr[i] = scan.next();
		}
		
		System.out.println(findNum(arr));
	}

	public static String[] cards2 =
	{
				"NNNN",
				"YYYY",
				"YYYN",
				"YYNY",
				"YYNN",
				"YNYY",
				"YNYN",
				"YNNY",
				"YNNN",	
	};
	
	public static int findNum(String[] arr) {
		int num = 1;
		int[] array = new int[4];
		int power = 1;
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("Y")) {
				array[i] = 0;
			} else {
				array[i] = 1;
			}
		}
		
		int len = array.length;
		for (int i = 0; i < array.length; i++) {
			power = 1;
			for (int j = 1; j < len - i; j++) {
				power *= 2;
			}
			num += power * array[i]; 
		}
		
		if (num == 16) {
			num = 0;
		}
		
		return num;
	}
}
