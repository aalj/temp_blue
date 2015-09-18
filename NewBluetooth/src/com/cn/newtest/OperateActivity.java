package com.cn.newtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbluetooth.BlueSocketActivity;
import com.example.newbluetooth.R;

@SuppressWarnings("deprecation")
public class OperateActivity extends Activity {

	/**
	 * 操作界面
	 */
	static String MY_TAG = "徐野测试";

	private TextView tv;
	String address;
	private BluetoothDevice device;
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothSocket clientSocket;
	private OutputStream os;
	private BlueThread blueThread;
	private String open = "com.xuye.isOpen";
	private String close = "com.xuye.isClose";
	private String shake = "com.xuye.shake";
	private String cancelShake = "com.xuye.cancelShake";
	private String getPhoneData = "com.xuye.getPhoneData";
	private String recordAudio = "com.xuye.recordAudio";
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
	EditText et;
	private String phoneData="";

	private boolean exit = false;
	private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/record_file.amr";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.operate_main);
		viewInit();
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		receiver = new BatteryReceiver();
		Intent intent = getIntent();
		// 从Intent当中根据key取得value
		address = intent.getStringExtra("ADDRESS");
		blueThread = new BlueThread();
		blueThread.start();

	}

	private void viewInit() {
		tv = (TextView) findViewById(R.id.tv);
		btn1 = (Button) findViewById(R.id.btn_open);
		btn2 = (Button) findViewById(R.id.btn_close);
		btn3 = (Button) findViewById(R.id.btn_shake);
		btn4 = (Button) findViewById(R.id.btn_Closeshake);
		btn5 = (Button) findViewById(R.id.btn_surplus);
		btn6 = (Button) findViewById(R.id.btn_record);
		btn7 = (Button) findViewById(R.id.btn_send);
		et = (EditText) findViewById(R.id.et);
		btn1.setOnClickListener(new MyBlueClickListener());
		btn2.setOnClickListener(new MyBlueClickListener());
		btn3.setOnClickListener(new MyBlueClickListener());
		btn4.setOnClickListener(new MyBlueClickListener());
		btn5.setOnClickListener(new MyBlueClickListener());
		btn6.setOnClickListener(new MyBlueClickListener());
		btn7.setOnClickListener(new MyBlueClickListener());
	}

	public boolean isNumeric(String str){ 
	    
	    return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();   
	 } 
	
	private class MyBlueClickListener implements View.OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_send:
				
				String com = "距离-" + et.getText().toString();
				if(isNumeric(et.getText().toString())){
					dataInit(com);
				}else{
					Toast.makeText(OperateActivity.this, "请填写正确数字", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_open:
				
				dataInit(open);
				break;
			case R.id.btn_close:
				dataInit(close);
				break;
			case R.id.btn_shake:
				dataInit(shake);
				break;
			case R.id.btn_Closeshake:
				dataInit(cancelShake);
				break;
			case R.id.btn_surplus:
				dataInit(getPhoneData);
				break;
			case R.id.btn_record:
				setProgressBarIndeterminateVisibility(true);
				setTitle("开始远程录音...");
				dataInit(recordAudio);
				break;
			default:
				break;
			}
		}

	}

	private void dataInit(String data) {
		try {
			try {

				if (device == null) {
					// 获得蓝牙设备，相当于网络客户端Socket指定IP地址
					device = bluetoothAdapter.getRemoteDevice(address);
				}

				if (clientSocket == null) {
					// 通过UUID连接蓝牙设备，相当于网络客户端Socket指定端口号
					clientSocket = device.createRfcommSocketToServiceRecord(BlueSocketActivity.MY_UUID);
					// 开始连接蓝牙设备
					clientSocket.connect();
					System.out.println("开始连接蓝牙设备");
					// 获得向服务端发送数据的OutputStream对象
					os = clientSocket.getOutputStream();
				}
			} catch (IOException e) {

			}
			if (os != null) {
				// 向服务端发送一个字符串
				os.write(data.getBytes("utf-8"));
				Toast.makeText(this, "信息发送成功.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "信息发送失败.", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String recordData = "";
	
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			if (String.valueOf(msg.obj).equals(open)) {

				myUnLock();

			}  else if(String.valueOf(msg.obj).equals(close)) {

				myLock();

			} else if (String.valueOf(msg.obj).equals(shake)) {

				shake();

			}else if (String.valueOf(msg.obj).equals(cancelShake)) {

				shakeCancel();

			} else if (String.valueOf(msg.obj).equals(getPhoneData)) {

				getEnergy(btn5);
				new Handler().postDelayed(new Runnable() {

					public void run() {
						dataInit(phoneData);
					}
				}, 1000);

			} else if (String.valueOf(msg.obj).equals(recordAudio)) {

				counting();

			} else {

				String receiveData = String.valueOf(msg.obj);
				if(receiveData.startsWith("电池技术")){
					tv.setText(String.valueOf(msg.obj));
				}else{

					if(receiveData.equals("record_end")){
					
						System.out.println("成功");
						setProgressBarIndeterminateVisibility(false);
						setTitle("录音传输成功");     
						try {
							decoderBase64File(recordData, filePath);
						} catch (Exception e) {
							e.printStackTrace();
						}
//						if(isSuccess){
							File f = new File(filePath);
							play(f);
							recordData = "";
//						}else{
//							Toast.makeText(OperateActivity.this, "文件转换失败-toFile", Toast.LENGTH_LONG).show();
//						}
					}else{
						recordData = recordData + receiveData;
						System.out.println("追加");
					}

					setTitle("蓝牙功能");     
				}
			}

		}
	};

	private class BlueThread extends Thread {

		private BluetoothServerSocket serverSocket;
		private BluetoothSocket socket;
		private InputStream is;
		private OutputStream os;

		public BlueThread() {

			try {
				// 此方法用来创建BluetoothServerSocket的对象
				// 第一个参数表示蓝牙服务的名称，可以是任意字符串
				// 第二个参数是UUID
				serverSocket = bluetoothAdapter
						.listenUsingRfcommWithServiceRecord(
								BlueSocketActivity.NAME,
								BlueSocketActivity.MY_UUID);
			} catch (IOException e) {

			}
		}

		public void run() {

			try {
				// 通过BluetoothServerSocket.accept();方法收到客户端的请求后，
				// accept()方法会返回一个BluetoothSocket对象，可以通过该对象获得读写数据的InputStream和OutputStream对象
				// 等待接受蓝牙客户端的请求
				socket = serverSocket.accept();

				// 通过循环不断接收客户端发过来的数据。如果客户端暂时没发数据，则read方法处于阻塞状态
				while (!exit) {
					is = socket.getInputStream();
					os = socket.getOutputStream();
					byte[] buffer = new byte[2048];
					int count = is.read(buffer);

					System.out.println(count + "---------------");
					String recvData = new String(buffer, 0, count, "utf-8");
//					System.out.println("----" + recvData );

					Message msg = Message.obtain();
					msg.obj = recvData;
					handler.sendMessage(msg);
				}

			} catch (Exception e) {

			}
		}
	}

	/**
	 * 屏幕锁定部分
	 */

	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	public void myLock() {
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(this, LockReceiver.class);
		// 判断是否有权限
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow();
			// 下面两行都不好使，在android4.2上
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);
		} else {
			activeManager();
		}
	}

	private void activeManager() {
		// 激活设备管理器获取权限
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "传说中的一键锁屏");
		startActivity(intent);
		finish();
	}

	public void myUnLock() {
		// 屏幕解锁
		//		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		//		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock(MY_TAG);
		//		keyguardLock.disableKeyguard(); //Can not Uses
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// 解锁
		kl.disableKeyguard();
		// 获取电源管理器对象
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		// 释放
		wl.release();
	}


	/**
	 * 震动方法
	 */

	private Vibrator vibrator;

	public void shake() {
		/*
		 * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
		 */
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
		vibrator.vibrate(pattern, 2); // 重复两次上面的pattern 如果只想震动一次，index设为-1
	}

	public void shakeCancel() {
		vibrator.cancel();
	}

	/**
	 * 获取手机电量
	 */
	private BatteryReceiver receiver = null;

	public void getEnergy(View v) {

		if(v.getTag() == null){
			v.setTag(true);
		}

		//获取电池电量
		if((Boolean) v.getTag()){
			IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			registerReceiver(receiver, filter);//注册BroadcastReceiver
			v.setTag(false);
		}else {
			//停止获取电池电量
			unregisterReceiver(receiver);
			v.setTag(true);
		}
	}
	
	protected void onDestroy() {
		exit = true;
		super.onDestroy();
	}

	private class BatteryReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			String BatteryStatus = ""; // 电池状态
			String BatteryTemp = ""; // 电池使用情况

			switch (intent.getIntExtra("status",
					BatteryManager.BATTERY_STATUS_UNKNOWN)) {
					case BatteryManager.BATTERY_STATUS_CHARGING:
						BatteryStatus = "充电状态";
						break;
					case BatteryManager.BATTERY_STATUS_DISCHARGING:
						BatteryStatus = "放电状态";
						break;
					case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
						BatteryStatus = "未充电";
						break;
					case BatteryManager.BATTERY_STATUS_FULL:
						BatteryStatus = "充满电";
						break;
					case BatteryManager.BATTERY_STATUS_UNKNOWN:
						BatteryStatus = "未知道状态";
						break;
			}

			switch (intent.getIntExtra("health",
					BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
					case BatteryManager.BATTERY_HEALTH_UNKNOWN:
						BatteryTemp = "未知错误";
						break;
					case BatteryManager.BATTERY_HEALTH_GOOD:
						BatteryTemp = "状态良好";
						break;
					case BatteryManager.BATTERY_HEALTH_DEAD:
						BatteryTemp = "电池没有电";
						break;
					case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
						BatteryTemp = "电池电压过高";
						break;
					case BatteryManager.BATTERY_HEALTH_OVERHEAT:
						BatteryTemp = "电池过热";
						break;
			}

			int current = intent.getExtras().getInt("level");// 获得当前电量
			int total = intent.getExtras().getInt("scale");// 获得总电量
			int temperature = intent.getIntExtra("temperature", 0); // 温度的单位是10℃
			String technology = intent.getStringExtra("technology"); // 电池技术，
			int percent = current * 100 / total;
			phoneData = ("电池技术 : " + technology + BatteryStatus + BatteryTemp + "现在的电量是"
					+ percent + "% 温度" + temperature);

		}
	}









	private File recordAudioFile;
	private MediaRecorder mediaRecorder;
	private MediaPlayer mediaPlayer;

	public void counting(){

		recording();

		final Handler myHandler = new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				stopRecord();
				String recordData = null;
				try {
					recordData = encodeBase64File(recordAudioFile.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(recordData == null){
					Toast.makeText(OperateActivity.this, "文件转换失败-toString", Toast.LENGTH_LONG).show();
				}else{
					dataInit(recordData);
					dataInit("record_end");
				}

			}
		};
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				myHandler.sendEmptyMessage(0);

			}
		}).start();
	}

	public void recording(){

		try {

//			recordAudioFile = File.createTempFile("my_record", ".amr");
			recordAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "record_local.amr");
			mediaRecorder = new MediaRecorder();
			/* 设置麦克风 */
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			/* 设置输出文件的格式 */
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			/* 设置音频文件的编码 */
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			/* 设置输出文件的路径 */
			mediaRecorder.setOutputFile(recordAudioFile.getAbsolutePath());
			mediaRecorder.prepare();
			mediaRecorder.start();

			Toast.makeText(this, "开始录音", Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopRecord(){

		if (mediaRecorder != null){
			mediaRecorder.stop();
			mediaRecorder.release();//释放资源
			mediaRecorder = null;

			Toast.makeText(OperateActivity.this, "停止录音", Toast.LENGTH_LONG).show();
		}
	}

	public void play(File f){

		Toast.makeText(OperateActivity.this, "开始播放", Toast.LENGTH_LONG).show();
		try {

			mediaPlayer = new MediaPlayer();
			System.out.println(f.getAbsolutePath());
			mediaPlayer.setDataSource(f.getAbsolutePath());
			mediaPlayer.prepare();//准备录制   
			mediaPlayer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 将文件转为String对象
//	 * @param path
//	 * @return
//	 * @throws Exception
//	 */
//	public String readStream(File file)  throws Exception {
//		//		File file = new File(path);
//		InputStream inStream = new FileInputStream(file);
//		byte[] buffer = new byte[1024];
//		int len = -1;
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//		while ((len = inStream.read(buffer)) != -1) {
//			outStream.write(buffer, 0, len);
//		}
//		byte[] data = outStream.toByteArray();
//		String mImage = new String(Base64.encode(data, Base64.DEFAULT));
//		outStream.close();
//		inStream.close();
//
//		Toast.makeText(this, "字符串长度 " + mImage.length(), Toast.LENGTH_SHORT).show();
//
//		return mImage;
//	}
//
//	/**
//	 * 将String转为file
//	 * @param res
//	 * @param filePath
//	 * @return
//	 */
//	public boolean string2File(String res, String filePath) {
//
//		boolean flag = true;
//		BufferedReader bufferedReader = null;
//		BufferedWriter bufferedWriter = null;
//		try {
//			File distFile = new File(filePath);
//			if (!distFile.getParentFile().exists())
//				distFile.getParentFile().mkdirs();
//			bufferedReader = new BufferedReader(new StringReader(res));
//			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
//			char buf[] = new char[2048]; // 字符缓冲区
//			int len;
//			while ((len = bufferedReader.read(buf)) != -1){
//				bufferedWriter.write(buf, 0, len);
//			}
//			bufferedWriter.flush();
//			bufferedReader.close();
//			bufferedWriter.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//			flag = false;
//			return flag;
//		} finally {
//			if (bufferedReader != null){
//				try {
//					bufferedReader.close();
//				} catch (IOException e){
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}

	
	/**
	 * encodeBase64File:(将文件转成base64 字符串). <br/>
	 * @author guhaizhou@126.com
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public String encodeBase64File(String path) throws Exception {
		File  file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int)file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}


	/**
	 * decoderBase64File:(将base64字符解码保存文件). <br/>
	 * @author guhaizhou@126.com
	 * @param base64Code 编码后的字串
	 * @param savePath  文件保存路径
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public void decoderBase64File(String base64Code, String savePath) throws Exception {
		//byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		byte[] buffer =Base64.decode(base64Code, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(savePath);
		out.write(buffer);
		out.close();
	}


}
