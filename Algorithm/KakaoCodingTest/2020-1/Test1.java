/**
 * @author Seonmi Hwang
 * @since 2020.09.04
 * 약 2시간 30분 소요
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
		int minLength = s.length(); // 압축한 문자열의 길이, 최대 문자열 길이로 초기화
		int count;
		
		for (int i = 1; i <= s.length() / 2; i++) { // i : 압축할 단위			
			int startIdx = 0; // 시작 인덱스
			int endIdx = i; // 끝 인덱스
			String newStr = "";
			
			while (endIdx <= s.length()) { // 끝까지 다 볼때까지
				String str_div = s.substring(startIdx, endIdx); // 반복될 문자열 선택
				count = 1; // 문자열이 몇 번 반복? 기본 1로 초기화
				
				int next_startIdx = endIdx; // 다음 문자열의 시작 인덱스
				int next_endIdx = next_startIdx + i; // 다음 문자열의 끝 인덱스
				while (next_endIdx <= s.length() && str_div.equals(s.substring(next_startIdx, next_endIdx))) { // 현재 문자열과 같은 문자열들이 반복되는 동안
					count++;
	
					next_startIdx = next_endIdx;
					next_endIdx = next_startIdx + i;
				}
	
				if (count != 1) { // 반복이 있었을 경우 - 몇 번 반복되었는지와 반복된 문자열을 새로운 문자열에 붙임.
					newStr += String.valueOf(count) + str_div;
				} else {
					newStr += str_div;
				}
	
				startIdx = next_startIdx; // 마지막으로 본 문자열의 다음부터 보기 시작.
				endIdx = next_endIdx;
			}
			
			if (startIdx < s.length()) { // 딱 맞게 나누어 떨어지지 않아서 남은 문자열이 있다면 새 문자열에 마저 붙여줌.
				newStr += s.substring(startIdx);
			}
			
//			System.out.println(newStr);
			
			if (newStr.length() < minLength) { // 압축한 문자열의 길이가 여태껏보다 작으면 갱신
				minLength = newStr.length();
			}
		}
		
		return minLength;
	}

}
