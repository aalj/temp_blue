package com.example.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * ClassName:ThirdActivity
 * 
 * @Function: ������ͨѶ�׽���BluetoothServerSocket����ͨѶ�׽���BluetoothSocket
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015��8��25�� ����2:59:30
 *
 * @see
 */
public class ThirdActivity extends Activity {

	private TextView tv_name;
	// �����
	private EditText et_input;
	// ��
	private Vibrator vibrator;
	// ������
	BluetoothAdapter bt_adapter;
	// Զ���豸
	BluetoothDevice bt_device;
	// ��������˿�
	BluetoothServerSocket bluetoothServerSocket;
	//
	BluetoothSocket bluetoothSocket;
	// �ͻ���
	BluetoothSocket client_Socket;
	// �����
	private OutputStream os;
	// ��ʶ
	boolean flag = false;

	// �𶯱�ʶ
	private String shake = "com.shake";
	// ȡ���𶯱�ʶ
	private String unshake = "com.unshake";
	// ����
	private String lock = "com.lock";
	// ����
	private String unlock = "com.unlock";
	private String time = "com.time";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		viewInit();
	}

	// ʵ�ֻ�ȡ�����豸����
	public void viewInit() {
		Button btn_shake = (Button) findViewById(R.id.shake);
		Button btn_unshake = (Button) findViewById(R.id.unshake);
		Button btn_lock = (Button) findViewById(R.id.btn_lock);
		Button btn_unlock = (Button) findViewById(R.id.btn_unlock);
		Button btnclock = (Button) findViewById(R.id.btnclock);
		Button btn_send = (Button) findViewById(R.id.btn_send);

		btn_send.setOnClickListener(new Myonclick());
		btn_shake.setOnClickListener(new Myonclick());
		btn_unshake.setOnClickListener(new Myonclick());
		btn_lock.setOnClickListener(new Myonclick());
		btn_unlock.setOnClickListener(new Myonclick());
		btnclock.setOnClickListener(new Myonclick());

		bt_adapter = BluetoothAdapter.getDefaultAdapter();
		tv_name = (TextView) findViewById(R.id.tv_name);
		et_input = (EditText) findViewById(R.id.et_input);
		tv_name.setText(getIntent().getStringExtra(SecondActivity.NAME));
		new BlueThread().start();
	}

	// ������
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (String.valueOf(msg.obj).contains("����")) {
				String s = (String) msg.obj;
				et_input.setText(s);
				// Toast.makeText(ThirdActivity.this, "sdf", Toast.LENGTH_SHORT)
				// .show();
			} else if (String.valueOf(msg.obj).equals(shake)) {
				shake();
			} else if (String.valueOf(msg.obj).equals(unshake)) {
				unshake();
			} else if (String.valueOf(msg.obj).equals(lock)) {
				myLock();
			} else if (String.valueOf(msg.obj).equals(unlock)) {
				myUnLock();
			} else if (String.valueOf(msg.obj).equals(time)) {
				clock();
			}

		};
	};

	// �߳�
	private class BlueThread extends Thread {
		// ������
		private InputStream is;

		public BlueThread() {
			// TODO Auto-generated constructor stub
			// ��������˿�
			try {
				bluetoothServerSocket = bt_adapter
						.listenUsingRfcommWithServiceRecord(
								SecondActivity.NAME, SecondActivity.MY_UUID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			/*
			 * Ϊ�˴���һ����׼���õ�����������ȥ���м���BluetoothServerSocket�࣬ʹ��
			 * BluetoothAdapter.listenUsingRfcommWithServiceRecord
			 * ()������Ȼ�����accept()����ȥ�� �������ӵ�����
			 */
			try {
				/*
				 * ����ͨѶ�׽��֣���������Զ���豸�����ӵ㣬
				 * ʹ��socket���س������ͨ��inputstream��outputstream��Զ�˳������ͨѶ��
				 */
				bluetoothSocket = bluetoothServerSocket.accept();
				while (!flag) {
					is = bluetoothSocket.getInputStream();
					os = bluetoothSocket.getOutputStream();
					byte[] buffer = new byte[2048];
					int count = is.read(buffer);
					System.out.println(count + "---------------");
					String recvData = new String(buffer, 0, count, "utf-8");
					// System.out.println("----" + recvData );
					// ����Ϣ�������淢������
					// message ������Ϣ�ĳ�����
					Message msg = Message.obtain();
					// ������Ϣ������ obj��ʾ Object
					msg.obj = recvData;
					// ������Ϣ
					handler.sendMessage(msg);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ���Ͱ�ť�ļ���
	// public void send(View v) {
	// dataInit(et_input.getText().toString());
	// }

	// ����
	private void dataInit(String data) {
		try {
			// ���Զ���豸Ϊnull
			if (bt_device == null) {
				bt_device = bt_adapter.getRemoteDevice(getIntent()
						.getStringExtra(SecondActivity.NAME));
			}
			if (client_Socket == null) {
				// ͨ��UUID��������
				client_Socket = bt_device
						.createRfcommSocketToServiceRecord(SecondActivity.MY_UUID);
				// ����������
				client_Socket.connect();
				os = client_Socket.getOutputStream();
			}
			if (os != null) {
				os.write(data.getBytes("utf-8"));
				Toast.makeText(ThirdActivity.this, "��Ϣ���ͳɹ�", 0).show();
			}
		} catch (IOException e) {
			Toast.makeText(ThirdActivity.this, "��Ϣ����ʧ��", 0).show();
		}
	}

	public class Myonclick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_send:
				// �õ����������
				String send = "����" + et_input.getText().toString();
				dataInit(send);
				break;
			case R.id.shake:
				dataInit(shake);
				break;
			case R.id.unshake:
				dataInit(unshake);
				break;
			case R.id.btn_lock:
				dataInit(lock);
				break;
			case R.id.btn_unlock:
				dataInit(unlock);
				break;
			case R.id.btnclock:
				dataInit(time);
				break;
			default:
				break;
			}
		}
	}

	// ��
	public void shake() {
		// ��ȡ��Ȩ��
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// ��ʱ�䣬ֹͣ �� ֹͣ ��
		long pattern[] = { 100, 400, 100, 400 };
		// ִ��2��
		vibrator.vibrate(pattern, 2);
	}

	// ȡ����
	public void unshake() {
		vibrator.cancel();
	}

	/**
	 * ��Ļ��������
	 */
	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	// ����
	public void myLock() {
		// ����豸�������
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		// LockReceiver�̳���DeviceAdminReceiver
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
		// KeyguardManager keyguardManager = (KeyguardManager)
		// getSystemService(KEYGUARD_SERVICE);
		// KeyguardLock keyguardLock = keyguardManager.newKeyguardLock(MY_TAG);
		// keyguardLock.disableKeyguard(); //Can not Uses
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		// KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		KeyguardLock keyguardLock = km.newKeyguardLock("unLock");
		// ����
		keyguardLock.disableKeyguard();

		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wl.acquire();
		// �ͷ�
		wl.release();
	}

	public void clock() {

		Time time = new Time();
		time.setToNow();
		int hour = time.hour;
		int minute = time.minute;
		Toast.makeText(ThirdActivity.this, hour + "" + ":" + minute + "", 500)
				.show();
	}
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	//
	// if (KeyEvent.KEYCODE_POWER == keyCode) {
	//
	// }
	// return super.onKeyDown(keyCode, event);
	//
	// }
}
