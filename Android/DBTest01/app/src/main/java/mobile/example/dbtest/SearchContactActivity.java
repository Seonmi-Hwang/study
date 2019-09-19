package mobile.example.dbtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchContactActivity extends AppCompatActivity {

	ContactDBHelper helper;
	TextView tvSearchResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);
		
		helper = new ContactDBHelper(this);
		tvSearchResult = findViewById(R.id.tvSearchResult);
	}


	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSearchContact:
			SQLiteDatabase db = helper.getReadableDatabase();
			EditText etSearchName = findViewById(R.id.etSearchName);
			String searchName = etSearchName.getText().toString();

			/*like 을 사용하여 입력한 문자를 포함한 모든 이름 검색*/
			Cursor cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME + " where "
                            + ContactDBHelper.COL_NAME + " like '%" + searchName +"%'", null);

			/*검색 결과를 하나만 표시하므로 ContactDto 사용
			여러 개의 검색 결과를 표시하여야 할 경우 결과 개수 만큼 dto 생성 후 ArrayList 등에 저장 필요*/
			ContactDto item = new ContactDto();
			
			while(cursor.moveToNext()) {
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1)); 
				item.setPhone(cursor.getString(2));
				item.setCategory(cursor.getString(3));
			}

			/*필요에 따라 리스트뷰로 대체*/
			tvSearchResult.setText(item.toString() + "\n");
			
			cursor.close();
			helper.close();
			
			break;
		case R.id.btnClose:
			finish();
			break;
		}
	}
	
	
	
}
