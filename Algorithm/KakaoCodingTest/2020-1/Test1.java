/**
 * @author Seonmi Hwang
 * @since 2020.09.04
 * �� 2�ð� 30�� �ҿ�
 */
import java.util.Scanner;

public class Test1 {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		String s = scan.next(); // aabbcc
		
		System.out.println(solution(s));
		
//		System.out.println("Finished");
	}
	
	public static int solution(String s) {
		int minLength = s.length(); // ������ ���ڿ��� ����, �ִ� ���ڿ� ���̷� �ʱ�ȭ
		int count;
		
		for (int i = 1; i <= s.length() / 2; i++) { // i : ������ ����			
			int startIdx = 0; // ���� �ε���
			int endIdx = i; // �� �ε���
			String newStr = "";
			
			while (endIdx <= s.length()) { // ������ �� ��������
				String str_div = s.substring(startIdx, endIdx); // �ݺ��� ���ڿ� ����
				count = 1; // ���ڿ��� �� �� �ݺ�? �⺻ 1�� �ʱ�ȭ
				
				int next_startIdx = endIdx; // ���� ���ڿ��� ���� �ε���
				int next_endIdx = next_startIdx + i; // ���� ���ڿ��� �� �ε���
				while (next_endIdx <= s.length() && str_div.equals(s.substring(next_startIdx, next_endIdx))) { // ���� ���ڿ��� ���� ���ڿ����� �ݺ��Ǵ� ����
					count++;
	
					next_startIdx = next_endIdx;
					next_endIdx = next_startIdx + i;
				}
	
				if (count != 1) { // �ݺ��� �־��� ��� - �� �� �ݺ��Ǿ������� �ݺ��� ���ڿ��� ���ο� ���ڿ��� ����.
					newStr += String.valueOf(count) + str_div;
				} else {
					newStr += str_div;
				}
	
				startIdx = next_startIdx; // ���������� �� ���ڿ��� �������� ���� ����.
				endIdx = next_endIdx;
			}
			
			if (startIdx < s.length()) { // �� �°� ������ �������� �ʾƼ� ���� ���ڿ��� �ִٸ� �� ���ڿ��� ���� �ٿ���.
				newStr += s.substring(startIdx);
			}
			
//			System.out.println(newStr);
			
			if (newStr.length() < minLength) { // ������ ���ڿ��� ���̰� ���²����� ������ ����
				minLength = newStr.length();
			}
		}
		
		return minLength;
	}

}
