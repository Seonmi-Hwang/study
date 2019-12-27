package dduwcom.mobile.ma02_20170995;

import java.io.Serializable;

/* 안전비상벨의 정보를 담을 DTO */
public class SafeBellDto implements Serializable {

    private int _id;
    private String safeBellNo;
    private String latitude; // 위도
    private String hardness; // 경도

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getHardness() {
        return hardness;
    }

    public void setHardness(String hardness) {
        this.hardness = hardness;
    }

}
