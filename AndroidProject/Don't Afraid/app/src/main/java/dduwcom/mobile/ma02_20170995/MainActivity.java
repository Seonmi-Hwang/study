package dduwcom.mobile.ma02_20170995;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import android.os.Handler;
import android.os.ResultReceiver;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import dduwcom.mobile.ma02_20170995.ui.home.HomeFragment;
import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    private final static int MY_PERMISSIONS_REQ_LOC = 100;

    private AppBarConfiguration mAppBarConfiguration;

    private Location currentLoc;
    private LocationManager locManager;

    private MarkerOptions options;

    HomeFragment homeFragment;

    private String bestProvider;

    private AddressResultReceiver addressResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = new MarkerOptions();

        /* Geocoding */
        addressResultReceiver = new AddressResultReceiver(new Handler());

        /* Location */
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 아래는 실제 디바이스를 가지고 실행할 때 하자
//        Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//		criteria.setAltitudeRequired(false);
//		criteria.setCostAllowed(false);
//		bestProvider = locManager.getBestProvider(criteria, true);

        bestProvider = LocationManager.PASSIVE_PROVIDER;

//		위치 관련 권한 확인 - 필요한 부분이 여러 곳이므로 메소드 화
        checkPermission();

//      현재 사용 중인 Provider 로부터 전달 받은 최종 위치의 주소 확인
        Location lastLocation = locManager.getLastKnownLocation(bestProvider);
        currentLoc = lastLocation;
        if (lastLocation != null) {
            Intent intent = new Intent(MainActivity.this, FetchAddressIntentService.class);
            intent.putExtra(Constants.RECEIVER, addressResultReceiver);     // 결과를 수신할 ResultReceiver 객체 저장
            intent.putExtra(Constants.LAT_DATA_EXTRA, lastLocation.getLatitude());      // 위도 저장
            intent.putExtra(Constants.LNG_DATA_EXTRA, lastLocation.getLongitude());     // 경도 저장

            startService(intent);
        }

        /* UI */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.emergency);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "응급 메세지 전송 완료", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//		위치 조사 시작
        locManager.requestLocationUpdates(bestProvider, 5000, 0, locListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//      위치 조사 종료
        locManager.removeUpdates(locListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /* 위치 정보 수신 리스너 생성 */
    LocationListener locListener = new LocationListener() {
//		위치 변경 시마다 호출
        @Override
        public void onLocationChanged(Location loc) {
            currentLoc = loc;
//            double latitude = loc.getLatitude();	// 위도 확인
//            double longitude = loc.getLongitude();	// 경도 확인
//
//			IntentService 를 사용하여 Geocoding 수행
//			FetchAddressIntentService 는 AndroidManifest.xml 에 서비스로 등록한 상태여야 사용 가능
//            Intent intent = new Intent(MainActivity.this, FetchAddressIntentService.class);
//            intent.putExtra(Constants.RECEIVER, addressResultReceiver);
//            intent.putExtra(Constants.LAT_DATA_EXTRA, latitude);
//            intent.putExtra(Constants.LNG_DATA_EXTRA, longitude);
//
//            startService(intent);
            searchStart(PlaceType.POLICE);
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

    private void searchStart(String type) {
        new NRPlaces.Builder().listener(placesListener) // 장소 요청 결과 수신 Listener
                .key(getString(R.string.api_key)) // 구글 키 값
                .latlng(currentLoc.getLatitude(), currentLoc.getLongitude()) // 검색 위치의 위도, 경도
                .radius(1000) // 검색 반경 1km
                .type(type) // 찾고자 하는 장소의 유형 (ex.PlaceType.RESTAURANT)
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
                        options.title(place.getName());
                        options.position(new LatLng(place.getLatitude(), place.getLongitude()));
                        Marker newMarker = homeFragment.getmGoogleMap().addMarker(options); // 필요 시 멤버변수로 보관
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

            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null) return;
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                if (addressOutput == null) addressOutput = "";
                options.title(addressOutput);
            } else {
                options.title(getString(R.string.no_address_found));
            }
            // TODO: 추가
        }
    }

    /* 위치 관련 권한 확인 메소드 - 필요한 부분이 여러 곳이므로 메소드 화
    ACCESS_FINE_LOCATION - 상세 위치 확인에 필요한 권한
    ACCESS_COARSE_LOCATION - 대략적 위치 확인에 필요한 권한 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQ_LOC);

                return true;
            }
        }
        return false;
    }
}
