/**
 * BlueToothList.java
 * com.aalj35.aa11
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015年8月23日 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.aalj35.aa11.utils.SendData;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * ClassName:BlueToothList
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015年8月23日		下午8:44:09
 *
 * @see 	 

 */
public class BlueToothList extends Activity {
	//蓝牙列表的链表的声明
	private List<String> bluetoothList = null;
	//声明ListView
	private ListView bluetoothListView = null;
	//蓝牙适配器
	private BluetoothAdapter bluetoothAdapter = null;
	//声明蓝牙设备类
	private BluetoothDevice blueToothDevice = null;
	//声明ListView适配器
	private ArrayAdapter<String> arrayAdapter = null;
	public static BluetoothSocket bluetoothSocket = null;
	
	//设置全局变量  能在全局使用
	public static String  address = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetoothlist);
		//加载数据初始化方法
		dataInit();
		//加载页面初始化方法
		viewInit();
		
	}
	
	
	
	
	
	/**
	 * 
	 * onclick:(按钮点击事件监听器)
	 *
	 * @param  @param v    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public void onclick(View v){
		if (bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
		
	}
	/**
	 * 
	 * dataInit:(listView数据的初始化)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param      设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private void dataInit() {
		//初始化蓝牙适配器 同时得到手机硬件的蓝牙适配器
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		//
		bluetoothList = new ArrayList<String>();
		Set<BluetoothDevice> localSet =bluetoothAdapter.getBondedDevices();
	    if (localSet.size() > 0){
	    	for (BluetoothDevice bluetoothDevice : localSet) {
				bluetoothList.add(bluetoothDevice.getName()+":"+bluetoothDevice.getAddress());
			}
	      
	  }
		
		
	}

	/**
	 * 
	 * viewInit:(页面初始化    包括listView初始化)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param      设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private void viewInit() {
		
		//初始化ListView
		bluetoothListView = (ListView) findViewById(R.id.listView1);
		//设置listView点击的监听器
		bluetoothListView.setOnItemClickListener(new MyOnItemClickListener() );
		
		//设置listView数据
		arrayAdapter = new ArrayAdapter<String>(BlueToothList.this, android.R.layout.simple_list_item_1,bluetoothList);
		bluetoothListView.setAdapter(arrayAdapter);
		//蓝牙来时搜索时 注册广播
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		BlueToothList.this.registerReceiver(receiver, filter);
		
		
		//蓝牙结束扫描的时候注册广播
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		BlueToothList.this.registerReceiver(receiver, filter);
		
		
		
	}
	/**
	 * 
	 * ClassName:MyOnItemClickListener
	 * Function: listView点击监听事件
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  BlueToothList
	 * @since    Ver 1.1
	 * @Date	 2015	2015年8月24日		上午9:57:22
	 *
	 * @see
	 */
	public class MyOnItemClickListener implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String name = arrayAdapter.getItem(position);
			address = name.substring(name.indexOf(":")+1).trim();
			
			try{
				if (bluetoothAdapter.isDiscovering()){
	                //如果这是正在搜索蓝牙设备，取消搜索
					bluetoothAdapter.cancelDiscovery();
	            }	
//				SendData.bluetoothDevice = null;
//				SendData.bluetoothSocket = null;
//				if(SendData.dataInit("")){
					Intent intent = new Intent(BlueToothList.this,BluetoothFuntion.class) ;
					
					startActivity(intent);
					
//				}
				
				
				
		
		}catch (Exception e){
            Toast.makeText(BlueToothList.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
			
		}
		
	}
	
	
	
	//写广播接收者
	private final BroadcastReceiver receiver = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent) {
			System.out.println("jh;kandkjfasndf;kj");
			try {
				String action = intent.getAction();
				if(BluetoothDevice.ACTION_FOUND.equals(action)){
					BluetoothDevice device = intent.getParcelableExtra(blueToothDevice.EXTRA_DEVICE);
					if(device.getBondState()!=BluetoothDevice.BOND_BONDED){
						//添加还未配对的的设备
						bluetoothList.add(device.getName()+":"+device.getAddress());
						arrayAdapter.notifyDataSetChanged();
					}
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		};
	};


}


	
