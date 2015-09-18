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
 * @Function: 服务器通讯套接字BluetoothServerSocket蓝牙通讯套接字BluetoothSocket
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015年8月25日 下午2:59:30
 *
 * @see
 */
public class ThirdActivity extends Activity {

	private TextView tv_name;
	// 输入框
	private EditText et_input;
	// 震动
	private Vibrator vibrator;
	// 适配器
	BluetoothAdapter bt_adapter;
	// 远程设备
	BluetoothDevice bt_device;
	// 监听服务端口
	BluetoothServerSocket bluetoothServerSocket;
	//
	BluetoothSocket bluetoothSocket;
	// 客户端
	BluetoothSocket client_Socket;
	// 输出流
	private OutputStream os;
	// 标识
	boolean flag = false;

	// 震动标识
	private String shake = "com.shake";
	// 取消震动标识
	private String unshake = "com.unshake";
	// 锁屏
	private String lock = "com.lock";
	// 解锁
	private String unlock = "com.unlock";
	private String time = "com.time";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		viewInit();
	}

	// 实现获取蓝牙设备名称
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

	// 接受者
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (String.valueOf(msg.obj).contains("距离")) {
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

	// 线程
	private class BlueThread extends Thread {
		// 输入流
		private InputStream is;

		public BlueThread() {
			// TODO Auto-generated constructor stub
			// 监听服务端口
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
			 * 为了创建一个对准备好的新来的连接去进行监听BluetoothServerSocket类，使用
			 * BluetoothAdapter.listenUsingRfcommWithServiceRecord
			 * ()方法。然后调用accept()方法去监 听该链接的请求。
			 */
			try {
				/*
				 * 蓝牙通讯套接字，代表了与远端设备的连接点，
				 * 使用socket本地程序可以通过inputstream和outputstream与远端程序进行通讯。
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
					// 向消息机制里面发送数据
					// message 发送消息的承载体
					Message msg = Message.obtain();
					// 发送消息的类型 obj表示 Object
					msg.obj = recvData;
					// 发送消息
					handler.sendMessage(msg);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 发送按钮的监听
	// public void send(View v) {
	// dataInit(et_input.getText().toString());
	// }

	// 连接
	private void dataInit(String data) {
		try {
			// 如果远程设备为null
			if (bt_device == null) {
				bt_device = bt_adapter.getRemoteDevice(getIntent()
						.getStringExtra(SecondActivity.NAME));
			}
			if (client_Socket == null) {
				// 通过UUID连接蓝牙
				client_Socket = bt_device
						.createRfcommSocketToServiceRecord(SecondActivity.MY_UUID);
				// 蓝牙的连接
				client_Socket.connect();
				os = client_Socket.getOutputStream();
			}
			if (os != null) {
				os.write(data.getBytes("utf-8"));
				Toast.makeText(ThirdActivity.this, "信息发送成功", 0).show();
			}
		} catch (IOException e) {
			Toast.makeText(ThirdActivity.this, "信息发送失败", 0).show();
		}
	}

	public class Myonclick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_send:
				// 得到输入框内容
				String send = "距离" + et_input.getText().toString();
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

	// 震动
	public void shake() {
		// 获取震动权限
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// 震动时间，停止 震动 停止 震动
		long pattern[] = { 100, 400, 100, 400 };
		// 执行2次
		vibrator.vibrate(pattern, 2);
	}

	// 取消震动
	public void unshake() {
		vibrator.cancel();
	}

	/**
	 * 屏幕锁定部分
	 */
	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	// 锁屏
	public void myLock() {
		// 获得设备管理服务
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		// LockReceiver继承自DeviceAdminReceiver
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
		// KeyguardManager keyguardManager = (KeyguardManager)
		// getSystemService(KEYGUARD_SERVICE);
		// KeyguardLock keyguardLock = keyguardManager.newKeyguardLock(MY_TAG);
		// keyguardLock.disableKeyguard(); //Can not Uses
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		// KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		KeyguardLock keyguardLock = km.newKeyguardLock("unLock");
		// 解锁
		keyguardLock.disableKeyguard();

		// 获取电源管理器对象
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		// 释放
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
