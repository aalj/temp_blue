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
	 * ��������
	 */
	static String MY_TAG = "��Ұ����";

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
		// ��Intent���и���keyȡ��value
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
				
				String com = "����-" + et.getText().toString();
				if(isNumeric(et.getText().toString())){
					dataInit(com);
				}else{
					Toast.makeText(OperateActivity.this, "����д��ȷ����", Toast.LENGTH_SHORT).show();
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
				setTitle("��ʼԶ��¼��...");
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
					// ��������豸���൱������ͻ���Socketָ��IP��ַ
					device = bluetoothAdapter.getRemoteDevice(address);
				}

				if (clientSocket == null) {
					// ͨ��UUID���������豸���൱������ͻ���Socketָ���˿ں�
					clientSocket = device.createRfcommSocketToServiceRecord(BlueSocketActivity.MY_UUID);
					// ��ʼ���������豸
					clientSocket.connect();
					System.out.println("��ʼ���������豸");
					// ��������˷������ݵ�OutputStream����
					os = clientSocket.getOutputStream();
				}
			} catch (IOException e) {

			}
			if (os != null) {
				// �����˷���һ���ַ���
				os.write(data.getBytes("utf-8"));
				Toast.makeText(this, "��Ϣ���ͳɹ�.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "��Ϣ����ʧ��.", Toast.LENGTH_LONG).show();
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
				if(receiveData.startsWith("��ؼ���")){
					tv.setText(String.valueOf(msg.obj));
				}else{

					if(receiveData.equals("record_end")){
					
						System.out.println("�ɹ�");
						setProgressBarIndeterminateVisibility(false);
						setTitle("¼������ɹ�");     
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
//							Toast.makeText(OperateActivity.this, "�ļ�ת��ʧ��-toFile", Toast.LENGTH_LONG).show();
//						}
					}else{
						recordData = recordData + receiveData;
						System.out.println("׷��");
					}

					setTitle("��������");     
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
				// �˷�����������BluetoothServerSocket�Ķ���
				// ��һ��������ʾ������������ƣ������������ַ���
				// �ڶ���������UUID
				serverSocket = bluetoothAdapter
						.listenUsingRfcommWithServiceRecord(
								BlueSocketActivity.NAME,
								BlueSocketActivity.MY_UUID);
			} catch (IOException e) {

			}
		}

		public void run() {

			try {
				// ͨ��BluetoothServerSocket.accept();�����յ��ͻ��˵������
				// accept()�����᷵��һ��BluetoothSocket���󣬿���ͨ���ö����ö�д���ݵ�InputStream��OutputStream����
				// �ȴ����������ͻ��˵�����
				socket = serverSocket.accept();

				// ͨ��ѭ�����Ͻ��տͻ��˷����������ݡ�����ͻ�����ʱû�����ݣ���read������������״̬
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
	 * ��Ļ��������
	 */

	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	public void myLock() {
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(this, LockReceiver.class);
		// �ж��Ƿ���Ȩ��
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow();
			// �������ж�����ʹ����android4.2��
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);
		} else {
			activeManager();
		}
	}

	private void activeManager() {
		// �����豸��������ȡȨ��
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "��˵�е�һ������");
		startActivity(intent);
		finish();
	}

	public void myUnLock() {
		// ��Ļ����
		//		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		//		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock(MY_TAG);
		//		keyguardLock.disableKeyguard(); //Can not Uses
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// ����
		kl.disableKeyguard();
		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wl.acquire();
		// �ͷ�
		wl.release();
	}


	/**
	 * �𶯷���
	 */

	private Vibrator vibrator;

	public void shake() {
		/*
		 * �������𶯴�С����ͨ���ı�pattern���趨���������ʱ��̫�̣���Ч�����ܸо�����
		 */
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 100, 400, 100, 400 }; // ֹͣ ���� ֹͣ ����
		vibrator.vibrate(pattern, 2); // �ظ����������pattern ���ֻ����һ�Σ�index��Ϊ-1
	}

	public void shakeCancel() {
		vibrator.cancel();
	}

	/**
	 * ��ȡ�ֻ�����
	 */
	private BatteryReceiver receiver = null;

	public void getEnergy(View v) {

		if(v.getTag() == null){
			v.setTag(true);
		}

		//��ȡ��ص���
		if((Boolean) v.getTag()){
			IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			registerReceiver(receiver, filter);//ע��BroadcastReceiver
			v.setTag(false);
		}else {
			//ֹͣ��ȡ��ص���
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

			String BatteryStatus = ""; // ���״̬
			String BatteryTemp = ""; // ���ʹ�����

			switch (intent.getIntExtra("status",
					BatteryManager.BATTERY_STATUS_UNKNOWN)) {
					case BatteryManager.BATTERY_STATUS_CHARGING:
						BatteryStatus = "���״̬";
						break;
					case BatteryManager.BATTERY_STATUS_DISCHARGING:
						BatteryStatus = "�ŵ�״̬";
						break;
					case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
						BatteryStatus = "δ���";
						break;
					case BatteryManager.BATTERY_STATUS_FULL:
						BatteryStatus = "������";
						break;
					case BatteryManager.BATTERY_STATUS_UNKNOWN:
						BatteryStatus = "δ֪��״̬";
						break;
			}

			switch (intent.getIntExtra("health",
					BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
					case BatteryManager.BATTERY_HEALTH_UNKNOWN:
						BatteryTemp = "δ֪����";
						break;
					case BatteryManager.BATTERY_HEALTH_GOOD:
						BatteryTemp = "״̬����";
						break;
					case BatteryManager.BATTERY_HEALTH_DEAD:
						BatteryTemp = "���û�е�";
						break;
					case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
						BatteryTemp = "��ص�ѹ����";
						break;
					case BatteryManager.BATTERY_HEALTH_OVERHEAT:
						BatteryTemp = "��ع���";
						break;
			}

			int current = intent.getExtras().getInt("level");// ��õ�ǰ����
			int total = intent.getExtras().getInt("scale");// ����ܵ���
			int temperature = intent.getIntExtra("temperature", 0); // �¶ȵĵ�λ��10��
			String technology = intent.getStringExtra("technology"); // ��ؼ�����
			int percent = current * 100 / total;
			phoneData = ("��ؼ��� : " + technology + BatteryStatus + BatteryTemp + "���ڵĵ�����"
					+ percent + "% �¶�" + temperature);

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
					Toast.makeText(OperateActivity.this, "�ļ�ת��ʧ��-toString", Toast.LENGTH_LONG).show();
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
			/* ������˷� */
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			/* ��������ļ��ĸ�ʽ */
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			/* ������Ƶ�ļ��ı��� */
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			/* ��������ļ���·�� */
			mediaRecorder.setOutputFile(recordAudioFile.getAbsolutePath());
			mediaRecorder.prepare();
			mediaRecorder.start();

			Toast.makeText(this, "��ʼ¼��", Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopRecord(){

		if (mediaRecorder != null){
			mediaRecorder.stop();
			mediaRecorder.release();//�ͷ���Դ
			mediaRecorder = null;

			Toast.makeText(OperateActivity.this, "ֹͣ¼��", Toast.LENGTH_LONG).show();
		}
	}

	public void play(File f){

		Toast.makeText(OperateActivity.this, "��ʼ����", Toast.LENGTH_LONG).show();
		try {

			mediaPlayer = new MediaPlayer();
			System.out.println(f.getAbsolutePath());
			mediaPlayer.setDataSource(f.getAbsolutePath());
			mediaPlayer.prepare();//׼��¼��   
			mediaPlayer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * ���ļ�תΪString����
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
//		Toast.makeText(this, "�ַ������� " + mImage.length(), Toast.LENGTH_SHORT).show();
//
//		return mImage;
//	}
//
//	/**
//	 * ��StringתΪfile
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
//			char buf[] = new char[2048]; // �ַ�������
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
	 * encodeBase64File:(���ļ�ת��base64 �ַ���). <br/>
	 * @author guhaizhou@126.com
	 * @param path �ļ�·��
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
	 * decoderBase64File:(��base64�ַ����뱣���ļ�). <br/>
	 * @author guhaizhou@126.com
	 * @param base64Code �������ִ�
	 * @param savePath  �ļ�����·��
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
