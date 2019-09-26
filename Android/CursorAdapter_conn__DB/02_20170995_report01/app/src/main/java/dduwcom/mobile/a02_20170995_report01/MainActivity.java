package dduwcom.mobile.a02_20170995_report01;

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
		case R.id.btnOpenAllMovie:
			intent = new Intent(this, AllMoviesActivity.class);
			break;
		case R.id.btnAddNewMovie:
			intent = new Intent(this, InsertMovieActivity.class);
			break;
		}
		
		if (intent != null) startActivity(intent);
	}
	
}
