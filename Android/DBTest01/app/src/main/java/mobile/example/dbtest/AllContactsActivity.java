package mobile.example.dbtest;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class AllContactsActivity extends AppCompatActivity {
	
	private ListView lvContacts = null;

	private ArrayAdapter<ContactDto> adapter;
	private ContactDBHelper helper;
	private ArrayList<ContactDto> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);

		helper = new ContactDBHelper(this);
		contactList = new ArrayList<>();

		lvContacts = findViewById(R.id.lvContacts);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);

		lvContacts.setAdapter(adapter);


		/*리스트 항목을 롱클릭 하였을 경우 확인 다이얼로그를 표시한 후 삭제 또는 취소
		삭제 시 삭제한 정보를 제외한 내용으로 리스트 갱신*/
		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long _id) {

				ContactDto dto = contactList.get(pos);		// 리스트뷰에서 클릭한 위치에 해당하는 dto 확인
				final long id = dto.getId();	// 다이얼로그 객체 내부에서 id 값을 사용하므로 값 유지를 위해 상수 선언

				new AlertDialog.Builder(AllContactsActivity.this)
						.setTitle("연락처 삭제")
						.setMessage(dto.getName() + " 를 삭제하시겠습니까?")
						.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								SQLiteDatabase db = helper.getWritableDatabase();
								String whereClause = ContactDBHelper.COL_ID + "=?";
								String[] whereArgs = new String[] { String.valueOf(id) };
								db.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);
								helper.close();
								reloadList();		// 삭제한 내용을 반영하여 리스트뷰 reload
							}
						})
						.setNegativeButton("취소", null)
						.show();
				return true;
			}
		});

		/*롱클릭할 경우 현재 위치 항목의 dto 를 UpdateActivity 에 전달*/
		lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AllContactsActivity.this, UpdateActivity.class);
				intent.putExtra("dto", contactList.get(position));
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		reloadList();
	}


	/*DB의 모든 레코드를 읽어와 List에 저장하는 메소드
	반복 사용되는 부분이므로 별도의 메소드로 분리하여 작성 - onResume, Dialog 확인 클릭 리스너에서 사용*/
	private void reloadList() {
		contactList.clear();

		SQLiteDatabase db = helper.getReadableDatabase();

//		메소드 사용 시  Cursor cursor = db.query(ContactDBHelper.TABLE_NAME, null, null, null, null, null, null, null);
//		sql을 직접 작성할 경우
		Cursor cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);

		while(cursor.moveToNext()) {
			ContactDto dto = new ContactDto();
			dto.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			dto.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
			dto.setPhone(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
			dto.setCategory(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_CAT)));
			contactList.add(dto);
		}

		adapter.notifyDataSetChanged();

		cursor.close();
		helper.close();
	}



}




