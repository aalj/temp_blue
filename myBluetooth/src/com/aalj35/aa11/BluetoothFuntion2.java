/**
 * BluetoothFuntion.java
 * com.aalj35.aa11
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015年8月24日 		view
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
 * Function: 实现蓝牙功能   该方法目前是基于服务存在  暂时停用
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015年8月24日		上午9:43:23
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
	 * onclick:(按钮点击监听器)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param v    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public void onclick(View v){
		
		System.out.println("按钮已经点击");
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
	 * viewInit:(页面控件初始化)
	 * @param      设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private void viewInit() {
		
		  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		text = (TextView) findViewById(R.id.text);
		edit = (EditText) findViewById(R.id.editText1);
		text.setText(getIntent().getStringExtra(Changliang.NAME));
		//启动线程
		new BlueThread().start();
	}
	
	
	/******************************************以下是蓝牙接收的部分      以放置服务里面*************************/
	
	
	/**
	 * 消息机制   用于吧接收到的消息加载到当前的布局里面
	 */
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			String s = (String)msg.obj;
			System.out.println("连接返回");
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
	 * Function: 创建一个线程执行接收连接过程
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  BluetoothFuntion
	 * @since    Ver 1.1
	 * @Date	 2015	2015年8月24日		上午11:32:02
	 *
	 * @see
	 */
	private class BlueThread extends Thread {

		private InputStream is;

		public BlueThread() {

			try {
				// 此方法用来创建BluetoothServerSocket的对象
				// 第一个参数表示蓝牙服务的名称，可以是任意字符串
				// 第二个参数是UUID
				bluetoothServerSocket = bluetoothAdapter
						.listenUsingRfcommWithServiceRecord(
								Changliang.NAME,
								Changliang.MY_UUID);
			} catch (IOException e) {

			}
		}

		public void run() {

			try {
				// 通过BluetoothServerSocket.accept();方法收到客户端的请求后，
				// accept()方法会返回一个BluetoothSocket对象，可以通过该对象获得读写数据的InputStream和OutputStream对象
				// 等待接受蓝牙客户端的请求
				bluetoothSocket = bluetoothServerSocket.accept();

				
				// 通过循环不断接收客户端发过来的数据。如果客户端暂时没发数据，则read方法处于阻塞状态
				while (!exit) {
					is = bluetoothSocket.getInputStream();
					os = bluetoothSocket.getOutputStream();
					byte[] buffer = new byte[2048];
					int count = is.read(buffer);

					System.out.println(count + "---------------");
					String recvData = new String(buffer, 0, count, "utf-8");
					System.out.println("查看是否");
//					System.out.println("----" + recvData );
					//向消息机制里面发送数据
					//message  发送消息的承载体 
					Message msg = Message.obtain();
					//发送消息的类型    obj表示 Object
					msg.obj = recvData;
					//发送消息
					handler.sendMessage(msg);
				}

			} catch (Exception e) {

			}
		}
	}
	/******************************************以上是蓝牙接收的部分      以放置服务里面*************************/

	

}

