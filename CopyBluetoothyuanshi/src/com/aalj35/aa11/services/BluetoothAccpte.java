/**
 * BluetoothAccpte.java
 * com.aalj35.aa11.services
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��24�� 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.utils.SendData;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * ClassName:BluetoothAccpte
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015��8��24��		����2:16:55
 *
 * @see 	 

 */
public class BluetoothAccpte extends Service {
	
	
	BluetoothAdapter bluetoothAdapter = null;
	BluetoothDevice  bluetoothDevice = null;
	BluetoothSocket bluetoothSocket= null;
	public static BluetoothServerSocket bluetoothServerSocket = null;
	private OutputStream os;
	boolean exit  = false;
	Vibrator vibrator = null;
	public BluetoothAccpte() {
		System.out.println("��֤���캯���Ƿ�����");
	
	}
	@Override
	public IBinder onBind(Intent intent) {
		
		
		return null;
		
	}

	@Override
	public void onCreate() {
		
		
		
		 vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		System.out.println("������");
		new BlueThread ().start();
		
		
		super.onCreate();
		
	}

	@Override
	public void onDestroy() {
		
		
		super.onDestroy();
		
	}
	
	
	/**
	 * ��Ϣ����   ���ڰɽ��յ�����Ϣ���ص���ǰ�Ĳ�������
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			switch (s) {
			case Changliang.SEND:
				System.out.println("�������ڷ������棬���ڽ�������");
				Changliang.SUCCESS_FLAGS = "3";
				SendData.dataInit("lklk");
				break;

			case Changliang.SHAKE:
				long[] pattern = { 100, 400, 100, 400 };
				vibrator.vibrate(pattern, 2);
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.CANCALSHAKE:
				vibrator.cancel();
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.LOCK:
				myLock();
				Toast.makeText(getApplicationContext(), s, 0).show();
				break;
			case Changliang.UNLOCK:

				Toast.makeText(getApplicationContext(), s, 0).show();
				break;

			default:
				break;
			}

		};
	};
	
	
	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	public void myLock() {
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(getApplicationContext(), com.aalj35.aa11.Receiver.LockReceiver.class);
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
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "��˵�е�һ������");
		startActivity(intent);
		
	}
	
	
	
	/**
	 * 
	 * ClassName:BlueThread
	 * Function: ����һ���߳�ִ�н������ӹ���
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  BluetoothFuntion
	 * @since    Ver 1.1
	 * @Date	 2015	2015��8��24��		����11:32:02
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
				bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(Changliang.NAME,Changliang.MY_UUID);
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

					System.out.println(count + "---------------");
					String recvData = new String(buffer, 0, count, "utf-8");
					
					System.out.println("----" + recvData );
					//����Ϣ�������淢������
					//message  ������Ϣ�ĳ����� 
					Message msg = Message.obtain();
					//������Ϣ������    obj��ʾ Object
					msg.obj = recvData;
					//������Ϣ
					handler.sendMessage(msg);
				}

			} catch (Exception e) {

			}
		}
	}

}
