package ddwu.com.mobile.br.smsbrtest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    final static int SMS_RECEIVE_PERMISSON = 100;

    TextView tvSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSms = findViewById(R.id.tvSms);
        checkPermission();
    }


    private void checkPermission() {
        // SMS 수신 권한 확인 (Permission 실시간 체크)
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        if (permissonCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "SMS 수신권한 있음", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "SMS 수신권한 없음", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_RECEIVE_PERMISSON);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_RECEIVE_PERMISSON);
            }
        }
    }



//    SMS 수신 권한 다이얼로그 결과 확인
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[]){
        switch(requestCode){
            case SMS_RECEIVE_PERMISSON:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "SMS권한 승인함", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "SMS권한 거부 - 앱 수행 불가", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnStart:
                registerReceiver(smsBr, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                Log.d(TAG, "start!");
                break;
            case R.id.btnStop:
                unregisterReceiver(smsBr);
                Log.d(TAG, "stop!");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsBr != null) unregisterReceiver(smsBr);
    }

    BroadcastReceiver smsBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive!");
            StringBuilder result = new StringBuilder();
            Bundle bundle = intent.getExtras();

            result.append(tvSms.getText() + "\n");

            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus"); // 고유 key값

                for (int i=0; i < pdus.length; i++) {

                    SmsMessage msg = SmsMessage.createFromPdu((byte [])pdus[i]);
                    result.append("from : ");
                    result.append(msg.getOriginatingAddress());
                    result.append("\n");
                    result.append(msg.getMessageBody());
                }
                tvSms.setText(result.toString());
            }
        }
    };


}
