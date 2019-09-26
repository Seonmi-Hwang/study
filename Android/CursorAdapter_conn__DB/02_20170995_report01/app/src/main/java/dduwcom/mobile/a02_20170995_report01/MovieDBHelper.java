package dduwcom.mobile.a02_20170995_report01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBHelper extends SQLiteOpenHelper {
	final static String TAG = "MovieDBHelper";
	final static String DB_NAME = "movies.db";

	public final static String TABLE_NAME = "movie_table";
	public final static String COL_ID = "_id";
	public final static String COL_TITLE = "title";
	public final static String COL_GENRE = "genre";
	public final static String COL_HERO = "hero";
	public final static String COL_DIRECTOR = "director";
	public final static String COL_RATING = "rating";
	public final static String COL_PREMIERE = "premiere";

	public MovieDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
				COL_TITLE + " TEXT, " + COL_GENRE + " TEXT, " + COL_HERO + " TEXT, " + COL_DIRECTOR + " TEXT, " +
				COL_RATING + " TEXT, " + COL_PREMIERE + " TEXT)";
		Log.d(TAG, sql);
		db.execSQL(sql);

		// 샘플 데이터 삽입 - 필요할 경우에만
		db.execSQL("insert into " + TABLE_NAME + " values (null, '알라딘', '모험/판타지', '미나 마수드, 나오미 스콧, 윌 스미스', '가이 리치', '5', '2019');");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '걸캅스', '코미디/액션', '라미란, 이성경', '정다원', '4', '2019');");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '캡틴 마블', 'SF', '브리 라스', '애나 보든', '4', '2019');");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '캐롤', '멜로/로맨스', '케이트 블란쳇, 루니 마라', '토드 헤인즈', '5', '2016');");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '말할 수 없는 비밀', '멜로/판타지', '주걸륜, 계륜미', '주걸륜', '4.5', '2008');");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
