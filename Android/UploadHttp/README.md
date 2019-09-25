# Upload Http  
영화진흥위원회의 OpenAPI를 이용하여 영화 정보를 받아오는 애플리케이션이다.  

URL 주소는 아래와 같이 설정해준다.  
```java
String address = getResources().getString(R.string.url) + "?key=" + getResources().getString(R.string.key)
                + "&targetDt=" + etDate.getText().toString();
```  

아래 코드는 네트워크가 사용 가능한지 확인하는 코드이다.  
```java
private boolean isOnline() {
   ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
   NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
   return (networkInfo != null && networkInfo.isConnected());
}
```  

* **실행결과**  
![uploadHttp](https://user-images.githubusercontent.com/50273050/65614757-2312ea80-dff3-11e9-9c8c-b813dc112f15.png)  

* **주의사항**  
안드로이드 9.0 버전부터 http protocol을 지원(?)하지 않는 것으로 파악됨.  
https를 지원함.  

* **해결방안**  
http에 s를 붙여서 https로 만듦.  
usesClearTextTraffic을 true로 설정하여 protocol을 변경할 수 있도록 함.  

* **확장**  
movieCd(영화코드) 변수를 통해 영화의 세부정보를 불러올 수 있도록 textView와 button을 만들어 입력받는다.  
