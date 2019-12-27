package dduwcom.mobile.ma02_20170995.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dduwcom.mobile.ma02_20170995.LawInfoActivity;
import dduwcom.mobile.ma02_20170995.MainActivity;
import dduwcom.mobile.ma02_20170995.R;
import dduwcom.mobile.ma02_20170995.sexoffender.SexOffenderInfoActivity;

public class SettingActivity extends AppCompatActivity {

    private ListView lvContacts = null;

    private ArrayAdapter<ContactDto> adapter;
    private ContactDBHelper helper;
    private ArrayList<ContactDto> contactList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        helper = new ContactDBHelper(this);
        contactList = new ArrayList<>();

        lvContacts = findViewById(R.id.lvContacts);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);

        lvContacts.setAdapter(adapter);


        /* UI */
        this.InitializeLayout();

        FloatingActionButton fab = findViewById(R.id.floatAddBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "단축번호 추가를 시작합니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(SettingActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

        		/*리스트 항목을 롱클릭 하였을 경우 확인 다이얼로그를 표시한 후 삭제 또는 취소
		삭제 시 삭제한 정보를 제외한 내용으로 리스트 갱신*/
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long _id) {

                ContactDto dto = contactList.get(pos);	// 리스트뷰에서 클릭한 위치에 해당하는 dto 확인
                final long id = dto.getId();	// 다이얼로그 객체 내부에서 id 값을 사용하므로 값 유지를 위해 상수 선언

                new AlertDialog.Builder(SettingActivity.this) // 대화상자 띄움
                        .setIcon(R.drawable.baseline_delete_black_24dp)
                        .setTitle("단축번호 삭제")
                        .setMessage("'" + dto.getName() + "'의 번호를 을(를) \n삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // 삭제 실행
                                SQLiteDatabase db = helper.getWritableDatabase();
                                String whereClause = ContactDBHelper.COL_ID + "=?";
                                String[] whereArgs = new String[] { String.valueOf(id) };
                                db.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);
                                helper.close();
                                reloadList();	// 삭제한 내용을 반영하여 리스트뷰 reload
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
            }
        });

        /*롱클릭할 경우 현재 위치 항목의 dto 를 UpdateContactActivity 에 전달*/
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdateContactActivity.class);
                intent.putExtra("dto", contactList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() { // 화면에 보여지기 전에 화면의 내용을 갱신하기 위해 호출되는 메소드
        super.onResume();
        reloadList(); // DB의 모든 레코드 읽어오기
    }

    public void InitializeLayout() {
        // toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // App Bar의 좌측 영역에 Drawer를 Open 하기 위한 Icon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_menu_white_18dp);

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.close
        );

        drawLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item1:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item2:
                        Intent intent1 = new Intent(getApplicationContext(), SexOffenderInfoActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.item3:
                        Intent intent2 = new Intent(getApplicationContext(), LawInfoActivity.class);
                        startActivity(intent2);
                        break;
                }


                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /*DB의 모든 레코드를 읽어와 List에 저장하는 메소드
반복 사용되는 부분이므로 별도의 메소드로 분리하여 작성 - onResume, Dialog 확인 클릭 리스너에서 사용*/
    private void reloadList() {
        contactList.clear(); // 리스트 비우기

        SQLiteDatabase db = helper.getReadableDatabase();

//		메소드 사용 시  Cursor cursor = db.query(ContactDBHelper.TABLE_NAME, null, null, null, null, null, null, null);
//		sql을 직접 작성할 경우
        Cursor cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            ContactDto dto = new ContactDto();
            dto.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            dto.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
            dto.setPhone(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
            contactList.add(dto); // dto 객체를 list에 저장
        }

        adapter.notifyDataSetChanged(); // DB의 내용으로 갱신한 list의 내용을 ListView에 반영하기 위해 호출 (화면 갱신)

        cursor.close(); // 자원 반환
        helper.close(); // 자원 반환
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(SettingActivity.this) // 대화상자 띄움
                        .setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // 앱 종료 수행
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
        }
        return false;
    }

}
