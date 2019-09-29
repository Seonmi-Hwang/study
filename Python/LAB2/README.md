# Python Basic 2  
Python의 기초를 다지기 위한 directory  

**\* 주의 : 파이썬은 들여쓰기로 블록 구조를 만든다.**  

⬛️ **반복문**  
* **while 반복문**  
들여쓰기에 주의, ':' 빠뜨리지 말 것.  
```python
student = 1

while student <= 5 :
  print(student, "번 학생의 성적을 처리한다.")
  student += 1
```

* **for 반복문**  
컬렉션의 요소를 순서대로 반복하면서 루프의 명령을 실행(JAVA의 for-each문과 유사)  
\* 컬렉션은 리스트나 문자열이 대표적  
```python
for student in [1, 2, 3, 4, 5]: # 리스트
  print(student, "번 학생의 성적을 처리한다
  
for grade in range(1, 5): # 범위지정
  # C에서 사용하던 삼항연산자(?,:)는 아래와 같이 사용 가능
  homework = grade if grade == 4 else (grade + 1) ** 2 ## **는 제곱을 의미하는 듯!!
  print("컴퓨터학과 ", grade, "학년은 숙제가 ", homework, "개")
```

⬛️ **함수**  
* 반복적으로 사용되는 코드는 한 번 정의해 두고 계속 사용  
* 매개변수가 있는 경우, **자료형 없이 이름만 나열**   
* 함수 호출 전에 미리 함수를 정의해야 함  
* 빈 코드를 의미하는 별도의 키워드 필요 : pass  
```python
def fact(n): # 함수는 블록 내부에 정의, 파라미터는 이름만 나열.  
  result = 1
  
  for a in range(2, n + 1):
    result *= a
  
  return result
  
n = int(input("input the number : "))
print(fact(n))

def func(a, b, c):
  pass # 빈 코드를 의미

```

* **가변 인수**  
\- 가변 인수는 임의 개수의 인수를 받음  
\- 호출 시 개수가 맞지 않으면 에러 발생  
\- 가변 인수와 기본값 인수는 맨 뒤에만 올 수 있음  
```python
def intsum(*ints): # 인수 이름 앞에 '*' 기호를 붙이면 이 자리에 여러 개의 인수가 올 수 있음 
  sum = 0
    for num in ints: # 튜플로 묶여서 전달
      sum += num
    return sum
    
print(intsum(1, 2, 3))
print(intsum(5, 7, 9, 10))

def calcstep(a, b, step = 1) # 값이 잘 바뀌지 않는 인수는 인수 목록에서 기본값 지정 가능
  sum = 0
  for num in range(a, b + 1, step):
    sum += num
  return sum
  
print("1부터 10까지 합은? ", calcstep(1, 10)) # 생략 시 default값인 1 전달
print(~~~~~~~~~~~~~~~~~~~~~, calcstep(1, 10, 2)) # 명시해준 2가 전달
```

* **키워드 인수**  
\- 인수의 이름을 지정하여 대입 형식으로 전달하는 방식  
\- 이름으로 구분 가능하므로 순서가 바뀌어도 상관 없음  
```python
def calcstep(a, b, step) # 함수 prototype 정의

calcstep(step = 2, a = 1, b = 10) # 가능
calcstep(a = 1, 1, 2) # 에러
```

* **docstring**  
```python
def printString(s):
  ''' 문자열을 받아 출력한다 '''
  print(s)

help(printString) # '함수에 대한 긴 설명'을 출력해줌
```

⬛️ **Generator Expression**  
* **Memoization 사용 시 쓰임(물론 다른 경우에도 쓰일 수 있음)**  
Memo 할당 및 초기화 시 사용된다.  
아래는 **피보나치 수열**에 쓰인 경우이다.    
```c
// C언어
F = (int *) malloc(sizeof(int) * (n + 1));
for (i = 1; i <= n; i++)
  F[i] = 0;
```
```python
# Python
F = [0 for i in range(n + 1)]
```
아래는 방문 경로가 **최소값이 되는 path**를 구할 때 쓰인 경우이다.   
```c
// C언어  
for (i = 0; i < r; i++)
  for(j=0; j < c; j++ ) { 
    M[i][j] = 0; // 메모 초기화
  }
``` 
```python
#Python
M = [[0 for i in range(c)] for i in range(r)]
```
