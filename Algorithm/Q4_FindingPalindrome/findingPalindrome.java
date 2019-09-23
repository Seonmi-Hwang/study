import java.util.Scanner;

public class findingPalindrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		String str = scan.next();
		findingPalindrome(str);
	}
	
	public static String findingPalindrome(String str) {
		
		char[] sArr = str.toCharArray();
		
		
		return "a";
	}
	
	public static String makeShortestPalindrom(String s) {
		if (isPalindrome(s)) 
			return s;
		
		String ret = "";
		char[] str = s.toCharArray();
		char[] rev_str = new char[str.length - 1];
		
		for (int i = 0; i < rev_str.length; i++) {
			rev_str[i] = str[str.length - i -2];
		}
		
		for (int i = 0; i < rev_str.length; i++) {
			String t = s + String.valueOf(rev_str, i, rev_str.length - i);
			
			if (isPalindrome(t)) ret = t;
		}
		
		return ret;
	}

	public static boolean isPalindrome(String s) {
		int len = s.length();
		
		for (int i = 0; i < len / 2; i++) {
			if (s.charAt(i) != s.charAt(len - i - 1))
				return false;
		}
		return true;
	}
}
