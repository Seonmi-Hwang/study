# Get data via http & Show data through the web  

## 1. Get price data via http requests
Use urllib.request.urlopen  
https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,JPY,EUR,KRW  
https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,JPY,EUR,KRW  

## 2. Put data into SQLite 
### Table Schema 
```sql
drop table if exists price_tbl;

create table price_tbl(
CurrentTimevarchar(20) not null,
CryptoCurrencyNamevarchar(10) not null,
TargetCurrencyNamevarchar(10) not null,
Price decimal(20,5) not null);
```
## 3. Show price trend of cryptocurrency through the web  
Price data for the display are already stored in DB.  
Example) http://localhost:8080/BTC/KRW  

* 아래 코드 참고 및 수정하여 사용  

[webserver_new.py]  
```python
import configuration
from socket import *
import sqlite3
​
def get_connection(configProps):
    try:
        conn = sqlite3.connect( configProps['DB.db'] )
        return conn
    finally:
        pass
​
def initdb(con):
    cursor = con.cursor() 
    cursor.execute("DROP TABLE IF EXISTS USER")
    cursor.execute("CREATE TABLE USER (id CHAR(16) PRIMARY KEY, password CHAR(16), nickname CHAR(16))" )
    cursor.close()
​
def finduser(uid,conn):
    return __finduser(uid,conn.cursor())
​
​
def __finduser(uid,cursor): 
    cursor.execute("SELECT id, password, nickname FROM USER WHERE id = '%s'" % uid) 
    userlist = cursor.fetchall() 
    cursor.close()
    return userlist 
​
def insertuser(uid, passwd, nickname,conn): 
    cursor = conn.cursor() 
    ulist = __finduser(uid,cursor) 
    if len(ulist)>0: 
        return False 
    cursor.execute("INSERT INTO USER VALUES (\'%s\', \'%s\', \'%s\')" % (uid, passwd, nickname)) 
    cursor.close()
    return True
​
​
def signup(val,conn): 
    if len(val) < 5: 
        return "Please enter your ID, password and nickname in url" 
​
    if insertuser(val[2], val[3], val[4],conn): 
        return "Thank you for signing up!" 
    else: 
        return "Duplicated ID" 
    
    
def signin(val,conn): 
    userlist = finduser(val[2],conn) 
    if len(userlist) < 1: 
        return "This ID does not exist" 
​
    record = list(userlist[0]) 
    if val[3] != record[1]: 
        return "The password is incorrect" 
​
    userjson = dict(); 
    userjson['id'] = record[0] 
    userjson['password'] = record[1] 
    userjson['nickname'] = record[2] 
    return json.JSONEncoder().encode(userjson) 
​
def parserequest(msg,conn): 
    msg = str.strip(msg) 
    if msg == '': return None; 
    msg = msg.split("\n")[0] 
    url = msg.split()[1] 
    print(url)
    val = url.split('/') 
    if len(val) < 4:
        return None; 
    
    if val[1] == 'signup': 
        return signup(val,conn) 
    elif val[1] == 'signin': 
        return signin(val,conn) 
    else:
        return None; 
   
if __name__ == "__main__": 
    c = configuration.get_web_configuration('config.props')
    db_c = configuration.get_db_configuration('config.props')
    PORT = int(c['web.port'])
    BUFSIZE = int(c['web.bufsize'])
​
    db_conn = get_connection(db_c)
    initdb(db_conn)
    db_conn.commit()
​
    listen_sock = socket(AF_INET, SOCK_STREAM) 
    listen_sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    listen_sock.bind(('', PORT)) 
    listen_sock.listen(1) 
​
    while 1: 
        conn, addr = listen_sock.accept() 
        data = conn.recv(BUFSIZE).decode("UTF-8") 
​
        rslt = parserequest(data,db_conn) 
        if (rslt == None): 
            rslt = "Please Sign in or Sign up"
​
        msg = """HTTP/1.1 200 OK 
​
            
            <html><body>%s</body></html>""" % rslt 
        conn.sendall(msg.encode("UTF-8")) 
        conn.close()
```

[config.props]  
```props
[DB_DEFAULT]
DB.host=localhost
DB.port=3306
DB.user=bigdatalab
DB.passwd=1234
DB.db=python_lab
[WEBSERVER_DEFAULT]
WEB.port=8088
WEB.bufsize=1024
```

[configuration.py]  
![image](https://user-images.githubusercontent.com/50273050/69321077-c4ca5700-0c85-11ea-99b0-9a01795e184b.png)

## [실행화면]   
**HW4_1을 약 30 ~ 40 정도 수행시킨 후 확인한 결과이다.** 

### 1. HW4_check_db.py와 HW4_check_web.py 실행 시 출력결과  
![출력결과1](https://user-images.githubusercontent.com/50273050/70376434-6d4a0d80-194c-11ea-9a9b-c4179ad2a6ee.JPG)  

### 2. HTML 형식으로 출력한 HW4_check_web.py 출력결과  
![출력결과2](https://user-images.githubusercontent.com/50273050/70376441-86eb5500-194c-11ea-84a8-ea818484987f.JPG)  

### 3. HW4_1 실행 시 출력결과
![HW4_1 실행화면](https://user-images.githubusercontent.com/50273050/70376451-9ec2d900-194c-11ea-98d8-5445e3a31689.JPG)  

### 4. HW4_2 실행 시 출력결과  
![HW4_2 실행화면](https://user-images.githubusercontent.com/50273050/70376456-ae422200-194c-11ea-9c2b-c91df10a0a4e.JPG)  

### 5. 웹 실행결과  
![웹실행결과](https://user-images.githubusercontent.com/50273050/70376471-bb5f1100-194c-11ea-97fb-eb7ba80f901e.JPG)  

## [주의사항]  
DB에는 모든 날짜들의 정보가 들어있다해도, 해당 날짜(년-월-일)에 맞는 **'오늘의'** 정보만 웹페이지에 출력할 것.



