/**
 * SendData.java
 * com.aalj35.aa11.utils
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��26�� 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11.utils;

import java.io.IOException;
import java.io.OutputStream;

import com.aalj35.aa11.BlueToothList;
import com.aalj35.aa11.mystart.Changliang;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * ClassName:SendData
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015��8��26��		����2:36:17
 *
 * @see 	 

 */
public class SendData {
	public static BluetoothDevice bluetoothDevice = null;
	public static BluetoothAdapter bluetoothAdapter = BluetoothAdapter .getDefaultAdapter();
	public static BluetoothSocket bluetoothSocket = null;
	static OutputStream os = null;
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
	public  static   boolean dataInit(String data ) {
		try {
			
			if(bluetoothDevice==null){
				System.out.println("BlueToothList.address    "+BlueToothList.address);
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
			return true;
		} catch (IOException e) {
//			Toast.makeText(getApplicationContext(), "��Ϣ����ʧ��", 0).show();
			
			e.printStackTrace();
			return false;
			
		}
		
	}
	

}

