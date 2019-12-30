package dduwcom.mobile.ma02_20170995.safebell;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/* CCTV Open API에서 가져온 XML에서 필요한 정보만 가져오기 위한 XMLParser */
public class CctvXmlParser {

    public enum TagType { NONE, INSTITUTION, PURPOSE, NUMBER, OLDADDRESS, PHONE }

    public ArrayList<CctvDto> parse(String xml) {

        ArrayList<CctvDto> resultList = new ArrayList();
        CctvDto dto = null;

        CctvXmlParser.TagType tagType = CctvXmlParser.TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            dto = new CctvDto();
                        } else if (parser.getName().equals("institutionNm")) { // 관리기관
                            if (dto != null) tagType = CctvXmlParser.TagType.INSTITUTION;
                        } else if (parser.getName().equals("installationPurpsType")) { // 설치목적
                            if (dto != null) tagType = CctvXmlParser.TagType.PURPOSE;
                        } else if (parser.getName().equals("cctvNumber")) { // 설치개수
                            if (dto != null) tagType = CctvXmlParser.TagType.NUMBER;
                        } else if (parser.getName().equals("lnmadr")) { // 촬영방면
                            if (dto != null) tagType = CctvXmlParser.TagType.OLDADDRESS;
                        } else if (parser.getName().equals("phoneNumber")) { // 전화번호
                            if (dto != null) tagType = CctvXmlParser.TagType.PHONE;
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
                            case INSTITUTION:
                                dto.setInstitution(parser.getText());
                                break;
                            case PURPOSE:
                                dto.setPurpose(parser.getText());
                                break;
                            case NUMBER:
                                dto.setNumber(parser.getText());
                                break;
                            case OLDADDRESS:
                                dto.setOldAddress(parser.getText());
                                break;
                            case PHONE:
                                dto.setPhone(parser.getText());
                                break;
                        }
                        tagType = CctvXmlParser.TagType.NONE;
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
