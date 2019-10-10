# HW1  

### ■ Chapter 2  

**Q9. a = “3.5”, b = “1.5”일 때, print(a + b)의 실행 결과는?**  
a = 3.5, b = 1.5가 아니기 때문에 실수형 데이터가 할당된 것이 아니다.  
문자열이 각 변수에 할당된 것이므로 print(a + b)는   
단순히 a문자열과 b문자열을 단순 붙이기(concatenate) 한 3.51.5 가 출력된다.  

**답 : ② 3.51.5**  


**Q10. 다음과 같이 코드를 작성했을 때, 실행 결과로 알맞은 것은?**  
```python
a = ‘3’         
b =float(a)     
print(b **int(a)) 
```
a에는 3이라는 문자가 들어갔고,  
b는 a에 들어있는 3이라는 문자를 실수형으로 변환하여 저장했다.  
print()함수 안에 a를 정수형으로 변환하였기 때문에 b의 세제곱으로 계산된다.  
따라서 결과는 실수형인 27.0으로 출력된다.  

**답 : ③ 27.0**  


### ■ Chapter 3  

**Q3. 다음 코드의 실행 결과를 쓰시오.**  
```python
first = ["egg", "salad", "bread", "soup", "canafe"]
second = ["fish", "lamb", "pork", "beef", "chicken"]
third = ["apple", "banana", "orange", "grape", "mango"]

order = [first, second, third]
john = [order[0][:-2], second[1::3], third[0]] 
   # [[“egg”, “salad”, “bread”], [“lamb”, “chicken”], [“apple”]]
del john[2] # 마지막 행에 있는 1차원 리스트 통째로 삭제
john.extend([order[2][0:1]]) # third의 첫 번째 원소인 ‘apple’을 새로운 리스트로 3행에 추가
print(john)
```
first, second, third 리스트를 order에 원소로 넣음으로써   
3개의 행을 가진 2차원 리스트를 만들었다.  
john에는 각 리스트에서 원소들을 선택적으로 골라서 2차원 리스트를 만들었다.  
extend() 함수는 리스트의 덧셈 연산과 같다. 기존 리스트에 새로운 리스트를 합치는 기능(값 추가X).  

**실행 결과 : [["egg", "salad", "bread"], ["lamb", "chicken"], ["apple"]]**   

**Q4. 다음 코드의 실행 결과를 쓰시오.**  
```python
list_a = [3, 2, 1, 4]
list_b = list_a.sort()
print(list_a, list_b)
```
sort() 함수는 리스트에 있는 값들의 순서를 오름차순으로 변환하는 함수이다.  
그러나 반환 값은 없다.  
리스트를 반환해주는 정렬 함수는 sorted()이다. (sorted()는 내장함수)  
list_b = sorted(list_a) 이렇게 하면 list_b에 정렬된 list_a가 들어가게 된다.  
(주의, list_a는 원본 그대로이다.)  

원본변경O, 반환값X – list.sort()  
원본변경X, 반환값O – sorted(list)  

**답 : [1, 2, 3, 4] None**  


**Q8. 다음 코드의 실행 결과를 쓰시오.**  
```python
num = [1, 2, 3, 4]  
print(num *2)  
```
리스트의 곱셈 연산은 기준 리스트에 n을 곱했을 때, 같은 리스트를 n배만큼 늘려준다.  
따라서 위 코드는 num 리스트를 2배 만큼 늘려준다.  

**답 : [1, 2, 3, 4, 1, 2, 3, 4]**  


**Q10. 다음과 같이 코드를 작성했을 때, 실행 결과로 알맞은 것은?**  
```python
 list_a = ['Hankook', 'University', 'is', 'an', 'academic', 
	'institute', 'located', 'in', 'South Korea']
 list_b = [ ]

 for i in range(len(list_a)) :
    if i % 2 != 1 :
        list_b.append(list_a[i])
 print(list_b)
```
append() 함수를 사용하면 리스트 맨 끝 인덱스에 새로운 값을 추가할 수 있다.  
i가 짝수인 경우 list_b에 원소를 맨 끝에 추가하는 작업을 list_a의 길이만큼 반복한다.  

**답 : ③ ['Hankook', 'is', 'academic', 'located', 'South Korea']**  


### ■ Chapter 4

**Q6. 다음 코드의 실행 결과를 쓰시오.**  
```python
result = 0
for i in range(5, -5, -2) :
    if i < -3 :
        result += 1
    else :
        result -= 1
print(result)
```
range()는 마지막 번호의 마지막 숫자 바로 앞까지 리스트를 만든다.  
즉, range(5, -5, -2)는 5부터 –4까지 2씩 감소하는 리스트를 의미한다. [5, 3, 1, -1, -3]  
따라서 i가 –3미만인 경우가 없으므로 –5가 출력된다.  

**답 : -5**  


**Q8. 다음 코드의 실행 결과를 쓰시오.**  
```python
first_value = 0
second_value = 0
for i in range (1, 10) :
    if i is 5 :
        continue
        first_value = i
    if i is 10 :
        break
        second_value = i
print(first_value + second_value)
```
각 조건문 안에 continue(남은 명령을 건너뛰고 다음 반복문을 수행),   
break(논리적으로 반복을 종료)가 있으므로 반복문 안에 있는 조건문이 만족하더라도   
first_value = i, second_value = i 는 수행되지 않는다.  

**답 : 0**  


**Q10. 다음 함수는 작업의 상태를 나타내는 함수이다. 코드의 실행 결과를 쓰시오.**  
```python 
def work_status(task, worker, day) :
    rest_task = task
    for k in range(day) :
        if rest_task > 0 :
            rest_task = rest_task - worker
        elif rest_task <= 0 :
            print("Task end")

        if rest_task > 0 :
            print("Hire more workers")

work_status(100, 11, 10) #1
work_status(100, 1, 10) #2
work_status(100, 9, 10) #3
work_status(100, 10, 10) #4
``` 
range(10)은 0~9까지 반복한다는 의미이다.  
#1에서는 100에서 11을 9번 빼면 1이 되고, 10번째에 –10이 되어 for문 밖으로 탈출한다.  
#2에서는 100에서 1을 10번 빼면 90이 되고 for문 밖에 if문에서 “Hire more workers”를 출력한다.  
#3에서는 100에서 9를 10번 빼면 10이 되고 for문 밖에 if문에서 “Hire more workers”를 출력한다.  
#4에서는 100에서 10을 9번 빼면 10이 되고, 10번째에 0이 되어 for문 밖으로 탈출한다.  

**답)**  
**Hire more workers**  
**Hire more workers**  


### ■ Chapter 5  

**Q11. 다음 코드의 실행 결과를 쓰시오.**  
```python
def test(x, y) :
    tmp = x
    x = y
    y = tmp 
    return y.append(x)

x = ["y"]
y = ["x"]
test(x, y)
print(y)
```
파이썬은 함수 안 변수 호출방식을 객체 호출 방식(call by object reference)을 사용한다.  
새로운 값을 할당하기 전까지는 기존에 넘어온 인수 객체의 주소값을 쓰는 방식이라고 이해하면 된다.  
* 새로운 값 할당 전 : Call by reference  
* 새로운 값 할당 후 : Call by value  

**답 : ['x']**  


**Q15. 다음 코드의 실행 결과를 쓰시오.** 
```python
def exam_func():
    x = 10  #1
    print("Value : ", x)

x = 20  #2
exam_func()
print("Value : ", x)
```
#1에 있는 x는 새로운 값(10)이 할당되어 함수 내의 지역 변수가 되었다.  
#2에 있는 x는 함수 밖에 있는 변수이므로 #1의 x가 가리키는 메모리 주소와 다르다.  
따라서 함수의 호출과 관계없이 #2의 x는 20으로 출력된다.  

**답)**  
**Value :  10**  
**Value :  20**  


### ■ Chapter 6  
  
**Q2. 다음 코드의 실행 결과로 알맞은 것은?**  
```python
fact ="Python is funny"

print(str(fact.count('n') + fact.find('n') + fact.rfind('n')))
```
count(‘찾을 문자열’) : ‘찾을 문자열’이 몇 개 들어 있는지 개수 반환   
find(‘찾을 문자열’) : ‘찾을 문자열’이 왼쪽 끝부터 시작하여 몇 번째에 있는지 반환 (0부터 시작)  
rfind(‘찾을 문자열’) : find()함수와 반대로 오른쪽 끝부터 시작하여 몇 번째에 있는지 반환 (0부터 시작)  

※ 주의 : 역순으로 인덱싱 하지 않을 것. 오른쪽 끝에 있는 ‘찾을 문자열’이 왼쪽부터 몇 번째에 있는지를 계산하면 된다.   

따라서, 3 + 5 + 13 을 계산한 것을 string으로 바꾸어 출력했음을 알 수 있다.  

**답 : ② 21**  


**Q5. 다음 코드의 실행 결과를 쓰시오.**  
```python
a = '10'
b = '5-2'.split('-')[1]

print(a * 3 + b)
```
b는 ‘5-2’가 ‘-’를 기준으로 분리되므로 [‘5’, ‘2’]인데 1번 인덱스이므로 b = ‘2’ 가 된다.  
문자열을 곱하거나 더한 것은 ‘반복’일 뿐이므로, a를 3번 반복하고 b를 덧붙인 것이다.  

**답 : 1010102**  


**Q7. 다음 코드의 실행 결과를 쓰시오.**  
```python
a = "abcd e f g"
b = a.split()
c = (a[:3][0])
d = (b[:3][0][0])

print(c + d)
```
b = [‘abcd’, ‘e’, ‘f’, ‘g’]  
c = ‘abcd’  
d = [‘abcd’, ‘e’, ‘f’, ‘g’]  

**답 : aa**  


* **Ref**  
데이터 과학을 위한 파이썬 프로그래밍 (한빛아카데미 / 최성철)  
