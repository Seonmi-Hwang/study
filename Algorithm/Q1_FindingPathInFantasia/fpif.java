// Updated 19.09.14 by seonmi_Hwang
import java.util.Scanner;

public class findingPathInFantasia {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		double num = scan.nextInt(); // numerator
		double den = scan.nextInt(); // denominator
		
		while (den == 0) { // exception
			System.out.println("Denominator is zero. Try again.");
			num = scan.nextInt();
			den = scan.nextInt();
		}
		System.out.println(findPath(num, den));
		
	}
	
	public static String findPath(double num, double den) {
		String path = "";
		double sA = 0, sB = 1; // small 
		double bA = 1, bB = 0; // big
		double a = 1, b = 1; // mid
		
		while (true) {
			if (num == a && den == b) { // found!
				return path;
			} 

			if (num / den < a / b) {
				path += "L";
				bA = a; bB = b;
				a += sA;
				b += sB;
			} else {
				path += "R";
				sA = a; sB = b;
				a += bA;
				b += bB;			
			}
		}
	}		
}

