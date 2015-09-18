package com.example.blu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.aalj35.aa11.Receiver.InCallReceiver;
import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.utils.SendData;
import com.zhilian.blu.shouhuan.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import record.sqlite.SQLite_Activity;
import record.sqlite.SQlite;

public class ThreeMainActivity extends Activity {
	BluetoothAdapter bluetoothAdapter = null;
	BluetoothDevice bluetoothDevice = null;
	BluetoothSocket bluetoothSocket = null;
	public static BluetoothServerSocket bluetoothServerSocket = null;
	private OutputStream os;
	boolean exit = false;

	// ����ֵ
	public static int num = 0;
	// ����ֵ
	private int stepnum = 0;
	// ȡ�ص�����ֵ
	String str = "";
	/**
	 * ������Ҫ��
	 */
	Calendar cal = Calendar.getInstance();
	Vibrator vibrator = null;
	/**
	 * ���������
	 */
	// private AlarmManager alarmManager=null;
	/**
	 * ������ʾ����
	 */
	private TextView text = null;
	/**
	 * ���ڴ���ģ�������
	 */
	private EditText edit = null;
	/**
	 * ��ʾ�˶���
	 */
	TextView address = null;
	/**
	 * ��ȡ��ص����İ�ť
	 */
	Button battery = null;
	/**
	 * ��ȡ�ֻ�����İ�ť
	 */

	Button alarm = null;
	/**
	 * ��ʶ�����İ�ť
	 */
	Button lock = null;
	/**
	 * ��ʶ�������ѵĿ���
	 */
	boolean call = false;
	/**
	 * ������ť
	 */
	boolean lock_temp = false;
	/**
	 * �𶯵İ�ť
	 */
	Button cancalshake = null;
	/**
	 * ��ʹ��
	 */
	boolean vibrator_temp = false;
	/**
	 * �����㲥������
	 */
	private InCallReceiver inCallReceiver = null;
	public static int currentIntent = 0;
	int time = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_three_main);
		viewInit();

	}


	/**
	 * 
	 * viewInit:(ҳ��ؼ���ʼ��)
	 * 
	 * @param �趨�ļ�
	 * @return void DOM����
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */

	private void viewInit() {
		// �绰�Ĺ㲥�����߶���
		inCallReceiver = new InCallReceiver();
		cancalshake = (Button) findViewById(R.id.cancalshake);
		// ��̬ע������㲥
		// IntentFilter fk
		// �������ӵİ�ť
//		alarm = (Button) findViewById(R.id.clock);
		lock = (Button) findViewById(R.id.lock);
//		battery = (Button) findViewById(R.id.electric);
		// �ֻ��𶯳�ʼ��
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// ��������������
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// ���������ģ��ĵ��Ʋ�����
		edit = (EditText) findViewById(R.id.edit);
		// �����߳�
		new BlueThread().start();
		// new Connnet().start();
	}

	/**
	 * 
	 * onclick:(��ť���������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param @param
	 *            v �趨�ļ�
	 * @return void DOM����
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public void onclick(View v) {

		System.out.println("��ť�Ѿ����");
		switch (v.getId()) {
//		case R.id.data:
//			SendData.dataInit(Changliang.SEND);
//			break;
//		case R.id.incomming:// ��������
//			Button button = (Button) findViewById(R.id.incomming);
//			IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
//			// ͬһ����ť��ʵ����������
//			// �����һ���Ǵ򿪵绰����
//			// ����ڶ����ǹرյ绰����
//			if (!call) {
//				ThreeMainActivity.this.registerReceiver(inCallReceiver, intentFilter);
//				button.setText("�������ѣ��ѿ�");
//				call = true;
//			} else {
//
//				ThreeMainActivity.this.unregisterReceiver(inCallReceiver);
//				button.setText("�������ѣ��ѱ�");
//				call = false;
//			}
//			break;

		case R.id.cancalshake:// ��
			if(vibrator_temp){//ȡ����
				vibrator.cancel();
				SendData.dataInit(Changliang.CANCALSHAKE);
				cancalshake.setText("��");
				vibrator_temp = false;
				
			}else{//��
				SendData.dataInit(Changliang.SHAKE);
				cancalshake.setText("ȡ����");
				vibrator_temp = true;
				
			}
			break;
		
		case R.id.lock:// ���������
			if (lock_temp) {

				SendData.dataInit(Changliang.UNLOCK);
				lock.setText("����");
				lock_temp = false;
			} else {
				SendData.dataInit(Changliang.LOCK);
				lock.setText("����");
				lock_temp = true;
			}

//			break;
		// case R.id.unlock:// ����
		//
		//
		// break;
//		case R.id.electric:// ��ȡ����
//
//			SendData.dataInit(Changliang.BATTERYNUM);
//			break;
//		case R.id.clock:// ��������
//			// �����Ի�������ʱ��
//			popdia();
//
//			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * popdia:(���������ü�ʱ) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param �趨�ļ�
	 * @return void DOM����
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public void popdia() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ThreeMainActivity.this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("���ü�ʱ��ʱ��");

		LayoutInflater inflater = LayoutInflater.from(ThreeMainActivity.this);
		View view = inflater.inflate(R.layout.text, null);
		final EditText num = (EditText) view.findViewById(R.id.jitime);
		builder.setView(view);

		builder.setPositiveButton("�ύ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				String temp = num.getText().toString().trim();
				time = Integer.parseInt(temp);
				System.out.println(time);
				new JiTime(time).start();
			}
		});

		builder.create().show();

	}

	/**
	 * 
	 * ClassName:JiTime Function: ��ʱ���߳� Reason: TODO ADD REASON
	 *
	 * @author view
	 * @version BluetoothFuntion
	 * @since Ver 1.1
	 * @Date 2015 2015��8��31�� ����3:43:59
	 *
	 * @see
	 */
	public class JiTime extends Thread {
		int num = 0;

		public JiTime(int num) {
			this.num = num;
		}

		@Override
		public void run() {
			try {
				System.out.println("��ʱ��ʼ");
				Thread.sleep(num*1000);
				Message msg = Message.obtain();
				// ������Ϣ������ obj��ʾ Object
				msg.obj = "��ʱ";
				// ������Ϣ
				handler.sendMessage(msg);

			} catch (InterruptedException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}

	/**
	 * ��Ϣ���� ���ڰɽ��յ�����Ϣ���ص���ǰ�Ĳ�������
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			long[] pattern = { 100, 400, 100, 400 };
			String s = (String) msg.obj;
			System.out.println("SSSSSSSSSSSSS" + s);
			// ��ȡ�������ж�
			if (s.substring(s.indexOf(":") + 1).trim().equals("1")) {
				battery.setText("����ʣ�ࣺ" + s.substring(0, s.indexOf(":")).trim());
				System.out.println("�����Ƿ񴫵�");
				new DianLiangThread().start();
			}
			if (s.substring(s.indexOf(":") + 1).trim().equals("2")) {

				edit.setText( s.substring(0, s.indexOf(":")).trim());
				str = s.substring(0, s.indexOf(":")).trim();
				System.out.println("str    :" + str);
				sql_step(str);
			}
			/**
			 * ���û�ȡ���Ӱ�ť��
			 */

			if (s.equals("��ȡ����")) {

				battery.setText("��ȡ����");
			}
			if (s.equals("��ʱ")) {

				SendData.dataInit(Changliang.SHAKE);
			}
			switch (s) {
			case Changliang.SEND:// Ŀǰ����һ�����Ӳ���
				System.out.println("�������ڷ������棬���ڽ�������");

				SendData.dataInit(edit.getText().toString() + ":2");
				break;

			case Changliang.SHAKE:// ��

				vibrator.vibrate(pattern, 2);
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.CANCALSHAKE:// ȡ����
				vibrator.cancel();
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.LOCK:// �����ֻ�
				myLock();
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.UNLOCK:// �����ֻ�
				myUnLock();
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.ALARM:// �ֻ����� ������������������ȡ��

				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.BATTERYNUM:// �����ķ���
				System.out.println("��ȡ����");
				getEnergy(battery);
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;

			default:
				break;
			}

		};
	};

	/**
	 * 
	 * ClassName:BlueThread Function: ����һ���߳�ִ�н������ӹ��� Reason: TODO ADD REASON
	 *
	 * @author view
	 * @version BluetoothFuntion
	 * @since Ver 1.1
	 * @Date 2015 2015��8��24�� ����11:32:02
	 *
	 * @see
	 */
	private class BlueThread extends Thread {

		private InputStream is;

		public BlueThread() {

			try {
				// �˷�����������BluetoothServerSocket�Ķ���
				// ��һ��������ʾ������������ƣ������������ַ���
				// �ڶ���������UUID
				bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(Changliang.NAME,
						Changliang.MY_UUID);
			} catch (IOException e) {

			}
		}

		public void run() {

			try {
				// ͨ��BluetoothServerSocket.accept();�����յ��ͻ��˵������
				// accept()�����᷵��һ��BluetoothSocket���󣬿���ͨ���ö����ö�д���ݵ�InputStream��OutputStream����
				// �ȴ����������ͻ��˵�����
				bluetoothSocket = bluetoothServerSocket.accept();

				// ͨ��ѭ�����Ͻ��տͻ��˷����������ݡ�����ͻ�����ʱû�����ݣ���read������������״̬
				while (!exit) {
					is = bluetoothSocket.getInputStream();
					os = bluetoothSocket.getOutputStream();
					byte[] buffer = new byte[2048];
					int count = is.read(buffer);

					String recvData = new String(buffer, 0, count, "utf-8");
					// ����Ϣ�������淢������
					// message ������Ϣ�ĳ�����
					Message msg = Message.obtain();
					// ������Ϣ������ obj��ʾ Object
					msg.obj = recvData;
					// ������Ϣ
					handler.sendMessage(msg);
				}

			} catch (Exception e) {

			}
		}
	}

	/**
	 * ��ȡ�ֻ�����
	 */

	public void getEnergy(View v) {

		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		ThreeMainActivity.this.registerReceiver(receiver1, filter);

	}

	// **********************************ע���صĹ㲥*********************
	private BroadcastReceiver receiver1 = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("���������㲥");
			String BatteryStatus = ""; // ���״̬
			String BatteryTemp = ""; // ���ʹ�����

			int current = intent.getExtras().getInt("level");// ��õ�ǰ����
			int total = intent.getExtras().getInt("scale");// ����ܵ���
			System.out.println("��õ�ǰ����" + current);

			SendData.dataInit(current + ":1");
			ThreeMainActivity.this.unregisterReceiver(receiver1);
			int temperature = intent.getIntExtra("temperature", 0); // �¶ȵĵ�λ��10��
			String technology = intent.getStringExtra("technology"); // ��ؼ�����
			int percent = current * 100 / total;
			String phoneData = ("��ؼ��� : " + technology + BatteryStatus + BatteryTemp + "���ڵĵ�����" + percent + "% �¶�"
					+ temperature);
			System.out.println("phoneData    " + phoneData);
		}
	};

	/************************* �ı��ȡ�����İ�ť����ʾ ****/
	private class DianLiangThread extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(3000);

				Message msg = Message.obtain();
				// ������Ϣ������ obj��ʾ Object
				msg.obj = "��ȡ����";
				// ������Ϣ
				handler.sendMessage(msg);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			super.run();

		}

	}

	/**
	 * ��Ļ��������
	 */
	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	// ����
	public void myLock() {
		// ����豸��������
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		// LockReceiver�̳���DeviceAdminReceiver
		mComponentName = new ComponentName(this, com.aalj35.aa11.Receiver.LockReceiver.class);
		// �ж��Ƿ���Ȩ��
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow();
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

	}

	public void myUnLock() {
		// ��Ļ����
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock keyguardLock = km.newKeyguardLock("unLock");
		// ����
		keyguardLock.disableKeyguard();

		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		PowerManager.WakeLock wl = pm
				.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wl.acquire();
		// �ͷ�
		wl.release();
	}

	/**
	 * 
	 * sql_step:(��ѯ��ť��ʵ����ת��������ʾҳ��)
	 *
	 * @param @param
	 *            v �趨�ļ�
	 * @return void DOM����
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public void sql_step(View v) {

		Intent intent = new Intent();
		intent.setClass(ThreeMainActivity.this, SQLite_Activity.class);
		ThreeMainActivity.this.startActivity(intent);

	}

	/**
	 * 
	 * sql_step:(�Ի�õ����ݽ��д���) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param @param
	 *            v �趨�ļ�
	 * @return void DOM����
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public void sql_step(String v) {
		Date date = new Date();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		if (str.equals("")) {
			stepnum = 0;
		} else {
			stepnum = Integer.parseInt(v);
		}

		// ����ֵ
		num++;

		SQlite helper = new SQlite(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "insert into step(stepnum,time,num) values('" + stepnum + "','" + time + "', '" + num + "' ) ";
		db.execSQL(sql);

	}

}