package mobile.example.network.requesthttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    EditText etDate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDate = findViewById(R.id.etDate);
        tvResult = findViewById(R.id.tvResult);

        StrictMode.ThreadPolicy pol
                = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(pol);
    }


    public void onClick(View v) {
        String address = getResources().getString(R.string.url) + "?key=" + getResources().getString(R.string.key)
                + "&targetDt=" + etDate.getText().toString();
        Log.d("Mainactivity", address);

        // 네트워크 사용 가능 여부 확인
        if (!isOnline()) {
            Toast.makeText(this, "Network is not available!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.btn_request:
                String result = downloadContents(address);
                tvResult.setText(result);
                break;
        }
    }

    //	문자열 형태의 웹 주소를 입력으로 서버 응답을 문자열로 만들어 반환
    private String downloadContents(String address){
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;
        int responseCode = 200;

        try {
            URL url = new URL(address);

            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000); // 연결 대기 시간
            conn.setRequestMethod("GET"); // 데이터를 읽어오겠다
            conn.setDoInput(true); //

            responseCode = conn.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) { // IOException 발생 가능
                throw new IOException("HTTP error code: " + responseCode);
            }

            stream = conn.getInputStream(); // 데이터를 끄집어 냈음. 문자열로 바꿔주어야 함. 여기까지는 형식이 변할 수 없음. 똑같음!!!
            result = readStream(stream); // 문자열로 변환시키는 작업 if)이미지 일때 이미지로 변환시켜줘야 함.
        } catch (MalformedURLException e) {
            Toast.makeText(this, "주소 오류", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try { stream.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    public String readStream(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }







}
