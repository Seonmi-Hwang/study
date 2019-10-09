package mobile.exam.network.sample.openapi_with_parser;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by cooling
 */

public class NaverBookDto {

    private int _id;
    private String title;
    private String author;
    private String link;
    private String imageLink;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String imageLink) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);     // 문자열에 HTML 태그가 포함되어 있을 경우 제거 후 일반 문자열로 변환
        return spanned.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  getTitle() + " (" + author +")";
    }
}
