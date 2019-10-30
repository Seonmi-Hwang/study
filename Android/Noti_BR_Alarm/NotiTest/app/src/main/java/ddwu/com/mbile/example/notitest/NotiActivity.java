package ddwu.com.mbile.example.notitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class NotiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                finish();
                break;
        }
    }
}
