/**
 * BlueThread.java
 * com.aalj35.aa11.utils
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��9��1�� 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.aalj35.aa11.mystart.Changliang;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

/**
 * ClassName:BlueThread Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 *
 * @author view
 * @version
 * @since Ver 1.1
 * @Date 2015��9��1�� ����3:23:33
 *
 * @see
 * 
 */
public class BlueThread extends Thread {
	BluetoothAdapter bluetoothAdapter = null;
	BluetoothDevice bluetoothDevice = null;
	BluetoothSocket bluetoothSocket = null;
	public static String recvData = null;
	public static BluetoothServerSocket bluetoothServerSocket = null;
	private OutputStream os;
	boolean exit = false;
	public static boolean send_falg = false;
	private InputStream is;
	Handler handler = null;

	public BlueThread() {
		Changliang.flag_handler = false;
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
			String temp = null;
			// ͨ��ѭ�����Ͻ��տͻ��˷����������ݡ�����ͻ�����ʱû�����ݣ���read������������״̬
			while (!exit) {
				System.out.println("xunhuan");
				is = bluetoothSocket.getInputStream();
				os = bluetoothSocket.getOutputStream();
				byte[] buffer = new byte[2048];
				int count = is.read(buffer);

				  recvData = new String(buffer, 0, count, "utf-8");
				  if(recvData.equals(temp)){
					  send_falg = true;
				  }
				  temp =recvData; 
				if (Changliang.flag_handler) {

					// ����Ϣ�������淢������
					// message ������Ϣ�ĳ�����
					Message msg = Message.obtain();
					// ������Ϣ������ obj��ʾ Object
					msg.obj = recvData;
					// ������Ϣ
					if (handler != null) {
						handler.sendMessage(msg);
					}

				}
			}
					  

		} catch (Exception e) {

	}
	}
}