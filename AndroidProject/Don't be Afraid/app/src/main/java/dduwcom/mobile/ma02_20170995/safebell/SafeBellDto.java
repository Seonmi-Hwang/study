package dduwcom.mobile.ma02_20170995.safebell;

import java.io.Serializable;

/* 안전비상벨의 정보를 담을 DTO */
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
