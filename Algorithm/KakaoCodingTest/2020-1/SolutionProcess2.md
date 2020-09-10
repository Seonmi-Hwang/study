## 문제 설명  
두 번째 문제에 대한 설명은 아래와 같다.  
```
카카오에 신입 개발자로 입사한 콘은 선배 개발자로부터 개발역량 강화를 위해 다른 개발자가 작성한 소스 코드를 분석하여 문제점을 발견하고 수정하라는 업무 과제를 받았습니다.  
소스를 컴파일하여 로그를 보니 대부분 소스 코드 내 작성된 괄호가 개수는 맞지만 짝이 맞지 않은 형태로 작성되어 오류가 나는 것을 알게 되었습니다.  
수정해야 할 소스 파일이 너무 많아서 고민하던 콘은 소스 코드에 작성된 모든 괄호를 뽑아서 올바른 순서대로 배치된 괄호 문자열을 알려주는 프로그램을 다음과 같이 개발하려고 합니다.  
```
### 용어의 정의  
'(' 와 ')' 로만 이루어진 문자열이 있을 경우, '(' 의 개수와 ')' 의 개수가 같다면 이를 `균형잡힌 괄호 문자열`이라고 부릅니다.  
그리고 여기에 '('와 ')'의 괄호의 짝도 모두 맞을 경우에는 이를 `올바른 괄호 문자열`이라고 부릅니다.  
예를 들어, `"(()))("`와 같은 문자열은 "균형잡힌 괄호 문자열" 이지만 "올바른 괄호 문자열"은 아닙니다.  
반면에 `"(())()"`와 같은 문자열은 "균형잡힌 괄호 문자열" 이면서 동시에 "올바른 괄호 문자열" 입니다.  

'(' 와 ')' 로만 이루어진 문자열 w가 "균형잡힌 괄호 문자열" 이라면 다음과 같은 과정을 통해 "올바른 괄호 문자열"로 변환할 수 있습니다.  

* 편의 상 아래 과정을 **변환 과정**이라고 부르겠다.  
```
1. 입력이 빈 문자열인 경우, 빈 문자열을 반환합니다. 
2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리합니다. 단, u는 "균형잡힌 괄호 문자열"로 더 이상 분리할 수 없어야 하며, v는 빈 문자열이 될 수 있습니다. 
3. 문자열 u가 "올바른 괄호 문자열" 이라면 문자열 v에 대해 1단계부터 다시 수행합니다. 
  3-1. 수행한 결과 문자열을 u에 이어 붙인 후 반환합니다. 
4. 문자열 u가 "올바른 괄호 문자열"이 아니라면 아래 과정을 수행합니다. 
  4-1. 빈 문자열에 첫 번째 문자로 '('를 붙입니다. 
  4-2. 문자열 v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙입니다. 
  4-3. ')'를 다시 붙입니다. 
  4-4. u의 첫 번째와 마지막 문자를 제거하고, 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙입니다. 
  4-5. 생성된 문자열을 반환합니다.
```
"균형잡힌 괄호 문자열" p가 매개변수로 주어질 때, 주어진 알고리즘을 수행해 올바른 괄호 문자열로 변환한 결과를 return 하도록 solution 함수를 완성해 주세요.  

### 매개변수 설명  
* p는 '(' 와 ')' 로만 이루어진 문자열이며 길이는 2 이상 1,000 이하인 짝수입니다.  
* 문자열 p를 이루는 '(' 와 ')' 의 개수는 항상 같습니다.  
* 만약 p가 이미 올바른 괄호 문자열이라면 그대로 return 하면 됩니다.  

**입출력 예**와 같은 자세한 내용은 아래 링크를 참조.  
https://programmers.co.kr/learn/courses/30/lessons/60058  

<br>

## 풀이 과정  
괄호 문제임을 보자마자 stack이 떠올랐다.  

그리고 올바른 괄호 문자열로 `변환` 해야하고, `변환`을 어떻게 할지 친절하게 알고리즘을 한글로 써놔서 논리과정대로 코딩을 진행하면 된다.  

올바른 괄호 문자열일 경우엔 그대로 return해야 하고, 올바른 괄호 문자열인지를 파악하는 코드가 반복적으로 발생하므로,  

올바른 괄호 문자열을 확인해 줄 메소드(`isRightStrArr`)를 따로 만들면 간편하겠다는 생각이 들었다.  

stack을 사용해야겠다는 생각과 어우러져 ArrayList(`stack`)와 index 변수(`stackIdx`)를 이용하여 stack과 같은 역할을 하여  

괄호의 짝이 올바르게 맞는지 확인하는 작업을 하는 메소드를 작성했다.   

문자열의 각 문자(괄호)를 개별적으로 비교해야하므로 toCharArray() 메소드를 이용하여 String 변수를 char형 배열로 변환하여 사용한다.  

**아래는 변환 과정 2의 구체적인 풀이과정이다.**  

두 문자열 u, v로 분리할 때에는 u가 균형잡힌 괄호 문자열로 더 이상 분리할 수 없어야 하므로  

시작 괄호와 끝 괄호의 개수가 일치되는 순간까지만 문자열 u로 분리되어야 하고, 나머지 문자열을 v에 들어가야한다.  

그 순간이 문자열 끝까지 오게된다면 u에 모든 문자열이 들어가며, v는 빈 문자열이 된다.  

**아래는 변환 과정 3부터 4까지의 모든 과정을 구현한 코드이다.**  

```java
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
```


## 결과

프로그래머스 스쿨에서 실행시키기 위해서 Test2.java 파일의 solution 메소드에 있던 코드를 긁어와서 붙여넣기 하였다.

```java
import java.util.ArrayList;
class Solution {
    public String solution(String p) {
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
}
```  

실행을 시키면 다음과 같이 100%의 정확성으로 풀이하였음을 알 수 있다.<br>  
![image](https://user-images.githubusercontent.com/50273050/92756192-8f832700-f3c7-11ea-94de-e2a21ae53939.png)  
