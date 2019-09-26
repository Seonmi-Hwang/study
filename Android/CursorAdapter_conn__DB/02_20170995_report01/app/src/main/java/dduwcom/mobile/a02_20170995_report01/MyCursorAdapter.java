package dduwcom.mobile.a02_20170995_report01;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Cursor cursor;
    int layout;

    public MyCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        TextView textNo = (TextView)view.findViewById(R.id.textNo);
        TextView textTitle = (TextView)view.findViewById(R.id.textTitle);
        TextView textGenre = (TextView)view.findViewById(R.id.textGenre);
        TextView textRating = (TextView)view.findViewById(R.id.textRating);

        String title = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
        textNo.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ID)));
        textTitle.setText(title);
        textGenre.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE)));
        textRating.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RATING)));

        // 이미지 추가
        if (title.equals("알라딘"))
            imageView.setImageResource(R.mipmap.aladdin_image);
        else if (title.equals("캐롤"))
            imageView.setImageResource(R.mipmap.carol_image);
        else if (title.equals("말할 수 없는 비밀"))
            imageView.setImageResource(R.mipmap.secret_image);
        else if (title.equals("캡틴 마블"))
            imageView.setImageResource(R.mipmap.captain_image);
        else if (title.equals("걸캅스"))
            imageView.setImageResource(R.mipmap.girlcops_image);
        else
            imageView.setImageResource(R.mipmap.ic_launcher);

    }
}
