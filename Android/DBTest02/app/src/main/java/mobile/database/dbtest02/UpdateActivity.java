package mobile.database.dbtest02;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends Activity {

    EditText etName;
    EditText etPhone;
    EditText etCategory;

    ContactDBHelper helper;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etCategory = findViewById(R.id.etCategory);

        helper = new ContactDBHelper(this);

        id = getIntent().getLongExtra("id", 0);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from " + ContactDBHelper.TABLE_NAME + " where " + ContactDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            etName.setText( cursor.getString( cursor.getColumnIndex(ContactDBHelper.COL_NAME) ) );
            etPhone.setText( cursor.getString( cursor.getColumnIndex(ContactDBHelper.COL_PHONE) ) );
            etCategory.setText( cursor.getString( cursor.getColumnIndex(ContactDBHelper.COL_CATEGORY) ) );
        }
        cursor.close();
        helper.close();

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateContact:
//                DB 데이터 업데이트 작업 수행
//                DBTest01 을 참고하여 작성할 것

                break;
            case R.id.btnUpdateContactClose:
//                DB 데이터 업데이트 작업 취소


                finish();
                break;
        }
    }


}
