```java
package mobile.example.connectionmgrtest;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tvOutput;
	ConnectivityManager connMgr;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvOutput = findViewById(R.id.tvOutput);
        connMgr = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
    }
    
    public void onClick(View v) {
    	StringBuilder result = new StringBuilder();
    		
    	switch(v.getId()) {
    	case R.id.btnAllInfo:
    		NetworkInfo[] netInfo = connMgr.getAllNetworkInfo(); 
    		for (NetworkInfo n : netInfo) {
    			result.append(n.toString() + "\n\n");
    		}
    		break;
    		
    	case R.id.btnActiveInfo:
    		NetworkInfo activeNetInfo = connMgr.getActiveNetworkInfo();
    		result.append(activeNetInfo);
    		
    		break;
    	}
    	tvOutput.setText(result);
    }
}
```
