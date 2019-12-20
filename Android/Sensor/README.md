## Sensor의 활용 

### ⬛️ Sensor의 사용 절차

#### 1. SensorManager 획득
	getSystemService(Context.SENSOR_SERVICE)
#### 2. Sensor 목록 획득 및 확인
	sensorMgr.getSensorList(Sensor.TYPE_...)
#### 3. SensorListener 생성
	SensorListener
#### 4. SensorListener 등록
	sensorMgr.registerListener(sensorListener, ...)
#### 5. SensorListener 해제
	sensorMgr.unregisterListener(sensorListener)

```java
/* SensorManager 획득*/
SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

/* Sensor 목록 획득 및 확인 */
List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
String result = “”;

for (Sensor sensor : sensorList) {
	String sensorSpec =
		String.format(“Sensor Name: %s\nSensor Type: %s\n\n”,
			sensor.getName(), sensor.getType());
	result += sensorSpec;
}

/* SensorListener 생성 */
SensorEventListener mSensorListener = new SensorEventListener() {
	public void onAccuracyChanged(Sensor sensor, int accuracy) { ... }
	public void onSensorChanged(SensorEvent event) { ... }
};  /* event.accuracy // event.sensor // event.timestamp // event.values */

/* SensorListener 등록*/
Sensor sensorType = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
int sensorDelay = SensorManager.SENSOR_DELAY_UI;
sensorManager.registerListener(mSensorListener, sensorType, sensorDelay);
// sensorManager.registerListener(“SensorListener객체”, “등록 Sensor의 종류”, “sensing 빈도 지정”)

/* SensorListener 해제 */  ← sensor 작업은 자원을 많이 소비하므로 반드시 해제 필요 (onPause() 안에 작성)
sensorMgr.unregisterListener(mSensorListener);
```

### ⬛️ Device Orientation

- Device의 방향  
Azimuth → event.values[0]  
Pitch → event.values[1]  
Roll → event.values[2]  

- 필요 Sensor 선언 및 등록  (API level 8부터 Sensor.TYPE_ORIENTATION을 아래 코드로 대체)  
```java
Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 중력 가속도  
Sensor magnetometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); // 자기장  
```

