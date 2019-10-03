# File I/O    
파일 입출력에 대한 directory  

⬛️ **Stream**  
* **Stream?**  
입력과 출력을 바이트(byte)들의 **흐름** 으로 생각하는 것 (ex. file, device, network)  

* **Standard stream**  
표준 입력 스트림(stdin) - 키보드  
표준 출력 스트림(stdout) - 모니터 화면  
표준 오류 스트림(strerr) - 모니터 화면  

\- C programming  
```c
scanf(); // 표준 입력 스트림
...
printf(); // 표준 출력 스트림 
...
fprintf(stderr, ..); // 표준 오류 스트림
```

\- Java programming  
```java
System.in // 표준 입력 스트림
...
System.out // 표준 출력 스트림
...
System.err // 표준 오류 스트림
```

\- Python programming  
```python
input() # 표준 입력 스트림
...
print() # 표준 출력 스트림
...
print("..", file=sys.stderr) # 표준 오류 스트림
```

⬛️ **파일(Linux)**   
* **파일 접근 권한(ACL) 보호**    
기본적인 개념이므로 pass  

* **Stream redirection**  
입출력 재지정  

|종류   | file descript number | 문자  | 
|:-----:|:--------------------:|:-----:|
|stdin  | 0                    | < |
|stdout | 1                    | >  | 
|stderr | 2                    | 2>  | 

* **파일 쓰기**  
**open(파일경로, 모드)**  
\- 파일 경로 : 입출력 대상의 이름. 디렉토리 경로 포함 가능하며 파일명만 있으면 현재 디렉토리에서 찾음  
\- 모드 : 읽기, 쓰기, 추가 등 파일로 무엇을 할 것인가를 지정 (r, w, a, x)  
(a : 파일에 데이터를 추가한다, x : 파일에 기록하되 파일이 이미 있으면 실패한다)  

모드 뒤에는 파일의 종류를 지정하는 문자를 씀  
(t : text file, b : binary file)  

default : 'rt'  
