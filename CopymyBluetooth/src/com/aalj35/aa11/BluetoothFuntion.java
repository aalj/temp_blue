/**
 * BluetoothFuntion.java
 * com.aalj35.aa11
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��24�� 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11;

import java.io.IOException;
import java.io.OutputStream;

import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa12.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ClassName:BluetoothFuntion
 * Function: ʵ����������
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015��8��24��		����9:43:23
 *
 * @see 	 

 */
public class BluetoothFuntion extends Activity {
	//unknow 
	private TextView text  = null;
	private EditText edit  = null;
	//	BluetoothAdapter bluetoothAdapter = null;
	static BluetoothDevice  bluetoothDevice = null;
	static BluetoothSocket bluetoothSocket= null;
	BluetoothServerSocket bluetoothServerSocket = null;
	private static   OutputStream os  = null;
	boolean exit  = false;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funtion);
		viewInit();
	}
	/**
	 * 
	 * onclick:(��ť���������)
	 * TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)
	 * TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param  @param v    �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	public void onclick(View v){
		
		System.out.println("��ť�Ѿ����");
		switch (v.getId()) {
		case R.id.send:
			 dataInit(Changliang.SEND );
			break;
		
			
		case R.id.shake:
			dataInit(Changliang.SHAKE);
			break;
		case R.id.cancalshake:
			dataInit(Changliang.CANCALSHAKE);
			break;
		case R.id.lock:
			dataInit(Changliang.LOCK);
			break;
		case R.id.unlock:
			dataInit(Changliang.UNLOCK);
			break;

		default:
			break;
		}
		
		
		
		
		
	}
	
	
	
	/**
	 * 
	 * viewInit:(ҳ��ؼ���ʼ��)
	 * @param      �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	private void viewInit() {
		
		
		text = (TextView) findViewById(R.id.text);
		edit = (EditText) findViewById(R.id.editText1);
		text.setText(getIntent().getStringExtra(Changliang.NAME));
		//�����߳�
//		new BlueThread().start();
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * dataInit:(�������䷽��   �Լ����ӷ���)
	 * TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)
	 * TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param  @param data    �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	public static  void dataInit(String data ) {
		try {
			if(bluetoothDevice != null|| bluetoothSocket != null){
				bluetoothDevice = null;
				bluetoothSocket = null;
			}
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if(bluetoothDevice==null){
				System.out.println(bluetoothAdapter);
				bluetoothDevice = bluetoothAdapter.getRemoteDevice(BlueToothList.address);
			}
			if(bluetoothSocket==null){
				//ͨ��UUID��������  
				bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(Changliang.MY_UUID);
				//����������
				bluetoothSocket.connect();
				
				os = bluetoothSocket.getOutputStream();
			}
			if(os!=null){
				os.write(data.getBytes("utf-8"));
//				Toast.makeText(getApplicationContext(), "��Ϣ���ͳɹ�", 0).show();
			}else{
				
			}
		} catch (IOException e) {
//			Toast.makeText(getApplicationContext(), "��Ϣ����ʧ��", 0).show();
			
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/******************************************�������������յĲ���      �Է��÷�������*************************/
	
	
//	/**
//	 * ��Ϣ����   ���ڰɽ��յ�����Ϣ���ص���ǰ�Ĳ�������
//	 */
//	private Handler handler = new Handler(){
//		public void handleMessage(Message msg) {
//			String s = (String)msg.obj;
//			edit.setText(s);
//			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//			long[] pattern = { 100, 400, 100, 400 }; // ֹͣ ���� ֹͣ ����
//			vibrator.vibrate(pattern, 2); // �ظ����������pattern ���ֻ����һ�Σ�index��Ϊ-1
//			
//			
//			
////			switch (s) {
//			
////			case "test":
////				Toast.makeText(BluetoothFuntion.this, "���Գɹ����", 0).show();
////				break;
////
////			default:
////				break;
////			}
//			
//			
//			
//			
//			
//			
//		};
//	};
//	
//	
//	
//	
//	
//	
//	/**
//	 * 
//	 * ClassName:BlueThread
//	 * Function: ����һ���߳�ִ�н������ӹ���
//	 * Reason:	 TODO ADD REASON
//	 *
//	 * @author   view
//	 * @version  BluetoothFuntion
//	 * @since    Ver 1.1
//	 * @Date	 2015	2015��8��24��		����11:32:02
//	 *
//	 * @see
//	 */
//	private class BlueThread extends Thread {
//
//		private InputStream is;
//
//		public BlueThread() {
//
//			try {
//				// �˷�����������BluetoothServerSocket�Ķ���
//				// ��һ��������ʾ������������ƣ������������ַ���
//				// �ڶ���������UUID
//				bluetoothServerSocket = bluetoothAdapter
//						.listenUsingRfcommWithServiceRecord(
//								BlueToothList.NAME,
//								BlueToothList.MY_UUID);
//			} catch (IOException e) {
//
//			}
//		}
//
//		public void run() {
//
//			try {
//				// ͨ��BluetoothServerSocket.accept();�����յ��ͻ��˵������
//				// accept()�����᷵��һ��BluetoothSocket���󣬿���ͨ���ö����ö�д���ݵ�InputStream��OutputStream����
//				// �ȴ����������ͻ��˵�����
//				bluetoothSocket = bluetoothServerSocket.accept();
//
//				
//				// ͨ��ѭ�����Ͻ��տͻ��˷����������ݡ�����ͻ�����ʱû�����ݣ���read������������״̬
//				while (!exit) {
//					is = bluetoothSocket.getInputStream();
//					os = bluetoothSocket.getOutputStream();
//					byte[] buffer = new byte[2048];
//					int count = is.read(buffer);
//
//					System.out.println(count + "---------------");
//					String recvData = new String(buffer, 0, count, "utf-8");
////					System.out.println("----" + recvData );
//					//����Ϣ�������淢������
//					//message  ������Ϣ�ĳ����� 
//					Message msg = Message.obtain();
//					//������Ϣ������    obj��ʾ Object
//					msg.obj = recvData;
//					//������Ϣ
//					handler.sendMessage(msg);
//				}
//
//			} catch (Exception e) {
//
//			}
//		}
//	}
	/******************************************�������������յĲ���      �Է��÷�������*************************/

	

}

