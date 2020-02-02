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
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import dduwcom.mobile.ma02_20170995.R;
import dduwcom.mobile.ma02_20170995.contact.SettingActivity;

public class SafeBellActivity extends AppCompatActivity {

    public static final String TAG = "SafeBellActivity";

    EditText etTarget;
    ListView lvList;
    String apiAddress;

    String query;

    MySafeBellAdapter adapter;
    ArrayList<SafeBellDto> resultList;
    SafeBellXmlParser parser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safebell);

        etTarget = findViewById(R.id.etTarget); // 안전비상벨의 제공기관명
        lvList = findViewById(R.id.lvList);

        resultList = new ArrayList();
        adapter = new MySafeBellAdapter(this, R.layout.listview_safebell, resultList);
        lvList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.safeBell_api_url) + "?serviceKey=" + getResources().getString(R.string.open_api_key) + "&insttNm=";
        parser = new SafeBellXmlParser();

        /* ListView의 item을 클릭하면, 해당 안전비상벨의 주변 CCTV 정보를 나타내는 CctvActivity로 안전비상벨의 도로명주소 전달 */
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SafeBellDto safeBellDto = resultList.get(position);

                Intent intent = new Intent(SafeBellActivity.this, CctvActivity.class);
                intent.putExtra("address", safeBellDto.getAddress()); // 안전비상벨의 도로명 주소
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                query = etTarget.getText().toString();
                try {
                    new NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(query, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (!isOnline()) {
                Toast.makeText(SafeBellActivity.this, "네트워크가 설정되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            } // 네트워크 환경 조사

            progressDlg = ProgressDialog.show(SafeBellActivity.this, "Wait", "Downloading...");
        }



        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            Log.d(TAG, address);
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
                Toast.makeText(SafeBellActivity.this, "지역을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
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
        private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception, TimeoutException {
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
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
                Intent intent = new Intent(SafeBellActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(SafeBellActivity.this) // 대화상자 띄움
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
