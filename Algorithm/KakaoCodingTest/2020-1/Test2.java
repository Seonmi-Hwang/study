import java.util.ArrayList;
import java.util.Scanner;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		System.out.println(solution(s.next()));
	}
	
    public static String solution(String p) { // p는 '('와 ')'로만 이루어진 문자열
//        System.out.println("p : " + p);
    	
    	char[] arr = p.toCharArray();
        
    	if (p.equals("")) {
    		return "";
    	}
    	
        if (isRightStrArr(arr)) {
        	return p;
        }
        
        String u = "", v = "";

        int idx = 0;
        int startCnt = 0, endCnt = 0;
        do {
        	if (arr[idx] == '(') {
        		startCnt++;
        	} else {
        		endCnt++;
        	}
        	idx++;
        } while (endCnt != startCnt && idx != arr.length);

        if (idx == arr.length) {
        	for (int i = 0; i < arr.length; i++) {
        		u += arr[i];
        	}
        } else {
	        for (int i = 0; i < startCnt + endCnt; i++) {
	        	u += arr[i]; 
	        }
	        
	        for (int i = startCnt + endCnt; i < arr.length; i++) {
	        	v += arr[i];
	        }
        }
        
//        System.out.println("u : " + u);
//        System.out.println("v : " + v);
        
        String answer = "";
        char[] uArr = u.toCharArray();
        if (isRightStrArr(uArr)) {
        	return u + solution(v);
        } else {
        	answer = "(" + solution(v) + ")";
        	
        	for (int i = 1; i < uArr.length - 1; i++) {
        		if (uArr[i] == '(') {
        			answer += ")";
        		} else {
        			answer += "(";
        		}
        	}
        }
        
        return answer;
    }
    
    public static boolean isRightStrArr(char[] arr) { // 올바른 괄호 문자열
        ArrayList<String> stack = new ArrayList<String>();
        int stackIdx = -1;
        
        for (int i = 0; i < arr.length; i++) {
        	if (arr[i] == '(') {
        		stack.add("(");
        		stackIdx++;
        	} else { // arr[i] == ')'
        		if (stackIdx < 0) {
        			return false;
        		}
        		stack.remove(stackIdx--);
        	}
        }
        
        if (stack.size() == 0) {
        	return true;
        } else { 
        	return false;
        }
    }

}
