## Synchronizing Clock  
* 중복순열 문제   

![image](https://user-images.githubusercontent.com/50273050/67964838-51b06080-fc44-11e9-947a-c489fea83367.png)  

![image](https://user-images.githubusercontent.com/50273050/68688808-c3789a80-05b2-11ea-93c7-b0515bcc863e.png)  

![image](https://user-images.githubusercontent.com/50273050/67964932-786e9700-fc44-11e9-87a7-9118e34686df.png)  

### ⬛️ 문제 설명
* 표에 나온 것처럼 각 스위치에 시계가 연결되어있다.  
* 스위치와 연결된 시계, 현재 시계의 상태는 실행 중에 입력받는다.  
* 스위치의 index와 횟수를 선택하여 시계를 모두 12시로 맞춘다.   
* 스위치를 누르는 횟수가 가장 작은 숫자를 출력한다. 

### ⬛️ Solution  
1. 0번부터 9번까지의 스위치에 어떤 시계들이 연결되어 있는지의 정보를 담을 2차원 배열 buttons[10][동적할당]   
2. 16개의 시계를 입력받아 저장시켜줄 clocks[16]  
3. 각 스위치가 몇 번 눌렸는지 횟수를 저장할 bucket[10]  
4. 한 스위치는 최대 3번만 눌릴 수 있으므로(4번은 0번과 같음) bucket이 뽑을 item의 목록인 items는 {0, 1, 2, 3}  
5. 스위치와 시계를 입력받고 중복순열을 수행할 solution을 호출  
6. 중복순열을 수행하여 k가 0이 될 때마다(한 개의 중복순열이 도출될 때마다) clock의 시침을 옮겨줄 moveClock을 호출  
7. 원본은 변경되면 안되므로 clocks를 임시적으로 저장할 tmpClocks를 clocks의 사본으로 만듦  
8. bucket의 개수만큼 for문을 반복, 단 bucket의 값이 0이면 스위치가 선택되지 않았으므로 시침을 옮겨줄 필요가 없으므로 continue  
9. buttons[bucket의 인덱스(스위치 인덱스)]의 길이만큼(연결된 시계의 개수) 반복하여 해당 시계에 '스위치를 누른 횟수 * 3'을 더해줌(시침을 옮겨줌)  
10. bucket에 있는 모든 인덱스까지 처리된 후(시계의 시침을 조건만큼 옮겨준 후) tmpClocks 중 12의 배수가 아닌 수가 있으면 -1 반환, 아니면 bucket의 개수를 모두 더해(스위치를 누른 총 횟수) 반환  
11. solution으로 반환된 값이 -1이 아니고 스위치를 누른 최솟값(min) 보다 작은 경우 min에 반환된 값인 cnt를 넣고 return, 아니면 그냥 return.    
12. 모든 중복순열이 끝난 후 최종적으로 가장 작은 값이 저장되어 있을 min을 출력  

```java
import java.util.Scanner;

public class synchronizing_clock {

	static int[] clocks = new int[16];
	static int min = 100000; // 아무거나 큰 값 설정
	static int[][] buttons = new int[10][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int[] items = {0, 1, 2, 3};
		int[] bucket = new int[10];
		
		int idx, num; 
		
		for (int i = 0; i < 10; i++) {
			idx = scan.nextInt();
			num = scan.nextInt();
			
			buttons[idx] = new int[num];
			
			for (int j = 0; j < num; j++)
				buttons[idx][j] = scan.nextInt();
		}
		
		for (int i = 0; i < 16; i++)
			clocks[i] = scan.nextInt();
		
		solution(items, bucket, 10);
		System.out.println(min);
	}
	
	public static void solution (int[] items, int[] bucket, int k) {
		if (k == 0) {
			int cnt = moveClock(bucket);
			
			if (cnt != -1 && cnt < min)
				min = cnt;
			return;
		}
		
		int lastIdx = bucket.length - k - 1;
		
		for (int i = 0; i < items.length; i++) {
			bucket[lastIdx + 1] = items[i];
			solution(items, bucket, k - 1);
		}
	}
	
	public static int moveClock(int[] bucket) {
		int cnt = 0;
		int[] tmpClocks = new int[16];
		
		for (int i = 0; i < clocks.length; i++) // clocks 배열 복사
			tmpClocks[i] = clocks[i];
		
		for (int i = 0; i < bucket.length; i++) { // button 0부터 9까지
			if (bucket[i] == 0) continue; // 버튼을 누르지 않았으면 확인하지 않기
			
			for (int j = 0; j < buttons[i].length; j++)  // buttons 0 ~ 9까지의 포함한 시계
				tmpClocks[buttons[i][j]] += bucket[i] * 3; // clock의 값
		}
		
		for (int i = 0; i < tmpClocks.length; i++)
			if (tmpClocks[i] % 12 != 0) // 12가 아닌 경우가 한 번이라도 나오면
				return -1;
		
		for (int i = 0; i < bucket.length; i++)
			cnt += bucket[i];	
		
		return cnt;
	}

}
```
