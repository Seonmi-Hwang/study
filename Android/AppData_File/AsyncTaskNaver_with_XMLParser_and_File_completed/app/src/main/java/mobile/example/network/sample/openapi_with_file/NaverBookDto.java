package mobile.example.network.sample.openapi_with_file;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class NaverBookDto implements Serializable {

    private int _id;
    private String title;
    private String author;
    private String imageLink;
    private String imageFileName;       // 외부저장소에 저장했을 때의 파일명

    public NaverBookDto() {
        this.imageFileName = null;      // 생성 시에는 외부저장소에 파일이 없으므로 null로 초기화
    }

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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
//        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  _id + ": " + title + " (" + author + ')';
    }
}
