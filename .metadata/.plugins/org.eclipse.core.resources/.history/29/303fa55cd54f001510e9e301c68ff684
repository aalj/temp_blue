/**
 * SendData.java
 * com.aalj35.aa11.utils
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015年8月26日 		view
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
 * @Date	 2015年8月26日		下午2:36:17
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
	 * dataInit:(蓝牙传输方法   以及连接方法)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param data    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public  static   boolean dataInit(String data ) {
		try {
			
			if(bluetoothDevice==null){
				System.out.println("BlueToothList.address    "+BlueToothList.address);
				bluetoothDevice = bluetoothAdapter.getRemoteDevice(BlueToothList.address);
			}
			
			if(bluetoothSocket==null){
				//通过UUID连接蓝牙  
				bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(Changliang.MY_UUID);
				//蓝牙的连接
				bluetoothSocket.connect();
				
				os = bluetoothSocket.getOutputStream();
			}
			if(os!=null){
				os.write(data.getBytes("utf-8"));
//				Toast.makeText(getApplicationContext(), "信息发送成功", 0).show();
			}else{
				
			}
			return true;
		} catch (IOException e) {
//			Toast.makeText(getApplicationContext(), "信息发送失败", 0).show();
			
			e.printStackTrace();
			return false;
			
		}
		
	}
	

}

