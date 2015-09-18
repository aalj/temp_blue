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
import java.io.InputStream;
import java.io.OutputStream;

import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.utils.SendData;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ClassName:BluetoothFuntion
 * Function: ʵ����������   �÷���Ŀǰ�ǻ��ڷ������  ��ʱͣ��
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015��8��24��		����9:43:23
 *
 * @see 	 

 */
public class BluetoothFuntion2 extends Activity {
	//unknow 
	private TextView text  = null;
	private EditText edit  = null;
	//	BluetoothAdapter bluetoothAdapter = null;
	static BluetoothDevice  bluetoothDevice = null;
	static BluetoothSocket bluetoothSocket= null;
	BluetoothServerSocket bluetoothServerSocket = null;
	private static   OutputStream os  = null;
	boolean exit  = false;
	BluetoothAdapter bluetoothAdapter = null;
	
	
	
	
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
			SendData.dataInit(Changliang.SEND );
			break;
		
			
		case R.id.shake:
			SendData.dataInit(Changliang.SHAKE);
			break;
		case R.id.cancalshake:
			SendData.dataInit(Changliang.CANCALSHAKE);
			break;
		case R.id.lock:
			SendData.dataInit(Changliang.LOCK);
			break;
		case R.id.unlock:
			SendData.dataInit(Changliang.UNLOCK);
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
		
		  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		text = (TextView) findViewById(R.id.text);
		edit = (EditText) findViewById(R.id.editText1);
		text.setText(getIntent().getStringExtra(Changliang.NAME));
		//�����߳�
		new BlueThread().start();
	}
	
	
	/******************************************�������������յĲ���      �Է��÷�������*************************/
	
	
	/**
	 * ��Ϣ����   ���ڰɽ��յ�����Ϣ���ص���ǰ�Ĳ�������
	 */
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			String s = (String)msg.obj;
			System.out.println("���ӷ���");
				edit.setText(s);
//			switch (s) {
//			
//			case Changliang.SEND:
//				edit.setText();
//				break;
//
//			default:
//				break;
//			}
		};
	};
//	
//	
//	
//	
//	
//	
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
				bluetoothServerSocket = bluetoothAdapter
						.listenUsingRfcommWithServiceRecord(
								Changliang.NAME,
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

					System.out.println(count + "---------------");
					String recvData = new String(buffer, 0, count, "utf-8");
					System.out.println("�鿴�Ƿ�");
//					System.out.println("----" + recvData );
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
	/******************************************�������������յĲ���      �Է��÷�������*************************/

	

}
