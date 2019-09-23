package mobile.database.dbtest02;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchContactActivity extends Activity {

	EditText etSearchName;

	ContactDBHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);

		etSearchName = findViewById(R.id.etSearchName);

		helper = new ContactDBHelper(this);
	}
	
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSearchContactSave:
//			DB 검색 작업 수행

			break;
		case R.id.btnClose :
			finish();
			break;
		}
	}
	
	
	
}
