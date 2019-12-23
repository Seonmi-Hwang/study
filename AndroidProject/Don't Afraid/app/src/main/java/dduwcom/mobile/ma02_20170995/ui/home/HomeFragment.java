package dduwcom.mobile.ma02_20170995.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
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

import java.util.Arrays;
import java.util.List;

import dduwcom.mobile.ma02_20170995.MainActivity;
import dduwcom.mobile.ma02_20170995.R;
import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

//    private HomeViewModel homeViewModel;

    final static String TAG = "HomeFragment";
    final static int PERMISSION_REQ_CODE = 100;

    /* UI */
    private GoogleMap mGoogleMap;
    private Marker marker;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mapLoad();

        return view;
    }

    public void mapLoad() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap; // 화면 상에 있는 구글맵 객체를 얻음 (이것을 위해 많은 코드가 필요했던거임~)
        Log.d(TAG, "Map ready");

        // 맵 로딩 후 내 위치 표시 버튼 관련 설정
        mGoogleMap.setMyLocationEnabled(true);  // 내 위치 표시 버튼 활성화(true) / 비활성화(false)
        mGoogleMap.setOnMyLocationButtonClickListener(locationButtonClickListener);
    }


    /* 내 위치 확인 버튼 클릭 처리 리스너 */
    GoogleMap.OnMyLocationButtonClickListener locationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
//                    Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            };

    public void setMarkerOptions(MarkerOptions options){
        mGoogleMap.addMarker(options);
    }

    public Marker getMarker(){
        return marker;
    }

    public GoogleMap getmGoogleMap() {
        return mGoogleMap;
    }
}