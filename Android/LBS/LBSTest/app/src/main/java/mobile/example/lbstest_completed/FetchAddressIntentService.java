package mobile.example.lbstest_completed;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* FetchAddressIntentService 는 AndroidManifest.xml 에 서비스로 등록한 상태여야 사용 가능 */
public class FetchAddressIntentService extends IntentService {
    final static String TAG = "FetchAddress";

    private Geocoder geocoder;
    private ResultReceiver receiver;

    public FetchAddressIntentService() {
        super("FetchLocationIntentService");

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Geocoder 객체 생성
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        // MainActivity 가 전달한 Intent에서 위도/경도와 Receiver 객체 설정
        if (intent == null) return;
        double latitude = intent.getDoubleExtra(Constants.LAT_DATA_EXTRA, 0);
        double longitude = intent.getDoubleExtra(Constants.LNG_DATA_EXTRA, 0);
        receiver = intent.getParcelableExtra(Constants.RECEIVER);

        List<Address> addresses = null;

        // 위도/경도에 해당하는 주소 정보를 Geocoder 에게 요청
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {   // 네트워크 이상이 발생하였을 때
            e.printStackTrace();
        } catch (IllegalArgumentException e) {  // 잘못된 위도/경도를 전달하였을 때
            e.printStackTrace();
        }

        // Geocoding 결과로부터 주소 추출
        if (addresses == null || addresses.size()  == 0) {
            Log.e(TAG, getString(R.string.no_address_found));
            deliverResultToReceiver(Constants.FAILURE_RESULT, null);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList();

            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                TextUtils.join(System.getProperty("line.separator"),
                addressFragments));
        }
    }

    //    ResultReceiver 에게 결과를 Bundle 형태로 전달
    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();   // 결과를 저장할 bundle 생성
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        receiver.send(resultCode, bundle);
    }
}
