package dduwcom.mobile.ma02_20170995;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/* 안전비상벨 Open API에서 가져온 XML에서 필요한 정보만 가져오기 위한 XMLParser */
public class SafeBellXmlParser {

    public enum TagType { NONE, SAFEBELLNO, LATITUDE, HARDNESS }
    private final static String FAULT_RESULT = "faultResult";//　잘못　날라왔을　경우
    private final static String ITEM_TAG = "item";
    private final static String SAFEBELLNO_TAG = "safeBellManageNo";
    private final static String LATITUDE_TAG = "latitude";
    private final static String HARDNESS_TAG = "hardness";

    private XmlPullParser parser;
    private double curLatitude;
    private double curLongitude;

    public SafeBellXmlParser(LatLng latLng) {
//        xml 파서 관련 변수들은 필요에 따라 멤버변수로 선언 후 생성자에서 초기화
//        파서 준비
        XmlPullParserFactory factory = null;

        curLatitude = latLng.latitude;
        curLongitude = latLng.longitude;

        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
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
                        String tag = parser.getName();
                        if (tag.equals(ITEM_TAG)) {
                            dto = new SafeBellDto();
                        } else if (tag.equals(SAFEBELLNO_TAG)) { // 안전비상벨번호
                            if (dto != null) tagType = TagType.SAFEBELLNO;
                        } else if (tag.equals(LATITUDE_TAG)) { // 위도
                            if (dto != null) tagType = TagType.LATITUDE;
                        } else if (tag.equals(HARDNESS_TAG)) { // 경도
                            if (dto != null) tagType = TagType.HARDNESS;
                        } else if (tag.equals(FAULT_RESULT)) {   // 정상적인 xml문서가 아닌
                            return null;  // ex)"유효하지 않은 키 값입니다." 인 xml문서가 반환되었을 경우
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            if (distance(curLatitude, curLongitude, Double.parseDouble(dto.getLatitude()), Double.parseDouble(dto.getHardness())) != -1)
                                resultList.add(dto);
                            dto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case SAFEBELLNO:
                                dto.setSafeBellNo(parser.getText());
                                break;
                            case LATITUDE:
                                dto.setLatitude(parser.getText());
                                break;
                            case HARDNESS:
                                dto.setHardness(parser.getText());
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
        Log.d("안전비상벨 개수 : ", String.valueOf(resultList.size()));
        return resultList;
    }

    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344; // km

        if (dist > 1) return -1; // 1km 내에 있는 안전비상벨만.

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
