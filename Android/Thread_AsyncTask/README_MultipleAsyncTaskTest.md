# Multiple AsyncTask  
앞서 배운 Network 기능과 AsyncTask Class를 이용하여 앱 구현  

* **DataAsyncTask**  
**[DOWNLOAD DATA]** 버튼을 누르면 주소창에 입력된 주소에 접속하여 수신한 문자열을 TextView(tvImageLink)에 출력  
데이터를 정상 수신하였을 경우 [DOWNLOAD IMAGE] 버튼 활성화  

* **ImageAsyncTask**  
**[DOWNLOAD IMAGE]** 버튼을 누르면 tvImageLink에 기록하고 있는 주소에 접속  
이미지를 다운로드 후 ivImage에 출력  

* **DirectDownAsyncTask**  
**[DIRECT DOWNLOAD]** 버튼을 누를 경우 앞서 구현한 두 가지 기능을 차례대로 구현  
ivImage에 이미지 출력, tvImgLink에는 수신한 이미지 주소 출력  

* **[RESET]** 버튼을 누르면 초기화

⬛️ **AsyncTask Class**  
Thread와 Handler의 사용 대신 UI 상에서 간단하게 비동기 작업을 수행할 수 있도록 도와주는 클래스  
Thread 클래스와 달리 **UI 요소를 직접 접근**하여 사용 가능  

* **AsyncTask<Param1, Param2, Param3>** 을 상속  
\- Param1 : AsyncTask에 전달할 자료형 **(입력)**  
\- Param2 : **작업 진행 상태**를 표시할 자료형  
\- Param3 : AsyncTask 수행 후 **반환**할 결과의 자료형  

• **AsyncTask의 실행**  
AsyncTask<Params, Progress, Result> execute(Params… params)  
• **AsyncTask의 취소**  
boolean cancel(boolean mayInterruptIfRunning)  
• **AsyncTask 취소 확인**  
boolean isCancelled()  
• **AsyncTask 취소 후 처리**  
void onCancelled(Result), void onCancelled()  
• **AsyncTask의 실행 및 작업 종료 대기**  
Result get ( [long timeout, TimeUnit unit] );  
• **AsyncTask 작업 상태 조사 – PENDING/RUNNING/FINISHED**  
AsyncTask.Status getStatus();  
  
![AsyncTask](https://user-images.githubusercontent.com/50273050/66022960-8a1c2c00-e52a-11e9-8cff-4134e15ae13e.JPG)  


⬛️ **실행결과**  

### 첫 화면   
![첫 화면](https://user-images.githubusercontent.com/50273050/66023524-62c65e80-e52c-11e9-951c-afb30a4cb612.png)  

### [DIRECT DOWNLOAD] 버튼 클릭 or [DOWNLOAD DATA] -> [DOWNLOAD IMAGE]   
![실행화면](https://user-images.githubusercontent.com/50273050/66023525-62c65e80-e52c-11e9-8ad9-f65db2e82f20.png)  

