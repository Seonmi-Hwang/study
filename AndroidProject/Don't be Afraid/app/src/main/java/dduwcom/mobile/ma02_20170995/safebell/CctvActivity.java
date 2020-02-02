package dduwcom.mobile.ma02_20170995.safebell;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import dduwcom.mobile.ma02_20170995.R;
import dduwcom.mobile.ma02_20170995.contact.SettingActivity;

/* 안전비상벨의 위치 주소를 입력받아 주변 CCTV들의 정보를 출력해주는 Activity */
public class CctvActivity extends AppCompatActivity {

    public static final String TAG = "CctvActivity";

    ListView lvCctv;
    String apiAddress;

    MyCctvAdapter adapter;
    ArrayList<CctvDto> resultList;
    CctvXmlParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        Intent intent = getIntent();
        String cctvAddress = intent.getStringExtra("address"); // 안전비상벨 위치의 도로명주소

        lvCctv = findViewById(R.id.lvCctv);

        resultList = new ArrayList();
        adapter = new MyCctvAdapter(this, R.layout.listview_cctv, resultList);
        lvCctv.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.cctv_api_url) + "?serviceKey=" + getResources().getString(R.string.open_api_key) + "&rdnmadr=";
        parser = new CctvXmlParser();

        try {
            new CctvActivity.NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(cctvAddress, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (!isOnline()) {
                Toast.makeText(CctvActivity.this, "네트워크가 설정되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            } // 네트워크 환경 조사

            progressDlg = ProgressDialog.show(CctvActivity.this, "Wait", "Downloading...");
        }


        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            Log.d(TAG, address); // 올바른 URL 주소인지 확인
            String result = downloadContents(address);
            if (result == null) return "Error!";
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            progressDlg.dismiss();
            Log.i(TAG, result);

            resultList = parser.parse(result);      // 파싱 수행

            if (resultList == null) {       // 올바른 결과를 수신하지 못하였을 경우 안내
                Toast.makeText(CctvActivity.this, "지역을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
            } else if (!resultList.isEmpty()) {
                adapter.setList(resultList);    // Adapter 에 파싱 결과를 담고 있는 ArrayList 를 설정
            }
            adapter.notifyDataSetChanged();
        }


        /* 네트워크 관련 메소드 */

        /* 네트워크 환경 조사 */
        private boolean isOnline() {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }

        /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
        private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
            conn.setReadTimeout(60000); // XML을 불러오는데에 오래걸릴 수 있으므로 넉넉하게 60초 정도 기다림.
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + conn.getResponseCode());
            }

            return conn.getInputStream();
        }

        /* InputStream을 전달받아 문자열로 변환 후 반환 */
        protected String readStreamToString(InputStream stream){
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

        /* 주소(address)에 접속하여 문자열 데이터를 수신한 후 반환 */
        protected String downloadContents(String address) {
            HttpURLConnection conn = null;
            InputStream stream = null;
            String result = null;

            try {
                URL url = new URL(address);
                conn = (HttpURLConnection)url.openConnection();
                stream = getNetworkConnection(conn);
                result = readStreamToString(stream);
                if (stream != null) stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
            }

            return result;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                Intent intent = new Intent(CctvActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(CctvActivity.this) // 대화상자 띄움
                        .setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // 앱 종료 수행
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
        }
        return false;
    }
}
