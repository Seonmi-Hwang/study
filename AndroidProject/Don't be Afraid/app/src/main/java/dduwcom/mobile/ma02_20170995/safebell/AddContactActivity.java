package dduwcom.mobile.ma02_20170995.safebell;

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
import dduwcom.mobile.ma02_20170995.contact.ContactDBHelper;
import dduwcom.mobile.ma02_20170995.contact.SettingActivity;

public class AddContactActivity extends AppCompatActivity {
    ContactDBHelper helper;

    EditText etName;
    EditText etPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

//      DBHelper 생성
        helper = new ContactDBHelper(this);

        etName = (EditText)findViewById(R.id.etName);
        etPhone = (EditText)findViewById(R.id.etPhone);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addBtn:
                SQLiteDatabase db = helper.getWritableDatabase();

                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();

                ContentValues row = new ContentValues();
                row.put("name", name);
                row.put("phone", phone);
                long result = db.insert(ContactDBHelper.TABLE_NAME, null, row);

                helper.close();

                if (result > 0) {
                    etName.setText("");
                    etPhone.setText("");
                    Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
                }

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
                Intent intent = new Intent(AddContactActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(AddContactActivity.this) // 대화상자 띄움
                        .setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // 앱 종료 수행
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
        }
        return false;
    }


}
