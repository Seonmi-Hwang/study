package dduwcom.mobile.ma02_20170995;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dduwcom.mobile.ma02_20170995.contact.ContactDBHelper;
import dduwcom.mobile.ma02_20170995.contact.SettingActivity;
import dduwcom.mobile.ma02_20170995.geocoding.Constants;
import dduwcom.mobile.ma02_20170995.geocoding.FetchAddressIntentService;
import dduwcom.mobile.ma02_20170995.sexoffender.SexOffenderInfoActivity;
import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    private final static int PERMISSION_REQ_CODE = 100;
    private final static int MY_PERMISSIONS_REQUEST_SEND_SMS = 200;
    ContactDBHelper helper;

    private Location currentLoc;
    private LocationManager locManager;

    private MarkerOptions options;

    /* UI */
    private GoogleMap mGoogleMap;

    private String bestProvider;

//    /* OPEN API 전국안전비상벨위치표준데이터 */
//    private String apiAddress;
//    ArrayList<SafeBellDto> resultList;

    private AddressResultReceiver addressResultReceiver;

    /* MMS */
    private String mCurrentPhotoPath;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = new MarkerOptions();

        helper = new ContactDBHelper(this);

//        apiAddress = getResources().getString(R.string.bell_api_url);
//        resultList = new ArrayList<SafeBellDto>();

        /* Geocoding */
        addressResultReceiver = new AddressResultReceiver(new Handler());

        /* Location */
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 아래는 실제 디바이스를 가지고 실행할 때 하자
//      Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//		criteria.setAltitudeRequired(false);
//		criteria.setCostAllowed(false);
//		bestProvider = locManager.getBestProvider(criteria, true);

        bestProvider = LocationManager.PASSIVE_PROVIDER;

//		위치 관련 권한 확인 - 필요한 부분이 여러 곳이므로 메소드 화
        if (checkLocationPermission()) { mapLoad(); }

        /* UI */
        this.InitializeLayout();

        /* 응급 메세지 전송 버튼 구현 */
        FloatingActionButton fab = findViewById(R.id.emergency);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     //			IntentService 를 사용하여 Geocoding 수행
                if (checkSmsPermission())
                    startAddressService(new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude()));

                Snackbar.make(view, "응급 메세지 전송", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//		위치 조사 시작
        if (checkLocationPermission())
            locManager.requestLocationUpdates(bestProvider, 3000, 0, locListener);

//        if (!isOnline()) {
//            Toast.makeText(getApplicationContext(), "네트워크를 사용가능하게 설정해주세요.", Toast.LENGTH_SHORT).show();
//            return;
//        } // 네트워크 환경 조사
//
//        Log.d("API ADDRESS : ", apiAddress);
//        new MainActivity.NetworkAsyncTask().execute(apiAddress);    // server_url 에 입력한 날짜를 결합한 후 AsyncTask 실행
    }

//    class NetworkAsyncTask extends AsyncTask<String, Integer, String> { // 네트워크 작업을 할 AsyncTask
//
//        final static String NETWORK_ERR_MSG = "Server Error!";
//        public final static String TAG = "NetworkAsyncTask";
//
//        ProgressDialog progressDlg;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // 진행상황 다이얼로그 출력
//            progressDlg = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String address = strings[0];
//            String result = downloadContents(address); // 데이터 받아옴 (네트워크 작업)
//            if (result == null) {
//                cancel(true);
//                return NETWORK_ERR_MSG;
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            progressDlg.dismiss();  // 진행상황 다이얼로그 종료
//
////          parser 생성 및 OpenAPI 수신 결과를 사용하여 parsing 수행
//            SafeBellXmlParser parser = new SafeBellXmlParser(new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude()));
//            resultList = parser.parse(result);
//
//            if (resultList == null) {       // 올바른 결과를 수신하지 못하였을 경우 안내
//                Toast.makeText(MainActivity.this, "요청 값을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
//            } else if (!resultList.isEmpty()) {
//                for (int i = 0; i < resultList.size(); i++) {
//                    double lat = Double.parseDouble(resultList.get(i).getLatitude());
//                    double lng = Double.parseDouble(resultList.get(i).getHardness());
//                    options.title("안전비상벨");
//                    options.snippet(resultList.get(i).getSafeBellNo());
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//                    options.position(new LatLng(lat, lng));
//                    mGoogleMap.addMarker(options);
//                }
//            }
//        }
//
//        @Override
//        protected void onCancelled(String msg) {
//            super.onCancelled();
//            progressDlg.dismiss(); // 진행상황 다이얼로그 종료
//            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /* 이하 네트워크 접속을 위한 메소드 */
//
//    /* 네트워크 환경 조사 */
//    private boolean isOnline() {
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//    /* 주소(apiAddress)에 접속하여 문자열 데이터를 수신한 후 반환 */
//    protected String downloadContents(String address) {
//        HttpURLConnection conn = null;
//        InputStream stream = null;
//        String result = null;
//
//        try {
//            URL url = new URL(address);
//            conn = (HttpURLConnection)url.openConnection();
//            stream = getNetworkConnection(conn);
//            result = readStreamToString(stream);
//            if (stream != null) stream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) conn.disconnect();
//        }
//
//        return result;
//    }
//
//    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
//    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
//        conn.setReadTimeout(60000);
//        conn.setConnectTimeout(60000);
//        conn.setRequestMethod("GET");
//        conn.setDoInput(true);
//        conn.setRequestProperty("Cache-Control", "no-cache");
//        conn.setRequestProperty("Content-Type", "application/xml");
//        conn.setRequestProperty("Accept", "application/xml");
//
//        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
//            throw new IOException("HTTP error code: " + conn.getResponseCode());
//        }
//
//        return conn.getInputStream();
//    }
//
//    /* InputStream을 전달받아 문자열로 변환 후 반환 */
//    protected String readStreamToString(InputStream stream){
//        StringBuilder result = new StringBuilder();
//
//        try {
//            InputStreamReader inputStreamReader = new InputStreamReader(stream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            String readLine = bufferedReader.readLine();
//
//            while (readLine != null) {
//                result.append(readLine + "\n");
//                readLine = bufferedReader.readLine();
//            }
//
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result.toString();
//    }


    public void sendSms(String address) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + ContactDBHelper.COL_PHONE + " from " + ContactDBHelper.TABLE_NAME, null);

//        SmsManager smsManager = SmsManager.getDefault();

        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        takeCapture();

        // MMS 발송
        if (mCurrentPhotoPath != null) {
            while (cursor.moveToNext()) {
                Log.d(TAG, "file:/" + mCurrentPhotoPath);
                String phone = cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE));
                String message = "도와주세요. 제 현위치는 이곳입니다.\n" + address;

                Log.d(TAG, String.valueOf(Uri.fromFile(photoFile)));
                Uri uri;
                if (Build.VERSION.SDK_INT < 24) {
                    uri = Uri.fromFile(photoFile);
                } else {
                    uri = FileProvider.getUriForFile(getBaseContext(), getPackageName(), photoFile);
                }

                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra("address", phone);
                    intent.putExtra("sms_body", message);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");
                    startActivity(intent);
                    Log.d(TAG, "Success Send Message");
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
        Toast.makeText(getApplicationContext(), "전송 성공!", Toast.LENGTH_LONG).show();
    }

    public void takeCapture() { // 캡쳐하고 저장한 파일의 위치를 반환
        GoogleMap.SnapshotReadyCallback snapshotReadyCallback = new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                try {
                    FileOutputStream fos = null;

                    fos = new FileOutputStream(mCurrentPhotoPath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file:/" + mCurrentPhotoPath)));
                    Log.d(TAG, "캡쳐 저장완료!");

                    fos.flush();
                    fos.close();
//                    capture.destroyDrawingCache();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mGoogleMap.snapshot(snapshotReadyCallback);
    }

    /*현재 시간 정보를 사용하여 파일 정보 생성*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.d(TAG, "createImageFile");
        Log.d(TAG, mCurrentPhotoPath);

        return image;
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locManager.removeUpdates(locListener); // 위치 조사 종료
    }




    /* 위치 정보 수신 리스너 생성 */
    LocationListener locListener = new LocationListener() {
//		위치 변경 시마다 호출
        @Override
        public void onLocationChanged(Location loc) {
            currentLoc = loc;
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 16));

            searchStart(PlaceType.POLICE); // 주변 경찰서 검색 시작
        }

//		현재 위치제공자가 사용이 불가해질 때 호출
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            Log.i(TAG, provider + " is not available.");
        }

//		현재 위치제공자가 사용가능해질 때 호출
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            Log.i(TAG, provider + " is available.");
        }

//		위치제공자의 상태가 변할 때 호출
        @Override
        public void onStatusChanged(String provider, int status, Bundle extra) {
            // TODO Auto-generated method stub
            Log.i(TAG, provider + "'s status : " + status);
        }

    };

    public void mapLoad() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReadyCallback);
    }

    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap; // 화면 상에 있는 구글맵 객체를 얻음
            Log.d(TAG, "Map ready");

            checkLocationPermission();
//          현재 사용 중인 Provider 로부터 전달 받은 최종 위치의 주소 확인
            Location lastLocation = locManager.getLastKnownLocation(bestProvider);
            LatLng latLng;
            if (lastLocation != null) {
                latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                currentLoc = lastLocation;
            } else {
                latLng = new LatLng(37.606320, 127.041808);
                currentLoc.setLatitude(37.606320);
                currentLoc.setLongitude(127.041808);
            }
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

            // 맵 로딩 후 내 위치 표시 버튼 관련 설정
            mGoogleMap.setMyLocationEnabled(true);  // 내 위치 표시 버튼 활성화(true) / 비활성화(false)

            searchStart(PlaceType.POLICE);

//            if (!isOnline()) {
//                Toast.makeText(getApplicationContext(), "네트워크를 사용가능하게 설정해주세요.", Toast.LENGTH_SHORT).show();
//                return;
//            } // 네트워크 환경 조사
//
//            Log.d("API ADDRESS : ", apiAddress);
//            new MainActivity.NetworkAsyncTask().execute(apiAddress);    // server_url 에 입력한 날짜를 결합한 후 AsyncTask 실행
        }
    };

    private void searchStart(String type) {
//        mGoogleMap.clear();

        new NRPlaces.Builder().listener(placesListener) // 장소 요청 결과 수신 Listener
                .key(getString(R.string.google_api_key)) // 구글 키 값
                .latlng(currentLoc.getLatitude(), currentLoc.getLongitude()) // 검색 위치의 위도, 경도
                .radius(1000) // 검색 반경 1km
                .type(type) // 찾고자 하는 장소의 유형
                .build()
                .execute();
    }

    PlacesListener placesListener = new PlacesListener() {
        @Override
        public void onPlacesSuccess(final List<noman.googleplaces.Place> places) {
            runOnUiThread(new Runnable() {  // UI 요소에 접근하여야 할 경우 runOnThread() 사용
                @Override                   // ex) Map에 Marker 표시
                public void run() {
                    for (noman.googleplaces.Place place : places) {
                        Log.d(TAG, "Place.getName() : " + place.getName());
//                        Drawable drawable = getResources().getDrawable(R.drawable.baseline_emoji_flags_black_18dp);
                        options.title(place.getName());
//                        options.icon(BitmapDescriptorFactory.fromBitmap(((BitmapDrawable)drawable).getBitmap()));
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        options.position(new LatLng(place.getLatitude(), place.getLongitude()));
                        mGoogleMap.addMarker(options);
                        Marker newMarker = mGoogleMap.addMarker(options); // 필요 시 멤버변수로 보관
                        newMarker.setTag(place.getPlaceId()); // Marker의 setTag()를 사용하여 Place ID 보관
                        Log.d(TAG, "ID: " + place.getPlaceId());
                    }
                }
            }); // 확인 가능 정보 : 장소명, 위도/경도, Place ID 등
        }
        @Override
        public void onPlacesFailure(PlacesException e) {}
        @Override
        public void onPlacesStart() {}
        @Override
        public void onPlacesFinished() {}
    };


    /* 위도/경도 → 주소 변환 IntentService 실행 */
    private void startAddressService(LatLng latLng) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, addressResultReceiver); // 결과를 받을 객체(addressResultReceiver)
        intent.putExtra(Constants.LAT_DATA_EXTRA, latLng.latitude);
        intent.putExtra(Constants.LNG_DATA_EXTRA, latLng.longitude);
        startService(intent);
    }

    /* 위도/경도 → 주소 변환 ResultReceiver */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            String addressOutput = null;
            String data;

            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null) return;
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                if (addressOutput == null) addressOutput = "";
                data = addressOutput;
            } else {
                data = getString(R.string.no_address_found);
            }
            sendSms(data);
        }
    }

    /* 위치 관련 권한 확인 메소드 - 필요한 부분이 여러 곳이므로 메소드 화
    ACCESS_FINE_LOCATION - 상세 위치 확인에 필요한 권한
    ACCESS_COARSE_LOCATION - 대략적 위치 확인에 필요한 권한 */
    private boolean checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    public boolean checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
            return false;
        }
        Log.d(TAG, "Start checkSmsPermission()");
        return true;
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
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(MainActivity.this) // 대화상자 띄움
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
