package dduwcom.mobile.ma02_20170995.sexoffender;

public class LocalSexOffender {
    private long _id;
    private String city;
    private String count;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return city + " : " + count + "명";
    }
}
