package dduwcom.mobile.a02_20170995_report01;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends Activity {

    EditText etTitle;
    EditText etGenre;
    EditText etHero;
    EditText etDirector;
    EditText etRating;
    EditText etPremiere;
    ImageView imageView;

    MovieDBHelper helper;
    Cursor cursor;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etTitle = findViewById(R.id.editTitle);
        etGenre = findViewById(R.id.editGenre);
        etHero = findViewById(R.id.editHero);
        etDirector = findViewById(R.id.editDirector);
        etRating = findViewById(R.id.editRating);
        etPremiere = findViewById(R.id.editPremiere);
        imageView = findViewById(R.id.imageView3);

        helper = new MovieDBHelper(this);

        id = getIntent().getLongExtra("id", 0);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + MovieDBHelper.TABLE_NAME + " where " + MovieDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });

        while (cursor.moveToNext()) {
            etTitle.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE)));
            etGenre.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE)));
            etHero.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_HERO)));
            etDirector.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR)));
            etRating.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RATING)));
            etPremiere.setText(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_PREMIERE)));
        }

        String title = etTitle.getText().toString();
        setImageView(title);

        cursor.close();
        helper.close();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update :
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String hero = etHero.getText().toString();
                String director = etDirector.getText().toString();
                String rating = etRating.getText().toString();
                String premiere = etPremiere.getText().toString();

                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues row = new ContentValues();
                row.put(MovieDBHelper.COL_TITLE, title);
                row.put(MovieDBHelper.COL_GENRE, genre);
                row.put(MovieDBHelper.COL_HERO, hero);
                row.put(MovieDBHelper.COL_DIRECTOR, director);
                row.put(MovieDBHelper.COL_RATING, rating);
                row.put(MovieDBHelper.COL_PREMIERE, premiere);

                String whereClause = MovieDBHelper.COL_ID + "=?";
//               MainActivity 에서 전달받은 foodDto 의 id를 사용하여 수정할 데이터 식별
                String[] whereArgs = new String[] { String.valueOf(id) };

//               변경된 레코드 수 반환
                long count = db.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                if (count > 0) {
                    setResult(RESULT_OK, null);
                    helper.close();
                    finish();
                } else {
                    Toast.makeText(this, "영화 수정 실패", Toast.LENGTH_SHORT).show();
                    helper.close();
                }
                break;
            case R.id.btn_cancel :
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }

    public ImageView setImageView(String movieName) {
        if (movieName.equals("알라딘"))
            imageView.setImageResource(R.mipmap.aladdin_image);
        else if (movieName.equals("캐롤"))
            imageView.setImageResource(R.mipmap.carol_image);
        else if (movieName.equals("말할 수 없는 비밀"))
            imageView.setImageResource(R.mipmap.secret_image);
        else if (movieName.equals("캡틴 마블"))
            imageView.setImageResource(R.mipmap.captain_image);
        else if (movieName.equals("걸캅스"))
            imageView.setImageResource(R.mipmap.girlcops_image);
        else
            imageView.setImageResource(R.mipmap.ic_launcher);

        return imageView;
    }

}
