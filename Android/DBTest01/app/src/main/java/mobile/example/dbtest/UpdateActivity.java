package mobile.example.dbtest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    ContactDto dto;

    EditText etUpdateName;
    EditText etUpdatePhone;
    EditText etUpdateCategory;

    ContactDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        /*AllContactsActivity 에서 intent 를 통해 전달 받은 dto 를 확인*/
        Intent intent = getIntent();
        dto = (ContactDto) intent.getSerializableExtra("dto");

        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdatePhone = findViewById(R.id.etUpdatePhone);
        etUpdateCategory = findViewById(R.id.etUpdateCategory);

        helper = new ContactDBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*intent 를 통해 전달받은 dto 의 값을 화면에 설정*/
        etUpdateName.setText ( dto.getName() );
        etUpdatePhone.setText ( dto.getPhone() );
        etUpdateCategory.setText ( dto.getCategory() );
    }



    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateContact:
                /*id 를 기준으로 화면의 값으로 DB 업데이트*/
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();
                row.put(ContactDBHelper.COL_NAME, etUpdateName.getText().toString());
                row.put(ContactDBHelper.COL_PHONE, etUpdatePhone.getText().toString());
                row.put(ContactDBHelper.COL_CAT, etUpdateCategory.getText().toString());
                String whereClause = ContactDBHelper.COL_ID + "=?";
                String[] whereArgs = new String[] { String.valueOf(dto.getId()) };
                int result = db.update(ContactDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                helper.close();
                String msg = result > 0 ? "Updated!" : "Failed!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnUpdateContactClose:
                finish();
                break;
        }
    }
}
