## OPEN API 이용 어플리케이션 개발  
* 특정 검색어를 입력하여 Open API 에 요청 후 결과를 출력  
* Open API는 **공공데이터포털** 에 있는 '**전국안전비상벨위치표준데이터**' 를 사용함  
https://www.data.go.kr/dataset/15028206/standard.do?mypageFlag=Y  
\- 서비스 유형 : **REST**  
\- 데이터 포맷 : **JSON + XML**


DTO는 아래와 같이 작성하였다.
```java
package dduwcom.mobile.report02_02_20170995;

import java.io.Serializable;

public class SafeBellDto implements Serializable {

    private int _id;
    private String safeBellNo;
    private String location;
    private String address;

    public SafeBellDto() { }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSafeBellNo() {
        return safeBellNo;
    }

    public void setSafeBellNo(String safeBellNo) {
        this.safeBellNo = safeBellNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
```

XMLParser는 아래와 같이 작성하였다.  
```java
package dduwcom.mobile.report02_02_20170995;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class SafeBellXmlParser {

    public enum TagType { NONE, SAFEBELLNO, LOCATION, ADDRESS }

    public SafeBellXmlParser() {
    }

    public ArrayList<SafeBellDto> parse(String xml) {

        ArrayList<SafeBellDto> resultList = new ArrayList();
        SafeBellDto dto = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            dto = new SafeBellDto();
                        } else if (parser.getName().equals("safeBellManageNo")) { // 안전비상벨번호
                            if (dto != null) tagType = TagType.SAFEBELLNO;
                        } else if (parser.getName().equals("itlpc")) { // 지역
                            if (dto != null) tagType = TagType.LOCATION;
                        } else if (parser.getName().equals("rdnmadr")) { // 주소
                            if (dto != null) tagType = TagType.ADDRESS;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            resultList.add(dto);
                            dto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case SAFEBELLNO:
                                dto.setSafeBellNo(parser.getText());
                                break;
                            case LOCATION:
                                dto.setLocation(parser.getText());
                                break;
                            case ADDRESS:
                                dto.setAddress(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
```

요청할 address는 아래와 같이 설정해주었다.  
```java
        // onCreate 내부
        apiAddress = getResources().getString(R.string.api_url) + "?serviceKey=" + getResources().getString(R.string.secret_key) + "&insttNm=";
        
        // 생략 . . .
        
        // onClick 내부
        case R.id.btnSearch:

        if (!isOnline()) {
            Toast.makeText(MainActivity.this, "네트워크가 설정되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        } // 네트워크 환경 조사

        query = etTarget.getText().toString();
        try {
             new NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
        }
        break;
```


### ⬛️ 실행결과 
![Screenshot_1573325503](https://user-images.githubusercontent.com/50273050/68533727-ce8fb880-036f-11ea-86ce-f8ee19af7194.png)  


### ⬛️ 주의할 점  
* **AndroidManifest.xml** 설정을 반드시 확인할 것!  
1. **퍼미션 설정**  
```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET"/>
```
2. **Android 9.0(Pie) 일 경우 http를 지원하지 않음**  
```xml
    <!-- 서버가 지원할 경우 https로 주소 변경 -->
    android:usesCleartextTraffic="true" 
```

* **TimeOut 시간** 을 조정해줄 것!  
이 API는 유독 xml을 요청하는데에 시간이 많이 걸렸다.. **getNetworkConnection** 을 바꿔줄 것!
```java
    // 넉넉하게 60초로 잡아준다. 1000ms == 1s
    conn.setReadTimeout(60000); 
    conn.setConnectTimeout(60000);
```

* **Log.d** 로 URL address가 올바르게 요청되는지 확인할 것!  
```java
   public static final String TAG = "MainActivity";
   protected String doInBackground(String... strings) {
       String address = strings[0];
       Log.d(TAG, address);
       String result = downloadContents(address);
       if (result == null) return "Error!";
       return result;
   }
```

* (+) CustomAdapter class 내부에서 ViewHolder는 findViewById() 호출 감소를 위해 필수로 사용하자!  
