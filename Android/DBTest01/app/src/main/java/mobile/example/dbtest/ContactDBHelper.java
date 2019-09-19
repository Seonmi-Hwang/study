package mobile.example.dbtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactDBHelper extends SQLiteOpenHelper {

	private final String TAG = "ContactDBHelper";

	private final static String DB_NAME = "contact_db"; 
	public final static String TABLE_NAME = "contact_table";
	public final static String COL_ID = "_id";
	public final static String COL_NAME = "name";
	public final static String COL_PHONE = "phone";
	public final static String COL_CAT = "category";

	public ContactDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "create table " + TABLE_NAME + " ( " +  COL_ID + " integer primary key autoincrement,"
				+ COL_NAME +" TEXT, " + COL_PHONE + " TEXT, " + COL_CAT + " TEXT);";
		Log.d(TAG, createSql);
		db.execSQL(createSql);

		db.execSQL("insert into " + TABLE_NAME + " values (null, '컴퓨터', '1234', '정보과학대학')");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '정보통계', '2345', '정보과학대학')");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '응용화학', '3456', '자연과학대학')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}


