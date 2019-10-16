# AsyncTaskNaver with XMLParser and File    
* **네이버의 책 검색을 수행하는 앱**  

⬛️ **Open API의 활용구조**  
* 필요한 구성요소?  
\- OpenAPI용 AsyncTask  
\- XML Parser  
\- Custom Adapter(layout)  
\- Image 용 AsyncTask  
\- Image 파일 저장을 위한 파일 관리 클래스  

* 작동방식  
![image](https://user-images.githubusercontent.com/50273050/66912764-2b889f00-f04e-11e9-900d-399f847ddcdb.png)  


### [작성부분] 
내가 직접 작성한 부분이며, 실제 올린 파일은 교수님이 작성하신 파일이다.  
* **MyBookAdapter의 getView()**  
```java
        /*작성할 부분*/
        /*dto 에 저장하고 있는 이미지 주소를 사용하여 이미지 파일이 내부저장소에 있는지 확인
         * ImageFileManager 의 내부저장소에서 이미지 파일 읽어오기 사용
         * 이미지 파일이 있을 경우 bitmap, 없을 경우 null 을 반환하므로 bitmap 이 있으면 이미지뷰에 지정
         * 없을 경우 GetImageAsyncTask 를 사용하여 이미지 파일 다운로드 수행 */

        String url = dto.getImageLink();

        Bitmap bitmap = imageFileManager.getSavedBitmapFromInternal(url);

        if (bitmap != null) {
            viewHolder.ivImage.setImageBitmap(bitmap);
            Log.i(TAG, "Loaded from file!");
        } else { // bitmap이 null일 경우
            GetImageAsyncTask imageAsyncTask = new GetImageAsyncTask(viewHolder);
            try {
                imageAsyncTask.execute(url);
                Log.d(TAG, "Image downloading!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
```

* **MyBookAdapter의 GetImageAsyncTask 내부의 onPostExecute()**  
```java
            /*작성할 부분*/
            /*네트워크에서 다운 받은 이미지 파일을 ImageFileManager 를 사용하여 내부저장소에 저장
            * 다운받은 bitmap 을 이미지뷰에 지정*/
            if (bitmap != null) {
                imageFileManager.saveBitmapToInternal(bitmap, imageAddress);
                viewHolder.ivImage.setImageBitmap(bitmap);
            }
```

* **MainActivity의 listView onItemLongClick()**  
```java
               /* 작성할 부분 */
                /*롱클릭한 항목의 이미지 주소를 가져와 내부 메모리에 지정한 이미지 파일을 외부저장소로 이동
                * ImageFileManager 의 이동 기능 사용
                * 이동을 성공할 경우 파일 명, 실패했을 경우 null 을 반환하므로 해당 값에 따라 Toast 출력*/

                String newFileName = imgManager.moveStorage(resultList.get(position).getImageLink());

                if (newFileName != null) {
                    Toast.makeText(MainActivity.this, newFileName + " 이동 성공!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "이동 실패!", Toast.LENGTH_SHORT).show();
                }

                return true;
```


⬛️ **실행결과**  

![Naver OpenAPI with File 실행결과](https://user-images.githubusercontent.com/50273050/66913198-21b36b80-f04f-11e9-857f-de0f2259132d.png)  

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


