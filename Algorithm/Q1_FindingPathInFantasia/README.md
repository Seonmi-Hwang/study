# Finding Path in Fantasia  
* 수열의 규칙 혹은 모양을 이해하는 문제 
* 특정 분수를 입력받았을 경우, 해당 분수의 값을 가지는 노드를 찾는 path를 **LR 인코딩**  값으로 출력  

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **\* LR 인코딩**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_왼쪽 sub-tree 방문 -> L  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;오른쪽 sub-tree 방문 -> R_  



## [문제의 이해]
![fpif1](https://user-images.githubusercontent.com/50273050/64906949-88c7c280-d727-11e9-84dc-2add879c4948.jpg)

## [Fantasia Tree]
![fpif2](https://user-images.githubusercontent.com/50273050/64906950-88c7c280-d727-11e9-9193-81386526a88f.jpg)

## [예시]
![fpif3](https://user-images.githubusercontent.com/50273050/64906948-882f2c00-d727-11e9-9f1d-b3d19d61e9e1.jpg)

* **Hint**  
Binary Search 활용  
범위를 따져라  

* **Warning**  
1. Tree에서는 분수로 나오지만, 실제 컴퓨터는 소수(각각의 자리에 놓인 숫자와 소수점을 통해 나타낸 실수)로 계산  
&nbsp;&nbsp;&nbsp;&nbsp;==> 변수의 타입 주의 **int (X) -> double (O)**  
1. 분모가 0인 경우  
1. 코드의 논리과정을 따르며 머릿 속으로 먼저 Debugging 해본 후, Debugger 사용  

* **Solution**  
1. main) Scanner로 분자, 분모를 입력받는다. (type : double)  
1. main) 분모가 0일 경우, 올바르게 입력할 때까지 다시 입력받는다.
1. main) LR 인코딩을 진행할 findPath 함수를 호출한다.
1. findPath) path를 저장할 변수와 현재 분수를 나타낼 변수, 범위를 지정해 줄 분수 두 개를 선언한다.  
1. findPath) while문으로 반복해주고, loop를 종료시킬 조건문(분수를 찾았을 경우)을 넣는다.   
1. findPath) 찾을 분수와 현재 분수의 크기를 비교하여 if else문으로 path를 찾고, 범위와 현재 분수를 수정한다.  

* 빅오 표기법  
17n + 11 => O(n)  

* 풀이 소요시간  
약 90분
