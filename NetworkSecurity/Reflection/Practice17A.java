import java.util.Scanner;

public class Practice17A {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		System.out.print("날짜를 입력하세요 : ");
		String userInput = scan.next();
		String[] numbers = userInput.split("-");

		Date date = new Date(new Integer(numbers[0]), new Integer(numbers[1]), new Integer(numbers[2]));
		System.out.println(date.printWestern());
	}
	
}
