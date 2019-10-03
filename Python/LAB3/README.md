# Python Basic 3  
Python의 기초를 다지기 위한 directory  
String, List & Tuple  

⬛️ **문자열 관리**  
* **첨자**  
파이썬은 문자열을 기본 타입으로 지원 \-> 문자열 조작을 위한 다양한 명령 제공  
문자열을 구성하는 개별문자를 읽을 때는 [] 괄호와 문자의 위치인 첨자를 이용   
**첨자는 앞에서 셀 수도 있고 뒤에서 셀 수도 있음**  
문자열은 문자로 구성된 리스트 **(but, immutable)** 의 일종이기 때문에 for문으로 순회 가능  
*(List는 원래 mutable 이다.)*  

![image](https://user-images.githubusercontent.com/50273050/66102889-d4ff7780-e5ee-11e9-93c1-f3bf09b2b5a0.png)  

```python
s = 'python'

print(s[2]) # 앞에서 세기
print(s[-2]) # 뒤에서 세기

for c in s : # 문자열 내부를 for문으로 순회 가능
  print(c, end = ', ') # end='?' : 출력 후 끝부분에 \n 대신 ?를 붙여라.

# 결과 : p, y, t, h, o, n,
```

* **슬라이스(Slice)**  
부분 문자열 추출 시 사용 (원본과는 독립적임)  
[] 괄호 안에 시작, 끝, 증가값 지정(like range() 함수)  

```python
s ='python'

print(s[2:5]) # 3번째부터 5번째까지 // 결과 : tho
print(s[2:-2]) # 3번째에서 뒤에서 2번째 문자 직전까지 // 결과 : th
```


* **문자열 메서드**  
```python
s = "python programming"

# 검색
print(len(s)) # 문자열의 길이 조사. len 함수는 내장이므로 s.len()이 아님.
print(s.find('o')) # 문자 또는 부분 문자열의 위치를 앞에서부터 조사. 없으면 -1
print(s.rfind('o')) # 뒤에서 부터 조사
print(s.index('r')) # 문자 또는 부분 문자열을 찾음. 그러나 없으면 예외 발생.
print(s.count('n')) # 특정 문자의 개수를 센다. 부분 문자열 검색도 가능.

# 조사1 (True or False 로 반환)
print('a' in s)
print('pro' in s) # 부분 문자열도 조사 가능
print('x' not in s) # "not in"은 포함되어 있지 않은지를 조사

# 조사2 (True or False 로 반환)
name = "차은우"
if name.startswith("차") : # 문자열이 "차"로 시작하는지 점검
  print("~")
  
file = "boy.jpg"
if file.endswith(".jpg") : # 문자열이 ".jpg"로 끝나는지 점검
  print("~")
```
* 조사 (추가 메서드)  
![image](https://user-images.githubusercontent.com/50273050/66128264-7951e000-e628-11e9-814b-de152da3e66d.png)   


⬛️ **리스트와 튜플**  

