package mobile.database.dbtest02;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AllContactsActivity extends Activity {

	final static String TAG = "AllContactsActivity";
	
	ListView lvContacts = null;
	ContactDBHelper helper;
	Cursor cursor;
	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);
		lvContacts = (ListView)findViewById(R.id.lvContacts);

		helper = new ContactDBHelper(this);

//		  SimpleCursorAdapter 객체 생성
        adapter = new SimpleCursorAdapter ( this, android.R.layout.simple_list_item_2, null,
					new String[] { ContactDBHelper.COL_NAME, ContactDBHelper.COL_PHONE },
					new int[] { android.R.id.text1, android.R.id.text2 },
					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER );

		lvContacts.setAdapter(adapter);

//		리스트 뷰 클릭 처리
		lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AllContactsActivity.this, UpdateActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});


//		리스트 뷰 롱클릭 처리
		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
				TextView tvName = view.findViewById(android.R.id.text1);	// 리스트 뷰의 클릭한 위치에 있는 뷰 확인

				String dialogMessage = "'" + tvName.getText().toString() + "' 연락처 삭제?";	// 클릭한 위치의 뷰에서 문자열 값 확인

				new AlertDialog.Builder(AllContactsActivity.this).setTitle(R.string.title_dialog)
						.setMessage(dialogMessage)
						.setPositiveButton(R.string.ok_dialog, new DialogInterface.OnClickListener() {

//							삭제 수행
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SQLiteDatabase db = helper.getWritableDatabase();

								String whereClause = ContactDBHelper.COL_ID + "=?";
								String[] whereArgs = new String[] { String.valueOf(targetId) };

								db.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);
								helper.close();
								readAllContacts();		// 삭제 상태를 반영하기 위하여 전체 목록을 다시 읽음
							}
						})
						.setNegativeButton(R.string.cancel_dialog, null)
						.show();


				return true;
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		readAllContacts();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//        cursor 사용 종료
		if (cursor != null) cursor.close();
	}


	private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
		SQLiteDatabase db = helper.getReadableDatabase();
		cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);

		adapter.changeCursor(cursor);
		helper.close();
	}

}




