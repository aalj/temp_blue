package com.example.newbluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BluetoothAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = BluetoothAdapter.getDefaultAdapter();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent
			data) {
		if (RESULT_FIRST_USER == requestCode) {
			if(RESULT_OK == resultCode){
				Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 打开蓝牙 
	 */
	public void intentBlueTooth(View v) {
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(intent, RESULT_FIRST_USER); // 启动用户定义的活动的结果。
		// 若要调用
		// BluetoothAdapter.enable();
		// 方法可以直接打开蓝牙
		// BluetoothAdapter.disable();可关闭蓝牙
		// 以上方法不会展示对话框，只会无声息地开启蓝牙设备
	}
	
	public void closeBlueTooth(View v){
		adapter.disable();
	}

	/**
	 * 进入到搜索蓝牙设备界面
	 * @param v
	 */
	public void searchBluetoothDevice(View v) {
		// 与其他蓝牙设备通信之前需要搜索周围的蓝牙设备，想要自己的手机被其他蓝牙设备搜索到，需要进入蓝牙设置界面，选中可监测型复选框
		// 如果手机中已经和某些蓝牙设备绑定，可以使用BluetoothAdapter.getBondedDevices方法获得已经绑定的蓝牙设备列表。
		// 搜索周围的蓝牙设备使用BluetoothAdapter.startDiscovery方法，搜索到的蓝牙设备通过广播返回，因此需要注册广播
		// 接收器来获得已搜到的蓝牙设备

		Intent intent = new Intent(this, SearchBlueDevice.class);
		startActivity(intent);
	}

	/**
	 * 蓝牙通讯界面
	 * @param v
	 */
	public void BluetoothSocket(View v) {
		Intent intent = new Intent(this, BlueSocketActivity.class);
		startActivity(intent);
	}

}
