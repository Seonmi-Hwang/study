package dduwcom.mobile.ma02_20170995.safebell;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dduwcom.mobile.ma02_20170995.R;

/* 안전비상벨의 정보를 나타내기 위한 CustomAdapter */
public class MySafeBellAdapter extends BaseAdapter {

    public static final String TAG = "MySafeBellAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<SafeBellDto> list;

    public MySafeBellAdapter(Context context, int resource, ArrayList<SafeBellDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SafeBellDto getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView with position : " + position);
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvBellNo = view.findViewById(R.id.tvBellNo);
            viewHolder.tvLocation = view.findViewById(R.id.tvLocation);
            viewHolder.tvAddress = view.findViewById(R.id.tvAddress);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        SafeBellDto dto = list.get(position);
        viewHolder.tvBellNo.setText(dto.getSafeBellNo());
        viewHolder.tvLocation.setText(dto.getLocation());
        viewHolder.tvAddress.setText(dto.getAddress());

        return view;
    }

    public void setList(ArrayList<SafeBellDto> list) {
        this.list = list;
    }

//    ※ findViewById() 호출 감소를 위해 필수로 사용
    static class ViewHolder {
        public TextView tvBellNo = null;
        public TextView tvLocation = null;
        public TextView tvAddress = null;
    }

}
