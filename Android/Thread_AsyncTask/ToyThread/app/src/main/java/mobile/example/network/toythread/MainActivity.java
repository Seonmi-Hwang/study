package mobile.example.network.toythread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ToyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setText("Output");
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button:
//                쓰레드 생성 후 실행
                thread = new ToyThread(handler, "hi");
                thread.setDaemon(true);     // 실행중 메인스레드가 종료되면 같이 종료
                thread.start();     // 쓰레드가 갖고 있는 run() 메소드 실행

                Toast.makeText(this, "Thread Start!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                thread.interrupt();     // InterruptedException 생성
                break;
            case R.id.button3:
                Toast.makeText(this, "Toast!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    쓰레드에서 전달하는 메시지를 처리하기 위한 핸들러
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {        // msg 는 쓰레드에서 생성하여 전달한 Message
            if (msg.what == 1) {
                textView.setText(textView.getText() + "\narg1: " + msg.arg1);
                textView.setText(textView.getText() + "\nObj: " + msg.obj);
            }
        }
    };



//    쓰레드 클래스
    class ToyThread extends Thread {

        final static String TAG = "ToyThread";

        String data;
        Handler handler;

        /*Thread 에서 필요한 매개변수가 있을 경우 보통 생성자로 전달
        Handler : 쓰레드의 결과를 전달하기 위해 사용*/
        public ToyThread(Handler handler, String data) {
            this.data = data;
            this.handler = handler;
        }


        public void run() {
            Log.d(TAG, data + " thread start!");

            // Thread에서 해야할 작업
            int sum = 0;
            for (int i=1; i <= 100; i++) {
                sum += i;
                Log.d(TAG, "value: " + i);
                Log.d(TAG, "sum: " + sum);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

//            작업을 끝낸 후 결과를 Thread 실행 부분으로 전달
            Message msg = handler.obtainMessage();  // Message message = new Message(); 와 달리 Message 객체  재사용
//            Message 에 실행 결과 저장
            msg.what = 1;           // 쓰레드 수행 후의 결과 상태를 정수로 지정
            msg.arg1 = sum;         // msg.arg2 도 동일하게 정수 저장
            msg.obj = new Integer(sum);     // 객체 저장 시, Serializable 인터페이스 구현 객체
//           Handler를 사용하여 Message 전송
            handler.sendMessage(msg);       // Message 없이 결과만을 알릴 때 handler.sendEmptyMessage(int what) 사용

            Log.d(TAG, data + " thread stop!");
        }
    }
}
