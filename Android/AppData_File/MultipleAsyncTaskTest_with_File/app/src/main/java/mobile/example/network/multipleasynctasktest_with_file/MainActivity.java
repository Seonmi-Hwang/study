package mobile.example.network.multipleasynctasktest_with_file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    final static String ERROR_MSG = "Network Error!";

    EditText etDownLink;        // 데이터를 받아올 서버 주소 기록
    TextView tvImgLink;         // 수신한 데이터(이미지 주소)를 표시
    Button btnImgDownload;
    ImageView ivImage;          // 수신한 데이터(비트맵) 표시


    ImageFileManager imageFileManager;
    String newFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDownLink = findViewById(R.id.etDownLink);
        tvImgLink = findViewById(R.id.tvImgLink);
        btnImgDownload = findViewById(R.id.btnImgDownload);
        ivImage = findViewById(R.id.ivImage);

        etDownLink.setText( getResources().getString(R.string.default_link) );

        imageFileManager = new ImageFileManager(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        btnImgDownload.setEnabled(false);
    }


    public void onClick(View v) {

        if (!isOnline()) {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.btnReset:
                // 앱의 초기 상태로 돌아가기
                etDownLink.setText(getResources().getString(R.string.default_link));    // 기본 다운로드 주소로 초기화
                tvImgLink.setText("");      // 이미지 주소 초기화
                btnImgDownload.setEnabled(false);      // 이미지 다운 버튼 비활성화
                ivImage.setImageResource(R.mipmap.ic_launcher);    // 이미지를 초기 이미지로 지정
                break;
            case R.id.btnDataDownload:
                // EditText etDownLink 에 기록한 주소에 접속하여 문자열 data 받아오기
                String dataAddress = etDownLink.getText().toString();
                DataAsyncTask dataTask = new DataAsyncTask();
                dataTask.execute(dataAddress);
                break;
            case R.id.btnImgDownload:
                String url = tvImgLink.getText().toString();

                // 현재 url 에 해당하는 이미지를 저장한 파일이 있는지 확인
                Bitmap bitmap = imageFileManager.getSavedBitmapFromInternal(url);

                if (bitmap != null) {
                    ivImage.setImageBitmap(bitmap);
                    Log.i(TAG, "Loaded from file!");
                } else {
                    ImageAsyncTask imgTask = new ImageAsyncTask();
                    try {
                        imgTask.execute(url);
                        Log.d(TAG, "Image downloading!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                break;
        }
    }


    public void onSaveClick(View v) {
        switch(v.getId()) {
            case R.id.btnReadPublic:
                Bitmap bitmap = imageFileManager.getSavedBitmapFromExternal(newFileName);
                ivImage.setImageBitmap(bitmap);
                break;
            case R.id.btnMovePublic:
                newFileName = imageFileManager.moveStorage(tvImgLink.getText().toString());
                if (newFileName != null) {
                    Toast.makeText(this, "MOVED!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "MOVE FAILED!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnRemove:
                imageFileManager.removeImage(newFileName);
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageFileManager.clearSaveFilesOnInternal();
    }




    /* 문자열 형태의 주소를 전달 받아 네트워크에서 문자열 데이터(이미지 주소)를 받아옴
            수신한 문자열은 TextView(tvImgLink) 에 출력 */
    class DataAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = downloadContents(address);
            if (result == null) return ERROR_MSG;
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            tvImgLink.setText(result);
            if (!result.equals(ERROR_MSG)) btnImgDownload.setEnabled(true);
        }
    }



    /* 문자열 형태의 주소를 전달 받아 네트워크에서 비트맵 데이터(이미지)를 받아옴
        수신한 비트맵은 ImageView(ivImage) 에 출력 */
    class ImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        String address;

        @Override
        protected Bitmap doInBackground(String... strings) {
            address = strings[0];
            Bitmap result = downloadImage(address);
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                Log.i(TAG,"downloading complete");
                ivImage.setImageBitmap(bitmap);
                imageFileManager.saveBitmapToInternal(bitmap, address);
            } else {
                ivImage.setImageResource(R.mipmap.ic_launcher);
                tvImgLink.setText(ERROR_MSG);
            }
        }
    }




    /* 네트워크 환경 조사 */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
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


    /* 주소(address)에 접속하여 비트맵 데이터를 수신한 후 반환 */
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
}
