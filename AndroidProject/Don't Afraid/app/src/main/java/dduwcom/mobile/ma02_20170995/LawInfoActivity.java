package dduwcom.mobile.ma02_20170995;

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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import dduwcom.mobile.ma02_20170995.contact.SettingActivity;


public class LawInfoActivity extends AppCompatActivity {

//    ListView lvList;

    TextView view;
//
//    ArrayList<LocalSexOffender> resultList;
//    ArrayAdapter<LocalSexOffender> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lawinfo);

//        lvList = (ListView) findViewById(R.id.lvList);
//
//        resultList = new ArrayList<LocalSexOffender>();
//        adapter = new ArrayAdapter<LocalSexOffender>(this, android.R.layout.simple_list_item_1, resultList);
//
//        lvList.setAdapter(adapter);

        view = (TextView)findViewById(R.id.linkView);

        if (!isOnline()) {
            Toast.makeText(LawInfoActivity.this, "네트워크를 사용가능하게 설정해주세요.", Toast.LENGTH_SHORT).show();
            return;
        } // 네트워크 환경 조사

        new AsyncTask<Object, Object, Object>() { // 네트워크 작업을 할 AsyncTask

            final static String NETWORK_ERR_MSG = "Server Error!";
            public final static String TAG = "NetworkActivity";

            ProgressDialog progressDlg;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // 진행상황 다이얼로그 출력
                progressDlg = ProgressDialog.show(LawInfoActivity.this, "Wait", "Downloading...");
            }

            @Override
            protected void onProgressUpdate(Object... values) {
                super.onProgressUpdate(values);
                view.append(((String)values[0]) + "\n");
                Log.d("onProgressUpdate : ", (String)values[0]);
            }

            @Override
            protected String doInBackground(Object... strings) {
                try {
                    Log.d("Start", "sendSoap");
                    sendSoap();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ERROR", "doInBackground : sendSoap");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                progressDlg.cancel();
            }

            private void sendSoap() throws Exception {
                String message = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
                        "xmlns:head='http://apache.org/headers' xmlns:open='http://openapi.affis.go.kr'>"
                        + "<soapenv:Header><head:ComMsgHeader>"
                        + "<RequestMsgID></RequestMsgID>"
                        + "<ServiceKey>"
                        + "Ukx9VRhhoqyD1sJvIdH6nMRgMyhaZvYn474Ce2QYvK8qlkn24RpKcjTaLVSG5v7nTyfIhD6L%2FF%2FtJHqsIOdTIA%3D%3D"
                        + "</ServiceKey>"
                        + "</head:ComMsgHeader></soapenv:Header>"
                        + "<soapenv:Body>"
                        + "<open:getMaskMAnswerAreaList/>"
                        + "</soapenv:Body>"
                        + "</soapenv:Envelope>";

                String strURL = "http://www.easylaw.go.kr/OPENAPI/soap/ManyAskManyAnswerService";

                HttpClient httpclient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(strURL);
                StringEntity entity = new StringEntity(message);
                post.setEntity(entity);
                post.setHeader("Content-Type", "text/xml;charset=UTF-8");
                post.setHeader("Connection", "Keep-Alive");

                try {
                    HttpResponse response = httpclient.execute(post);
                    System.out.println("Response body: ");

                    readSoap(response.getEntity().getContent());

                } finally {

                }
            }

            private void readSoap(InputStream is) throws SAXException, IOException, ParserConfigurationException {
                Log.d("Start", "readSoap");
                Document document = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().parse(is);
                Element el = document.getDocumentElement();

                NodeList list = el.getElementsByTagName("onhunansCts");
                System.out.println(list.getLength());

                boolean first = true;

                for(int i = 0 ; i < list.getLength() ; i++){
                    Node node = list.item(i);
                    System.out.println(node.getTextContent());
                    publishProgress(node.getTextContent());
                }
            }

        }.execute(view);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                Intent intent = new Intent(LawInfoActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                new AlertDialog.Builder(LawInfoActivity.this) // 대화상자 띄움
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
