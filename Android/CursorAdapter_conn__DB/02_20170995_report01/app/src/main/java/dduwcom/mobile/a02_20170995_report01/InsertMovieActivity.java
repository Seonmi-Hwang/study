package dduwcom.mobile.a02_20170995_report01;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InsertMovieActivity extends Activity {

	EditText etTitle;
	EditText etGenre;
	EditText etHero;
	EditText etDirector;
	EditText etRating;
	EditText etPremiere;
	ImageView imageView;

	MovieDBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_movie);

		etTitle = findViewById(R.id.addTitle);
		etGenre = findViewById(R.id.addGenre);
		etHero = findViewById(R.id.addHero);
		etDirector = findViewById(R.id.addDirector);
		etRating = findViewById(R.id.addRating);
		etPremiere = findViewById(R.id.addPremiere);
		imageView = findViewById(R.id.imageView);

		imageView.setImageResource(R.mipmap.ic_launcher);

		helper = new MovieDBHelper(this);
	}


	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_add:
//			 DB 데이터 삽입 작업 수행
			SQLiteDatabase db = helper.getWritableDatabase();
			ContentValues row = new ContentValues();

			String title = etTitle.getText().toString();
			String genre = etGenre.getText().toString();
			String hero = etHero.getText().toString();
			String director = etDirector.getText().toString();
			String rating = etRating.getText().toString();
			String premiere = etPremiere.getText().toString();

//           필수 항목을 입력하지 않으면 안내 토스트 출력
			if (title.equals("") || genre.equals("") || rating.equals("")) {
				Toast.makeText(this, "필수 항목을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
				helper.close();
				return;
			}

			row.put(MovieDBHelper.COL_TITLE, title);
			row.put(MovieDBHelper.COL_GENRE, genre);
			row.put(MovieDBHelper.COL_HERO, hero);
			row.put(MovieDBHelper.COL_DIRECTOR, director);
			row.put(MovieDBHelper.COL_RATING, rating);
			row.put(MovieDBHelper.COL_PREMIERE, premiere);

//          데이터 삽입이 정상적으로 이루어졌는지 확인
			long result = db.insert(MovieDBHelper.TABLE_NAME, null, row);

			helper.close();

			String msg = result > 0 ? "영화 추가 성공" : "영화 추가 실패";
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

			break;
		case R.id.btn_cancel:
//			DB 데이터 삽입 취소 수행
			break;
		}
		finish();
	}




	
}
