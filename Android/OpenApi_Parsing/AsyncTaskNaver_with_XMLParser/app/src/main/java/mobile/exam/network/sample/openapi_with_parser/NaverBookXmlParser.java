package mobile.exam.network.sample.openapi_with_parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class NaverBookXmlParser {
    private enum TagType { NONE, TITLE, AUTHOR, LINK, IMAGE_LINK };

    private final static String FAULT_RESULT = "faultResult";
    private final static String ITEM_TAG = "item";
    private final static String TITLE_TAG = "title";
    private final static String AUTHOR_TAG = "author";
    private final static String LINK_TAG = "link";
    private final static String IMAGE_LINK_TAG = "image";

    private XmlPullParser parser;

    public NaverBookXmlParser() {
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

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
}
