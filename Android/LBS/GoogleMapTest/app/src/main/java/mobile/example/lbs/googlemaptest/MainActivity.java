package mobile.example.lbs.googlemaptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        String location = null;
        Intent intent = null;
        Uri uri = null;
        switch (view.getId()) {
            case R.id.btnCoords:
                location = String.format("geo:%f,%f?z=%d", 37.60632200, 127.0418080, 17);
                break;
            case R.id.btnPath:
                location = String.format("https://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",
                        37.606617, 127.048854, 37.601933, 127.041498);
                break;
        }
        uri = Uri.parse(location);
        intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
