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

/* CCTV 정보를 나타내기 위한 CustomAdapter */
public class MyCctvAdapter extends BaseAdapter {

    public static final String TAG = "MyCctvAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<CctvDto> list;

    public MyCctvAdapter(Context context, int resource, ArrayList<CctvDto> list) {
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
    public CctvDto getItem(int position) {
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
        MyCctvAdapter.ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new MyCctvAdapter.ViewHolder();
            viewHolder.tvInstitution = view.findViewById(R.id.tvInstitution);
            viewHolder.tvPurpose = view.findViewById(R.id.tvPurpose);
            viewHolder.tvCctvNum = view.findViewById(R.id.tvCctvNum);
            viewHolder.tvOldAddress = view.findViewById(R.id.tvOldAddress);
            viewHolder.tvPhoneNum = view.findViewById(R.id.tvPhoneNum);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyCctvAdapter.ViewHolder)view.getTag();
        }

        CctvDto dto = list.get(position);
        viewHolder.tvInstitution.setText(dto.getInstitution());
        viewHolder.tvPurpose.setText(dto.getPurpose());
        viewHolder.tvCctvNum.setText(dto.getNumber());
        viewHolder.tvOldAddress.setText(dto.getOldAddress());
        viewHolder.tvPhoneNum.setText(dto.getPhone());
        return view;
    }

    public void setList(ArrayList<CctvDto> list) {
        this.list = list;
    }

    //    ※ findViewById() 호출 감소를 위해 필수로 사용
    static class ViewHolder {
        public TextView tvInstitution = null;
        public TextView tvPurpose = null;
        public TextView tvCctvNum = null;
        public TextView tvOldAddress = null;
        public TextView tvPhoneNum = null;
    }
}
