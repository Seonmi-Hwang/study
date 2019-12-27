package dduwcom.mobile.ma02_20170995.contact;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dduwcom.mobile.ma02_20170995.R;

public class UpdateContactActivity extends AppCompatActivity {
    ContactDto dto;

    EditText etUpdateName;
    EditText etUpdatePhone;

    ContactDBHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_contact);

        /* SettingActivity 에서 intent 를 통해 전달 받은 dto 를 확인*/
        Intent intent = getIntent();
        dto = (ContactDto) intent.getSerializableExtra("dto");

        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdatePhone = findViewById(R.id.etUpdatePhone);

        helper = new ContactDBHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*intent 를 통해 전달받은 dto 의 값을 화면에 설정*/
        etUpdateName.setText ( dto.getName() );
        etUpdatePhone.setText ( dto.getPhone() );
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.updateBtn:
                /* id 를 기준으로 화면의 값으로 DB 업데이트 */
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();

                row.put(ContactDBHelper.COL_NAME, etUpdateName.getText().toString());
                row.put(ContactDBHelper.COL_PHONE, etUpdatePhone.getText().toString());

                String whereClause = ContactDBHelper.COL_ID + "=?"; // id로 행을 찾을 것임
                String[] whereArgs = new String[] { String.valueOf(dto.getId()) }; // dto에서 id를 가져와서 long타입을 String으로 변환

                int result = db.update(ContactDBHelper.TABLE_NAME, row, whereClause, whereArgs); // update 수행 & 결과 저장

                helper.close();

                String msg = result > 0 ? "Updated!" : "Failed!";  // update가 제대로 수행되었는지 확인
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                Intent intent = new Intent(UpdateContactActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(UpdateContactActivity.this) // 대화상자 띄움
                        .setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // 앱 종료 수행
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
        }
        return false;
    }

}
