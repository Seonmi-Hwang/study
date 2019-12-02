package dduwcom.mobile.mygooglemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.zip.DeflaterInputStream;

public class MainActivity extends AppCompatActivity {

    private final static int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 100;
    private GoogleMap mGoogleMap;
    private LocationManager locManager;
    private MarkerOptions options;
    private Marker centerMarker; // 현재 위치를 표시해주는 마커
    private Marker newMarker; // 롱클릭 위치를 표시해주는 마커
    private PolylineOptions pOptions;

    private AddressResultReceiver addressResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        addressResultReceiver = new AddressResultReceiver(new Handler());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallBack);

        pOptions = new PolylineOptions();
        pOptions.color(Color.RED);
        pOptions.width(5);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                checkPermission();
                locManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 3000, 5, locationListener); // 3초마다, 5m 벗어날 때마다 위치 수신
                break;
            case R.id.btnStop:
                locManager.removeUpdates(locationListener);
                break;
            }
        }

    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap; // 화면 상에 있는 구글맵 객체를 얻음 (이것을 위해 많은 코드가 필요했던거임~)

            checkPermission();
            Location lastLocation = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            LatLng currentLoc;
            if (lastLocation != null) {
                currentLoc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            } else {
                currentLoc = new LatLng(37.606320, 127.041808);
            }
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

            options = new MarkerOptions();
            options.position(currentLoc);
            options.title("현재 위치");
            options.snippet("이동중");
//            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
            centerMarker = mGoogleMap.addMarker(options);
            centerMarker.showInfoWindow();

            mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Log.d("LatLng : ", latLng.latitude + ", " + latLng.longitude);
                    startAddressService(latLng);
//                    Log.d("address : ", address);
                    options = new MarkerOptions();
                    options.position(latLng);
//                   options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                    options.snippet("위도 : " + latLng.latitude + ", 경도 : " + latLng.longitude);
                }
            });
        }
    };



    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) { // location 객체 수신 받음
            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude()); // 위도 경도 꺼내옴
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
            centerMarker.setPosition(currentLoc);

            pOptions.add(currentLoc);
            mGoogleMap.addPolyline(pOptions);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch(requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission was granted! Do your job again!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Oops! Permission denied! You can't do your job!", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//        }
//    }

    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            return;
        }
    }

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
            newMarker = mGoogleMap.addMarker(options);
            newMarker.showInfoWindow();
        }
    }


}
