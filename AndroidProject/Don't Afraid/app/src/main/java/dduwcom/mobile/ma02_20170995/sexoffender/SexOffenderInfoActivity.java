package dduwcom.mobile.ma02_20170995.sexoffender;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import dduwcom.mobile.ma02_20170995.R;
import dduwcom.mobile.ma02_20170995.contact.SettingActivity;

public class SexOffenderInfoActivity extends AppCompatActivity {
    String apiAddress;
    ListView lvList;

    ArrayList<LocalSexOffender> resultList;
    ArrayAdapter<LocalSexOffender> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sexoffenderinfo);

        lvList = (ListView) findViewById(R.id.lvList);

        resultList = new ArrayList<LocalSexOffender>();
        adapter = new ArrayAdapter<LocalSexOffender>(this, android.R.layout.simple_list_item_1, resultList);

        lvList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.sexoffender_api_url);

        if (!isOnline()) {
            Toast.makeText(SexOffenderInfoActivity.this, "네트워크를 사용가능하게 설정해주세요.", Toast.LENGTH_SHORT).show();
            return;
        } // 네트워크 환경 조사

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("API ADDRESS : ", apiAddress);
        new NetworkAsyncTask().execute(apiAddress);    // server_url 에 입력한 날짜를 결합한 후 AsyncTask 실행
    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, String> { // 네트워크 작업을 할 AsyncTask

        final static String NETWORK_ERR_MSG = "Server Error!";
        public final static String TAG = "NetworkActivity";

        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 진행상황 다이얼로그 출력
            progressDlg = ProgressDialog.show(SexOffenderInfoActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = downloadContents(address); // 데이터 받아옴 (네트워크 작업)
            if (result == null) {
                cancel(true);
                return NETWORK_ERR_MSG;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDlg.dismiss();  // 진행상황 다이얼로그 종료
            adapter.clear();        // 어댑터에 남아있는 이전 내용이 있다면 클리어

//          parser 생성 및 OpenAPI 수신 결과를 사용하여 parsing 수행
            LocalSexOffenderXmlParser parser = new LocalSexOffenderXmlParser();
            resultList = parser.parse(result);

            if (resultList == null) {       // 올바른 결과를 수신하지 못하였을 경우 안내
                Toast.makeText(SexOffenderInfoActivity.this, "요청 값을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
            } else if (!resultList.isEmpty()) {
                adapter.addAll(resultList);     // 리스트뷰에 연결되어 있는 어댑터에 parsing 결과 ArrayList 를 추가
            }
        }

        @Override
        protected void onCancelled(String msg) {
            super.onCancelled();
            progressDlg.dismiss(); // 진행상황 다이얼로그 종료
            Toast.makeText(SexOffenderInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /* 이하 네트워크 접속을 위한 메소드 */

    /* 네트워크 환경 조사 */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    /* 주소(apiAddress)에 접속하여 문자열 데이터를 수신한 후 반환 */
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

    /* 주소(apiAddress)에 접속하여 비트맵 데이터를 수신한 후 반환 */
    protected Bitmap downloadImage(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        Bitmap result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToBitmap(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
//        conn.setRequestProperty("Cache-Control", "no-cache");
//        conn.setRequestProperty("Content-Type", "application/xml");
        conn.setRequestProperty("Accept", "application/xml");

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

    /* InputStream을 전달받아 비트맵으로 변환 후 반환 */
    protected Bitmap readStreamToBitmap(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
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
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                Intent intent = new Intent(SexOffenderInfoActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(SexOffenderInfoActivity.this) // 대화상자 띄움
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
