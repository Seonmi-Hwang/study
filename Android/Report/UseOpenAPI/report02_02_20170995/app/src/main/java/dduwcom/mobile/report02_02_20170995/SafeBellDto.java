package dduwcom.mobile.report02_02_20170995;

import java.io.Serializable;

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


//    @Override
//    public String toString() { }

}
