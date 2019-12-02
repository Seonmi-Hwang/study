package mobile.example.lbstest_completed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private final static String TAG = "MainActivity";
	private final static int MY_PERMISSIONS_REQ_LOC = 100;

	private TextView tvDisplay;
	private TextView tvLocations;
	private LocationManager locManager;

	private String bestProvider;

	AddressResultReceiver addressResultReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvDisplay = (TextView) findViewById(R.id.tvDisplay);
		tvLocations = (TextView) findViewById(R.id.tvLocations);

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		addressResultReceiver = new AddressResultReceiver(new Handler());

//		Passive 가 아닌 GPS 또는 Network provider 중 선택이 필요할 경우 기준 설정
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//		criteria.setAltitudeRequired(false);
//		criteria.setCostAllowed(false);
//		bestProvider = locManager.getBestProvider(criteria, true);

		bestProvider = LocationManager.PASSIVE_PROVIDER;

//		위치 관련 권한 확인 - 필요한 부분이 여러 곳이므로 메소드 화
		checkPermission();

		//현재 사용 중인 Provider 로부터 전달 받은 최종 위치의 주소 확인
		Location lastLocation = locManager.getLastKnownLocation(bestProvider);
		tvLocations.setText("최종 실행 위치: ");

		if (lastLocation != null) {
			Intent intent = new Intent(MainActivity.this, FetchAddressIntentService.class);
			intent.putExtra(Constants.RECEIVER, addressResultReceiver);     // 결과를 수신할 ResultReceiver 객체 저장
			intent.putExtra(Constants.LAT_DATA_EXTRA, lastLocation.getLatitude());      // 위도 저장
			intent.putExtra(Constants.LNG_DATA_EXTRA, lastLocation.getLongitude());     // 경도 저장

			startService(intent);
		}

	}


	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnStart:
				tvDisplay.setText("Best Provider: " + bestProvider);
				checkPermission();	// 위치 관련 권한 확인 - 필요한 부분이 여러 곳이므로 메소드 화

//				위치 조사 시작
				locManager.requestLocationUpdates(bestProvider, 5000, 0, locListener);
				break;
		}

	}


    @Override
    protected void onPause() {
        super.onPause();
//        위치 조사 종료
        locManager.removeUpdates(locListener);
    }


	/* 위치 관련 권한 확인 메소드 - 필요한 부분이 여러 곳이므로 메소드 화
    ACCESS_FINE_LOCATION - 상세 위치 확인에 필요한 권한
    ACCESS_COARSE_LOCATION - 대략적 위치 확인에 필요한 권한 */
    private void checkPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
					&& checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
								Manifest.permission.ACCESS_COARSE_LOCATION},
						MY_PERMISSIONS_REQ_LOC);

			    return;
			}
		}
	}

    /* 위치 정보 수신 리스너 생성 */
    LocationListener locListener = new LocationListener() {
//		위치 변경 시마다 호출
		@Override
		public void onLocationChanged(Location loc) {
			
			double latitude = loc.getLatitude();	// 위도 확인
			double longitude = loc.getLongitude();	// 경도 확인

//			IntentService 를 사용하여 Geocoding 수행
//			FetchAddressIntentService 는 AndroidManifest.xml 에 서비스로 등록한 상태여야 사용 가능
			Intent intent = new Intent(MainActivity.this, FetchAddressIntentService.class);
			intent.putExtra(Constants.RECEIVER, addressResultReceiver);
			intent.putExtra(Constants.LAT_DATA_EXTRA, latitude);
			intent.putExtra(Constants.LNG_DATA_EXTRA, longitude);

			startService(intent);
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
				tvLocations.setText(tvLocations.getText() + addressOutput + System.getProperty("line.separator"));
			} else {
				tvLocations.setText(getString(R.string.no_address_found));
			}
		}
	}

}

