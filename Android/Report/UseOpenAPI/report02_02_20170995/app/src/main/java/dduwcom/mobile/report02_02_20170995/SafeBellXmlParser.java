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
