package dduwcom.mobile.ma02_20170995.contact;

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

	public ContactDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "create table " + TABLE_NAME + " ( " +  COL_ID + " integer primary key autoincrement,"
				+ COL_NAME +" TEXT, " + COL_PHONE + " TEXT);";
		Log.d(TAG, createSql);
		db.execSQL(createSql);

		db.execSQL("insert into " + TABLE_NAME + " values (null, '선미', '5555215554')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}


