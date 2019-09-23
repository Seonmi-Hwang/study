package mobile.database.dbtest02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onClick(View v) {
		Intent intent = null;
		
		switch (v.getId()) {
		case R.id.btnOpenAllContact:
			intent = new Intent(this, AllContactsActivity.class);
			break;
		case R.id.btnAddNewContact:
			intent = new Intent(this, InsertContactActivity.class);
			break;
		case R.id.btnSearchContact:
			intent = new Intent(this, SearchContactActivity.class);
			break;
		}
		
		if (intent != null) startActivity(intent);
	}
	
}
