package mobile.example.network.sample.openapi_with_file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MyBookAdapter extends BaseAdapter {

    public static final String TAG = "MyBookAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<NaverBookDto> list;
    private ImageFileManager imageFileManager = null;


    public MyBookAdapter(Context context, int resource, ArrayList<NaverBookDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;
        this.imageFileManager = new ImageFileManager(context);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public NaverBookDto getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView with position : " + position);
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.tvTitle);
            viewHolder.tvAuthor = view.findViewById(R.id.tvAuthor);
            viewHolder.ivImage = view.findViewById(R.id.ivImage);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        NaverBookDto dto = list.get(position);

        viewHolder.tvTitle.setText(dto.getTitle());
        viewHolder.tvAuthor.setText(dto.getAuthor());


        /*작성할 부분*/
        /*dto 에 저장하고 있는 이미지 주소를 사용하여 이미지 파일이 내부저장소에 있는지 확인
         * ImageFileManager 의 내부저장소에서 이미지 파일 읽어오기 사용
         * 이미지 파일이 있을 경우 bitmap, 없을 경우 null 을 반환하므로 bitmap 이 있으면 이미지뷰에 지정
         * 없을 경우 GetImageAsyncTask 를 사용하여 이미지 파일 다운로드 수행 */


//        dto 에 기록한 이미지 주소를 사용하여 이미지 파일을 읽어오기 수행
        Bitmap savedBitmap = imageFileManager.getSavedBitmapFromInternal(dto.getImageLink());

//        파일에서 이미지 파일을 읽어온 결과에 따라 파일 이미지 사용 또는 네트워크 다운로드 수행
        if (savedBitmap != null) {
            viewHolder.ivImage.setImageBitmap(savedBitmap);
            Log.d(TAG,  "Image loading from file");
        } else {
            viewHolder.ivImage.setImageResource(R.mipmap.ic_launcher);  // 이미지를 다운받기 전엔 기본 이미지로 설정
            GetImageAsyncTask task = new GetImageAsyncTask(viewHolder);
            task.execute(dto.getImageLink());
            Log.d(TAG,  "Image loading from network");
        }

        return view;
    }


    public void setList(ArrayList<NaverBookDto> list) {
        this.list = list;
    }


//    ※ findViewById() 호출 감소를 위해 필수로 사용할 것
    static class ViewHolder {
        public TextView tvTitle = null;
        public TextView tvAuthor = null;
        public ImageView ivImage = null;
    }





   /* 책 이미지를 다운로드 후 내부저장소에 파일로 저장하고 이미지뷰에 표시하는 AsyncTask */
    class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ViewHolder viewHolder;
        String imageAddress;

        public GetImageAsyncTask(ViewHolder holder) {
            viewHolder = holder;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageAddress = params[0];
            Log.i(TAG, imageAddress);
            Bitmap result = downloadImage(imageAddress);
            return result;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {

            /*작성할 부분*/
            /*네트워크에서 다운 받은 이미지 파일을 ImageFileManager 를 사용하여
             내부저장소에 저장 다운받은 bitmap 을 이미지뷰에 지정*/

            /*네트워크를 통해 bitmap 을 정상적으로 받아왔을 경우 수행
            * 서버로부터 정상적으로 이미지 다운로드를 못했을 경우 null 이 반환되므로 기본 설정 이미지가 계속 유지됨*/
            if (bitmap != null) {
                // 다운로드한 bitmap 을 내부저장소에 저장
                imageFileManager.saveBitmapToInternal(bitmap, imageAddress);
                // 다운로드한 bitmap 을 이미지뷰에 표시
                viewHolder.ivImage.setImageBitmap(bitmap);
            }
        }



       /* 이미지를 다운로드하기 위한 네트워크 관련 메소드 */

        /* 네트워크 환경 조사 */
        private boolean isOnline() {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }

       /* 주소를 전달받아 bitmap 다운로드 후 반환 */
        private Bitmap downloadImage(String address) {
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


        /* InputStream을 전달받아 비트맵으로 변환 후 반환 */
        private Bitmap readStreamToBitmap(InputStream stream) {
            return BitmapFactory.decodeStream(stream);
        }

    }

}
