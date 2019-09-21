# Card Play  
* "있다"/"없다"를 각각 Y, N으로 하여 입력받아 숫자를 출력하는 프로그램  

## [문제의 이해]  
1. 16보다 작은 숫자를 떠올린다.  
1. 8개의 숫자가 적힌 카드 4개를 보고 Y, N을 입력한다.  
  Card 1 : 1, 2, 3, 4, 5, 6, 7, 8  
  Card 2 : 1, 2, 3, 4, 9, 10, 11, 12  
  Card 3 : 1, 2, 5, 6, 9, 10, 13, 14  
  Card 4 : 1, 3, 5, 7, 9, 11, 13, 15  
1. 입력한 값을 보고 숫자를 찾아 출력한다.  

## [예시]  
입력 : Y N Y Y [Enter]  
출력 : 5  

### ◼️ **Hint**  
두 가지 방법이 있다.  

* **방법1) 패턴 찾기** // 내가 직접 구상한 방식  
어떤 기준으로 카드에 숫자를 적어놓았는지 출력결과를 통해 패턴을 찾아보자.   

**\- 숫자에 따른 출력 결과**  
> 1을 생각했을 경우, YYYY  
> 2를 생각했을 경우, YYYN  
> 3을 생각했을 경우, YYNY  
> . . .  
> 15를 생각했을 경우, NNNN  

위와 같은 패턴이 나오도록 카드에 숫자를 적혀있다.  
한 자릿수 당 2가지의 경우의 수(Y or N)가 있으므로 2진법을 적용해볼 수 있다.  
Y는 0으로, N은 1로 치환하여 위 결과를 다시 보면,  

> 1을 생각했을 경우, 0000  
> 2를 생각했을 경우, 0001  
> 3을 생각했을 경우, 0010  
> . . .  
> 15를 생각했을 경우, 1111  

따라서 2진법으로 치환한 후, 1만 더해주면 숫자를 쉽게 찾을 수 있다. **_(단, 0의 경우는 주의)_**  

* **방법2) 일반적인 방법** // 교수님 방식  

~ 설명 필요 ~

### ◼️ **Warning**  
16이 나왔을 경우에 0을 반환해주는 조건이 필요하다.  
2진법으로 변환해줄 변수를 배열의 값에 곱하는 반복마다 1로 초기화해야 한다. (Solution 5)  

### ◼️ **Solution**  
1. main) 크기 4의 String 배열을 선언하여 배열의 크기만큼 for문으로 입력받는다.  
1. main) findNum 함수를 호출한다.  
1. findNum) 찾은 수를 담을 변수(num), 2진법을 계산해줄 변수(power), 입력받은 String을 2진법으로 치환해줄 배열(array)을 선언한다.  
1. findNum) 배열의 크기만큼 반복하여 Y는 0으로, N은 1로 치환해준 후 array에 저장한다.  
1. findNum) 각 자릿 수마다 알맞은 2의 배수를 계산한 후(power), 배열의 값과 power를 곱하여 num에 더해준다.  
1. findNum) num이 16일 경우는 0을 반환, 아닐 경우는 num을 반환한다.  

* 시간복잡도
2(n power 2) + 6n + 6 => O(n power 2)  

* 풀이 소요시간  
약 30분  

* 참고  
**교수님의 방법과 source code는 추후에 추가할 예정**  