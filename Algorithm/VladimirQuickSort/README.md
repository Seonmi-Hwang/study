## Vladimir Yaroslavskiy's Algorithm  
* **Dual pivot quicksort + Insertion sort algorithm**  

#### 1. Implementation of Vladimir Yaroslavskiy’s algorithm   
Vladimir QuickSort 알고리즘을 java(or c)로 구현한다.   

```java
  // 직접 짠 코드
	public static void vladimirSort(int A[], int left, int right, int L) {
		int len = right - left;
		
		if (len < L) { // L = 17, 33, 65, 129, 257, 513
            insertionSort(A, left, right);
            return;
    }

		if (left > right) return;
		
		int lpiv, rpiv;
		int l, g; // l : lpiv보다 작은 값들의 index, g : rpiv보다 큰 값들의 index
		
		if (A[left] > A[right])
			swap(A, left, right);
		
		lpiv = A[left];
		rpiv = A[right];
		
		l = left + 1;
		g = right - 1;
		
		for (int k = left + 1; k <= g; k++) {
			if (A[k] <= lpiv) {
				swap(A, l++, k);
			} else if (A[k] >= rpiv) {
				while (A[g] >= rpiv && g > k) g--; // A[g]가 rpiv보다 크다면 움직일 필요X (단, g > k 인 경우까지만)
				swap(A, g--, k);
				
				if (A[k] <= lpiv) { // 방금 바뀐 A[k]가 lpiv보다 작은지 확인
					swap(A, l++, k);
				}
			}
		}
		l--; g++;
		
		swap(A, l, left);
		swap(A, g, right);
		
		// 재귀호출
		vladimirSort(A, left, l - 1, L);
		vladimirSort(A, l + 1, g - 1, L);
		vladimirSort(A, g + 1, right, L);
	}
```

<hr>

#### 2. Comparison of the traditional quicksort and improved one.  
일반 QuickSort와 Vladimir QuickSort의 수행 시간을 비교한다.   

![TraditionalQuickSort_수행시간_비교그래프](https://user-images.githubusercontent.com/50273050/68300482-38466300-00e1-11ea-8c7c-19d1f50dc796.JPG)  
17 ~ 513은 Vladimir QuickSort에서 L값을 변화시켜 나온 그래프를 의미한다.  
둘의 수행시간을 그래프로 비교한 결과 L값에 상관없이 Vladimir QuickSort는 매우 빠른 것을 볼 수 있으나,  
Traditional QuickSort는 그들에 비해 매우 느린 것을 확인할 수 있다.  


아래는 직접 입력하여 수행시간을 출력한 표이다.  
Traditional QuickSort는 1600k 이후로 너무 느려서 1600k 이전의 증가하는 모습을 반영하여 임의의 값을 입력하였다.  

![실행시간표](https://user-images.githubusercontent.com/50273050/68300567-63c94d80-00e1-11ea-95d9-321f3dce5c94.JPG)  

<hr>

#### 3. 수행 시간 비교를 위해 정렬해야 할 item의 수와 L을 변경한다.  
L = 17, 33, 65, 129, 257, 513  

정렬해야 하는 array는 integer array. (초기 값은 모두 random으로)  
Array 크기도 변화 시켜가면서 (10k, 100k, 200k, 400k, 800k, 1600k, 3200k, 6400k)  

<hr>

#### 4. 그래프는 Excel 등으로 그린다.  

![Vladimir_수행시간_그래프](https://user-images.githubusercontent.com/50273050/68300626-8491a300-00e1-11ea-8b30-8e96be815978.JPG)  

위 그래프는 개별항목과 전체를 비교한 그래프이다. 따라서 왼쪽의 숫자는 실제 실행시간이 아니다.  
실제 실행시간은 표1을 보면 확인할 수 있다.  

