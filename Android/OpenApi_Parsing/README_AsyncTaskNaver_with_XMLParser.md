# AsyncTaskNaver with XMLParser  
* 네이버 Open API의 검색 결과를 Parsing 하는 앱    

⬛️ **NAVER Open API 사용 전 준비**  

* **네이버 개발자 사이트(dev.naver.com)에서 애플리케이션 등록 후 진행**  

\- (로그인 후 위 사이트에서) Client ID 및 Client Secret을 발급 받아 strings.xml에 설정  
```java
<string name="app_name">Naver OpenAPI</string>
<string name="api_url">https://openapi.naver.com/v1/search/book.xml?display=50&amp;start=1&amp;query=</string>
<string name="client_id">6XbbSB62vSeZ6732lpcf</string>
<string name="client_secret">1gxoE8m4RF</string>
```

\- 애플리케이션 이름은 임의로 지정 (ex. openApiTest)  

\- 사용 API는 [검색] 선택  

\- 비로그인 Open API 서비스 환경은 [안드로이드] 선택 후 프로젝트의 패키지명 기록


⬛️ **실행결과**  

programming을 입력하고 [SEARCH] 버튼 누른 후 실행된 화면이다.  

![image](https://user-images.githubusercontent.com/50273050/66453304-d4f3f180-ea9e-11e9-8755-3aacf71d4750.png)  


* **NaverBookXmlParser 클래스**  
```java
    public ArrayList<NaverBookDto> parse(String xml) {
        ArrayList<NaverBookDto> resultList = new ArrayList<>();
        NaverBookDto dto = null;
        TagType tagType = TagType.NONE;

        try {
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG :
                        String tag = parser.getName();
                        if (tag.equals(ITEM_TAG)) {
                            dto = new NaverBookDto();
                        } else if (tag.equals(TITLE_TAG)) {
                            if (dto != null) tagType = TagType.TITLE;
                        } else if (tag.equals(AUTHOR_TAG)) {
                            tagType = TagType.AUTHOR;
                        } else if (tag.equals(LINK_TAG)) {
                            if (dto != null) tagType = TagType.LINK;
                        } else if (tag.equals(IMAGE_LINK_TAG)) {
                            tagType = TagType.IMAGE_LINK;
                        } else if (tag.equals(FAULT_RESULT)) {
                            return null;
                        }
                        break;

                    case XmlPullParser.END_TAG :
                        if (parser.getName().equals(ITEM_TAG)) {
                            resultList.add(dto);
                        }
                        break;

                    case XmlPullParser.TEXT :
                        switch(tagType) {
                            case TITLE :
                                dto.setTitle(parser.getText());
                                break;
                            case AUTHOR :
                                dto.setAuthor(parser.getText());
                                break;
                            case LINK :
                                dto.setLink(parser.getText());
                                break;
                            case IMAGE_LINK :
                                dto.setImageLink(parser.getText());
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
```

* **Naver Open API에서 주의할 점**  
다른 Open API를 사용할 때는 이전에 쓰던 네트워크 코드를 그대로 사용하면 되는데,
네이버 Open API를 사용할 때는 '네이버 사용 시 설정 필요' 아래 2줄의 코드가 필요하다.   

```java
    protected String downloadNaverContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        // 클라이언트 아이디 및 시크릿 그리고 요청 URL 선언
        String clientId = getResources().getString(R.string.client_id);
        String clientSecret = getResources().getString(R.string.client_secret);

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
           /* 네이버 사용 시 설정 필요 */
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

```
