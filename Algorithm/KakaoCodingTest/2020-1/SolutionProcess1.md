### 문제 설명  
첫 번째 문제에 대한 설명은 아래와 같다.  
```
데이터 처리 전문가가 되고 싶은 어피치는 문자열을 압축하는 방법에 대해 공부를 하고 있습니다. 
최근에 대량의 데이터 처리를 위한 간단한 비손실 압축 방법에 대해 공부를 하고 있는데, 
문자열에서 같은 값이 연속해서 나타나는 것을 그 문자의 개수와 반복되는 값으로 표현하여 더 짧은 문자열로 줄여서 표현하는 알고리즘을 공부하고 있습니다.

간단한 예로 aabbaccc의 경우 2a2ba3c(문자가 반복되지 않아 한번만 나타난 경우 1은 생략함)와 같이 표현할 수 있는데, 
이러한 방식은 반복되는 문자가 적은 경우 압축률이 낮다는 단점이 있습니다. 
예를 들면, abcabcdede와 같은 문자열은 전혀 압축되지 않습니다. 
어피치는 이러한 단점을 해결하기 위해 문자열을 1개 이상의 단위로 잘라서 압축하여 더 짧은 문자열로 표현할 수 있는지 방법을 찾아보려고 합니다.

예를 들어, ababcdcdababcdcd의 경우 문자를 1개 단위로 자르면 전혀 압축되지 않지만, 2개 단위로 잘라서 압축한다면 2ab2cd2ab2cd로 표현할 수 있습니다. 
다른 방법으로 8개 단위로 잘라서 압축한다면 2ababcdcd로 표현할 수 있으며, 이때가 가장 짧게 압축하여 표현할 수 있는 방법입니다.

다른 예로, abcabcdede와 같은 경우, 문자를 2개 단위로 잘라서 압축하면 abcabc2de가 되지만, 
3개 단위로 자른다면 2abcdede가 되어 3개 단위가 가장 짧은 압축 방법이 됩니다. 
이때 3개 단위로 자르고 마지막에 남는 문자열은 그대로 붙여주면 됩니다.

압축할 문자열 s가 매개변수로 주어질 때, 위에 설명한 방법으로 1개 이상 단위로 문자열을 잘라 압축하여 표현한 문자열 중 가장 짧은 것의 길이를 return 하도록 solution 함수를 완성해주세요.
```

## 풀이 과정  
처음에 split을 사용해야겠다는 생각에서 출발하여 길고 많은 단어를 압축시키면 좋겠다는 생각에서 아래 블로그를 방문했다.  

https://uiandwe.tistory.com/483  

이 블로그를 통해 substring을 사용하는 것이 맞겠다는 생각을 했고,    

블로그와 같이 중첩 for문을 쓰려다가 아래 예를 보고 n/2번 반복하는 for문 안에 while문을 넣기로 했다.  

```
입출력 예 #5

문자열은 제일 앞부터 정해진 길이만큼 잘라야 합니다.
따라서 주어진 문자열을 x / ababcdcd / ababcdcd 로 자르는 것은 불가능 합니다.
이 경우 어떻게 문자열을 잘라도 압축되지 않으므로 가장 짧은 길이는 17이 됩니다.
```
위 예시에 따라, 최대로 크게 쪼개도 n/2개 이하로 쪼개도록 반복한다.  

하나의 문자열(`str_div`)을 기준으로 잡아놓고,  

반복적으로 나올 수도 있는 동일한 문자열을 index(`next_startIdx`, `next_endIdx`)를 증가시키며 기준 문자열(`str_div`)과 같은지 비교한다.  

반복이 있었을 경우에는 반복된 갯수(`count`)와 기준 문자열(`str_div`)을 새로운 string 변수(`newStr`)에 추가하고,  

반복이 없었을 경우에는 기준 문자열(`str_div`)만 새로운 string 변수(`newStr`)에 추가한다.    

더 이상 동일한 문자열이 나오지 않을 경우에 그 다음 문자열로 기준을 바꾼다.  

만약 문자열을 쪼갠게 딱 떨어지지 않아서 전체 문자열을 보고도 나머지 문자열이 남아있을 경우,  

나머지 문자열들을 새로운 string 변수(`newStr`)에 추가한다.  

최종적으로, 새로운 string 변수(`newStr`)의 길이가 기존 길이(`minLength`)보다 작다면, 아래를 수행한다.  
```java  
minLength = newStr.length();
```

## 결과

프로그래머스 스쿨에서 실행시키기 위해서 Test1.java 파일의 solution 메소드에 있던 코드를 긁어와서 붙여넣기 하였다.

```java
class Solution {
    public int solution(String s) {
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
```  

실행을 시키면 다음과 같이 100%의 정확성으로 풀이하였음을 알 수 있다.<br>  
![image](https://user-images.githubusercontent.com/50273050/92232910-53912300-eeea-11ea-9f23-41c3b7bec879.png)  

