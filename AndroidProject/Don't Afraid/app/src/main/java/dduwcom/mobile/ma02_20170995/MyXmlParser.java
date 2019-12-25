package dduwcom.mobile.ma02_20170995;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class MyXmlParser {

    //    xml에서 읽어들일 태그를 구분한 enum  → 정수값 등으로 구분하지 않고 가독성 높은 방식을 사용　（열거형　상수　이름으로　구분）
    private enum TagType { NONE, CITY, COUNT }     // 해당없음, city, count
//    parsing 대상인 tag를 상수로 선언 (Parsing 중 관심대상인 tag는 상수로 정의할 것)
    private final static String FAULT_RESULT = "faultResult";//　잘못　날라왔을　경우
    private final static String ITEM_TAG = "City";
    private final static String NAME_TAG = "city-name";
    private final static String COUNT_TAG = "city-count";

    private XmlPullParser parser;

    public MyXmlParser() {
//        xml 파서 관련 변수들은 필요에 따라 멤버변수로 선언 후 생성자에서 초기화
//        파서 준비
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<LocalSexOffender> parse(String xml) {
        ArrayList<LocalSexOffender> resultList = new ArrayList();
        LocalSexOffender dto = null;
        TagType tagType = TagType.NONE;     //  태그를 구분하기 위한 enum 변수 초기화

        try {
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType(); // 태그 유형 구분 변수 준비

            while (eventType != XmlPullParser.END_DOCUMENT) {  // parsing 수행 - for 문 또는 while 문으로 구성
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://　없어도　됨．
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals(ITEM_TAG)) {    // 새로운 항목을 표현하는 태그(<city>)를 만났을 경우
                            dto = new LocalSexOffender(); // dto 객체 생성
                        } else if (tag.equals(NAME_TAG)) { // 관심 태그 <city-name>를 만났을 경우
                            tagType = TagType.CITY; // 태그 타입 지정
                        } else if (tag.equals(COUNT_TAG)) { // 관심 태그 <city-count>를 만났을 경우
                            tagType = TagType.COUNT;
                        } else if (tag.equals(FAULT_RESULT)) {   // 정상적인 xml문서가 아닌
                            return null;  // ex)"유효하지 않은 키 값입니다." 인 xml문서가 반환되었을 경우
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals(ITEM_TAG)) { // 한 항목이 끝나는 태그를 만나면
                            resultList.add(dto); // dto를 list에 추가
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {       // 태그의 유형에 따라 dto 에 값 저장
                            case CITY:
                                dto.setCity(parser.getText());
                                break;
                            case COUNT:
                                dto.setCount(parser.getText());
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
