# App Data with File  

⬛️ **Android의 데이터 저장 유형**  
* **기기저장소에 파일 저장**  
\- 내부 파일 저장소 : 해당 App 전용, 내부 캐시 파일 등  
\- 외부 파일 저장소 : 공유 데이터 (캡처한 사진 또는 다운로드 파일 등)  

**※ 주의! 외부 파일 저장소는 말그대로 외부에 있는 저장소(ex.sd카드)일 수 있으나,**  
**최근에 판매되고 있는 외부 저장소를 꽂을 수 없는 디바이스라도**   
**기기 자체 내부에 내부, 외부 저장소가 나뉘어 있다.**   

![image](https://user-images.githubusercontent.com/50273050/66915213-6d681400-f053-11e9-94dd-fc4de45d4b86.png)  


⬛️ **내부 저장소 파일 사용**  

\- 앱 전용의 파일 사용 시 적용  
\- 용량 부족 시 IOException 발생  
\- **파일 생성 및 준비**  
**getFilesDir()** : 앱 전용 위치의 경로   
=> data/data/패키지명/files/_filename_  
**getCacheDir()** : 앱 전용 캐쉬 위치에 파일 생성  
=> data/data/패키지명/cache/_filename_  

* **파일 쓰기(WRITE)**  
```java
   /* Bitmap 과 Bitmap 다운로드에 사용한 URL 을 전달받아 내부저장소에 JPG 파일로 저장 후
   파일 이름을 반환, 파일 저장 실패 시 null 반환 */
    public String saveBitmapToInternal(Bitmap bitmap, String url) {
        String fileName = null;
        try {
            fileName = getFileNameFromUrl(url); // 파일 이름 알아내기

            // 내부 저장소에 파일 생성 (지정한 위치에 파일 생성)
            File saveFile = new File(context.getFilesDir(), fileName);
            // file용 output 스트림 생성 (생성한 파일을 출력스트림에 연결)
            FileOutputStream fos = new FileOutputStream(saveFile);

            // 문자열을 파일에 기록할 경우
            // String data = "file contents!";
            // fos.write(data.getBytes());

            // 비트맵 이미지를 파일에 기록, Bitmap의 크기가 클 경우 quality를 조정
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileName = null;
        } catch (IOException e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }
```

File saveFile = new File(context.getFilesDir(), fileName);  
FileOutputStream fos = new FileOutputStream(saveFile);  

위 두 코드를 한 줄로 한 번에 수행하려면  
outputStream = openFileOutput(filename, Context.MODE_PRIVATE);  

```java
String filename = "myfile";
String fileContents = "Hello World!";
FileOutputStream outputStream;

try {
  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
  outputStream.write(fileContents.getBytes());
  outputStream.close();
} catch (Exception e) {
  e.printStackTrace();
}
```

* **파일 읽기(READ)**  
```java
   /* 이미지 다운로드에 사용하는 url 을 전달받아
    url 에 해당하는 내부 저장소에 이미지 파일이 있는지 확인 후 있으면 Bitmap 반환, 없을 경우 null 반환 */
    public Bitmap getSavedBitmapFromInternal(String url) {
        String fileName = getFileNameFromUrl(url); // "cat.jpg"

        String path = context.getFilesDir().getPath() + "/" + fileName;

       // 문자열일 경우의 읽기
        try {
            File readFile = new File(path);
            FileReader fileReader = new FileReader(readFile);
            BufferedReader br = new BufferedReader(fileReader);
            String line = "";
            while((line = br.readLine()) != null){
                Log.i(TAG, line);
            }
            br.close();
         } catch (IOException e) {
            e.printStackTrace();
         }

       // 비트맵일 경우의 읽기
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        Log.i(TAG, path);

        return bitmap; // file이 없으면 null값으로 반환
    }
```

* **파일 삭제(DELETE)**  
```java
    /* 내부저장소에 저장한 모든 임시 파일을 제거 */
    public void clearSaveFilesOnInternal() {
        // String fileName = "test";
        // File file = new File(context.getFilesDir() + "/" + fileName);
        // boolean result = file.delete();

        File internalStoragefiles = context.getFilesDir();
        File[] files = internalStoragefiles.listFiles();
        for (File target : files) {
            target.delete();
        }
    }
```

⬛️ **외부 저장소 파일 사용**  

\- SD카드 등 외부 저장소에 앱 전용 또는 공유 목적으로 파일 사용 시 적용   

* **퍼미션 추가(Permission)**  
READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE  
Android 4.4(API level 19) 이후 앱 전용 디렉토리 사용 시에는 퍼미션 불필요  
Android 4.3(API level 18) 지원이 필요할 경우 적용  
**=> 모든 안드로이드 기기에서 적용될 수 있도록 Manifest.xml에 아래와 같이 작성**   
```xml
<manifest ...>
  <!-- If you need to modify files in external storage, request WRITE_EXTERNAL_STORAGE instead. -->
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
                   android:maxSdkVersion="18" />
</manifest>                   
```

* **외부 저장소 사용 가능 확인**  
```java
    /* 외부 저장소가 읽기/쓰기가 가능한지 확인 */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false; // ex. sdcard mount가 안되어있을 경우
    }
```

공용 위치에 저장할 경우도 있으나 생략한다.  
* **전용 위치에 저장할 경우**  
앱 전용으로 지정된 위치에 파일 유형별로 디렉토리(세부폴더)가 생성됨  
\- 이미지 : DIRECTORY_PICTURES  
\- 영화 : DIRECTORY_MOVIE  
\- 벨소리 : DIRECTORY_RINGTONE  

```java
public File getPrivateAlbumStorageDir(Context context, String albumName) {
  // Get the directory for the app's private pictures directory.
  File file = new File(context.getExternalFilesDir(
          Enviroment.DIRECTORY_PICTURES), albumName); // directory명 지정(String)
  if (!file.mkdirs()) { // filename 생성
    Log.e(LOG_TAG, "Directory not created");
  }  
  return file; 
}
```


⬛️ **확인할 점**  

오른쪽 하단에 Device File Explorer를 클릭하여  
file이 내부저장소(data/data/패키지명/file/_filename_)에 저장되었는지,  
외부저장소(sdcard/Android/data/패키지명/files/_filename_)에 저장되었는지 확인한다.  
**=> 사용자에게 보여지는 화면에 나타난 부분만 image가 내부 저장소에 저장된다.**  
**=> 파일을 외부 저장소에 저장하는 moveStorage()메소드를 사용하여 저장한다.**  

```java
 public String moveStorage(String url) {
        if (!isExternalStorageWritable()) return null;

//        내부 저장소에 저장하고 있는 원본 파일 준비
        if(url != null) {
            String fileName = getFileNameFromUrl(url);
            File internalFile = new File(context.getFilesDir(), fileName);

//        외부 저장소에 저장할 복사본 파일 준비
            String newfileName = getCurrentTime() + IMG_EXT;     // 현재시간.jpg 로 파일이름 지정
            File externalFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), newfileName);

//        복사 수행
            boolean result = copyInternalToExternal(internalFile, externalFile);

//        정상적으로 복사 완료 시 내부저장소의 파일 삭제 및 외부저장소에 저장한 파일명 반환
            if (result) {
//                internalFile.delete();    // 파일을 외부저장소에 옮긴 후에도 사용할 수 있으므로 지우지 않음
                return newfileName;
            }
        }

        return null;
    }
```

또한 앱을 종료하거나 삭제했을 경우 외부, 내부 저장소에 저장된 파일들이 어떻게 변화하는지 살펴본다.  
**=> 앱을 종료했을 경우에는 두 저장소의 파일들이 모두 아무런 변화가 없다.**  
**=> 앱을 삭제했을 경우에는 두 저장소의 패키지 아래에 있는 폴더 및 파일들이 모두 삭제된다.**  


