package mobile.example.dbtest;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertContactActivity extends AppCompatActivity {

	ContactDBHelper helper;
	EditText etName;
	EditText etPhone;
	EditText etCategory;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_contact);

//      DBHelper 생성
		helper = new ContactDBHelper(this);
		
		etName = (EditText)findViewById(R.id.editText1);
		etPhone = (EditText)findViewById(R.id.editText2);
		etCategory = (EditText)findViewById(R.id.editText3);
	}
	
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnNewContactSave:
			SQLiteDatabase db = helper.getWritableDatabase();
			
			String name = etName.getText().toString();
			String phone = etPhone.getText().toString();
			String category = etCategory.getText().toString();
			
//			DB 메소드를 사용할 경우
			ContentValues row = new ContentValues();
			row.put("name", name);
			row.put("phone", phone);
			row.put("category", category);
			
			long result = db.insert(ContactDBHelper.TABLE_NAME, null, row);
			
//			SQL query를 직접 사용할 경우
//			db.execSql("insert into " + ContactDBHelper.TABLE_NAME
//									  + " values(null, '" + name + "', '" + phone +"', '" + category "');", null);
			
			helper.close();

			if (result > 0) {
				etName.setText("");
				etPhone.setText("");
				etCategory.setText("");
				Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btnNewContactClose:
			finish();
			break;
		}
	}
	
	
	
	
	
}
