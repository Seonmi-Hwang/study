# Download HTML  
* '다운로드' 버튼을 누르면 입력창에 입력된 google url로 들어가서 google html을 가져온다.  
* '이미지 다운로드' 버튼을 누르면 미리 설정된 url을 통해 google 이미지를 가져온다.  


⬛️ **실행결과**  

두 버튼을 모두 누른 후 실행된 화면이다.  

![captureHtml](https://user-images.githubusercontent.com/50273050/65611285-8b5ecd80-dfed-11e9-8fad-742b43ce11a5.png)  


* **Http 요청**  
```java
private String downloadUrl(URL url) throws IOException { 
  InputStreamstream =null; 
  HttpsURLConnectionconn=null; 
  Stringresult =null; 
  
  try { 
    conn = (HttpsURLConnection)url.openConnection(); // 서버 연결 설정 – MalformedURLException 
    conn.setReadTimeout(5000);// 읽기 타임아웃 지정 - SocketTimeoutException 
    conn.setConnectTimeout(5000);// 연결 타임아웃 지정 - SocketTimeoutException 
    conn.setRequestMethod("GET");// 연결 방식 지정 
    conn.setDoInput(true);// 서버 응답 지정 – default
    
    int responseCode = conn.getResponseCode(); // 서버 전송 및 응답결과 수신 
    if(responseCode !=HttpsURLConnection.HTTP_OK) { 
      throw new IOException("HTTP error code: "+responseCode); 
  }
    stream = conn.getInputStream();// 응답 결과 스트림 확인
    if (stream != null) { 
      result = readStream(stream,500); // 응답 결과 스트림을 문자열로 변환  –readStream 구현 필요 
    } 
  } finally { // 스트림 및 연결 종료 
     if (stream != null) { 
        stream.close(); 
     } 
     if (conn != null) { 
      conn.disconnect(); 
     } 
  } 
  return result;
}
```

* **InputStream의 String 변환**  

```java
public String readStream(InputStream stream){
		StringBuilder result = new StringBuilder();

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String readLine = bufferedReader.readLine();

			while (readLine != null) {
				result.append(readLine + "\n");
				readLine = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
```

* **inputStream의 이미지 변환**  

```java
InputStream is = null; 
... 
Bitmap bitmap = BitmapFactory.decodeStream(is); 
ImageView imageView = (ImageView)findViewById(R.id.image_view); 
imageView.setImageBitmap(bitmap);
```


* **Ref**  
https://developer.android.com/training/basics/network-ops/connecting.html
