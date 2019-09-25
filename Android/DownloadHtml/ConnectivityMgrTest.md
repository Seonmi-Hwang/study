⬛️ **NetworkInfo 클래스**  
* 네트워크 타입 정보를 표현하는 클래스  
* 현재 사용 가능한 네트워크 확인  

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
⬛️ **실행결과**  

* **'연결 정보 가져오기' 버튼을 눌렀을 경우**   

![connectInfo](https://user-images.githubusercontent.com/50273050/65613110-64ee6180-dff0-11e9-8f9d-aa8dd1e79116.png)  

* **'활성 정보 가져오기' 버튼을 눌렀을 경우**  

![activeInfo](https://user-images.githubusercontent.com/50273050/65613112-6586f800-dff0-11e9-8633-da91871f4372.png)  

