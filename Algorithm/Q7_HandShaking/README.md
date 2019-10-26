# Handshaking  
* 원탁에서 팔이 겹치지 않게 모두 악수를 하는 방법의 수를 구하는 문제  
* N개의 숫자(인원 수)를 입력받아 경우의 수를 리턴  

## [문제의 이해]  
1. 인원 수를 입력  
1. N = 2k일 때, H(2k) = H(2k-2) + H(2) \* H(2k-4) + H(3) \* H(2k-6) + ... + H(2k-4) \* H(2) + H(2k-2) 적용  
1. H(2k) 구하여 반환    

![image](https://user-images.githubusercontent.com/50273050/67615771-5e2f5600-f80b-11e9-903c-4bb6a2c8741f.png)  

## [예시]  
N = 2 일 때, H(2) = 1  
N = 4 일 때, H(4) = 2  
N = 6 일 때, H(6) = 5  

Input) 6 [Enter]  
Output) 5  

### ◼️ **Hint**  
두 가지 방법이 있다.  

* **방법1) 전체 경우를 고려**  
문제의 이해 2번에서 나온 식을 좀 더 보기 쉽게 바꿔보자.   
**H(2k) = _H(0) \* H(2k-2)_ + _H(2) \* H(2k-4)_ + _H(4) \* H(2k-6)_ + ... + _H(2k-4) \* H(2)_ + _H(2k-2) \* H(0)_**  
위 공식에 따라 코딩을 하면 아래와 같다.  
```java
public static long getHandShaking(int n) { 
	int k = n / 2;  
		
	if (n == 0 || n == 2) return 1;  

	long count = 0;  
	for (int i = 0; i < k; i++) { // (n / 2 - 1)번 반복  
		count += getHandShaking(2 * i) * getHandShaking(2 * (k - 1 - i)); // 재귀 호출
	}  
	return count;  
} 
```  
사실 H(0)은 없으나, 재귀 호출 시 종료조건(base condition)을 위해 H(0)을 1로 만들면 규칙이 성립된다.  
count는 k가 커졌을 때, 정수형 범위를 벗어날 수 있기 때문에 long타입으로 한다.  
반복문으로 k번 만큼 loop를 돌면, count에 쌓이게 되고 loop 탈출 후 반환한다.  
### BUT!  
N = 30 일 때부터 눈에 띄게 느려지더니, N = 40 일 때는 약 7초가 소요되었다.  
=> 속도를 높이려면 어떻게 해야할까? -> **방법2 이용!**  

* **방법2) Memoization 사용**  
Memoization을 이용하여 불필요한 재귀 호출을 줄여서  
k가 충분히 크더라도 1초에 계산할 수 있도록 한다.  

```java
public static long getHandShaking(int n, long[] memo) {
	int k = n / 2;
		
	if (n == 0 || n == 2) 
		return 1;

	long count = 0;
	for (int i = 0; i < k; i++) { // (n / 2 - 1)번 반복
		if (memo[i] == 0) // memo[i]가 존재하지 않는다면
			memo[i] = getHandShaking(2 * i, memo);
			
		if (memo[k - 1 - i] == 0) // memo[k - 1 - i]가 존재하지 않는다면
			memo[k - 1 - i] = getHandShaking(2 * (k - 1 - i), memo);
			
		count += memo[i] * memo[k - 1 - i]; 
	}
	return count; 
}
```
memo에 저장되지 않은 부분만 재귀호출을 하기 때문에 불필요한 재귀호출을 방지한다.  
k를 60, 80, 100, 1000 등의 큰 수를 입력해보았지만 모두 1초안에 결과값이 나왔다.

### ◼️ **Warning**  
K가 충분히 크더라도 1초에 계산을 할 수 있는 방법을 생각해보자.    

### ◼️ **Solution** 
(n / 2 - 1)번 동안, 계산할 것이 메모에 있는지 확인 후 재귀 호출할지 결정 후 곱해주면 끝.  

* **시간복잡도**  
\- 어떻게 구해야 할 까..?  
T(n) = T(0) * T(n - 2)  
&nbsp;&nbsp;&nbsp;&nbsp; T(2) * T(n - 4)  
&nbsp;&nbsp;&nbsp;&nbsp; T(4) * T(n - 6)  
...  
&nbsp;&nbsp;&nbsp;&nbsp; T(n - 2) * T(0)  
시그마..?

* **풀이 소요시간**  
\- 약 40분 (memoization)  
