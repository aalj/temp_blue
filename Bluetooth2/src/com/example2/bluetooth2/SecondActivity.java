package com.example2.bluetooth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
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

import com.exmaple2.bluetooth2.R;

/**
 * 
 * ClassName:SecondActivity
 * 
 * @Function: 蓝牙搜索 显示以匹配的设备
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015年8月25日 上午9:21:13
 *
 * @see
 */
public class SecondActivity extends Activity {

	// (本地)蓝牙适配器
	public static BluetoothAdapter bt_adapter = null;
	// 存以配对的设备名称及地址
	private List<String> list_device = new ArrayList<String>();
	// 数组型适配器
	private ArrayAdapter<String> arrayadapter = null;

	// 使用唯一的UUID码
	public final static UUID MY_UUID = UUID
			.fromString("2d266186-01fb-47c2-8d9f-10b8ec891363");
	// 定义位移使用的字符串标识
	public final static String NAME = "NewBluetooth_Socket";

	// 远程设备
	public static BluetoothDevice bt_device;
	// 监听服务端口
	public static BluetoothServerSocket bluetoothServerSocket;
	//
	public static BluetoothSocket bluetoothSocket;
	// 客户端
	public static BluetoothSocket client_Socket;
	// 配对的地址
	public static String address = "";

	// 输出流
	// public static OutputStream os;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		dataInit();
		viewInit();

	}

	// 数据源
	public void dataInit() {
		// (本地)蓝牙适配器
		bt_adapter = BluetoothAdapter.getDefaultAdapter();

		// 得到已连接设备
		Set<BluetoothDevice> set_device = bt_adapter.getBondedDevices();
		if (set_device.size() > 0) {
			// 得到每一个设备的名称和地址
			for (BluetoothDevice btdevice : set_device) {
				list_device.add(btdevice.getName() + "\n"
						+ btdevice.getAddress());
			}
		}

	}

	public void viewInit() {
		// 初始化链表
		ListView lv_device = (ListView) findViewById(R.id.listView1);

		// 数组型适配器
		arrayadapter = new ArrayAdapter<>(SecondActivity.this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				list_device);

		// 装载适配器
		lv_device.setAdapter(arrayadapter);
		lv_device.setOnItemClickListener(new MyOnItemClick());

		// 注册广播
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		SecondActivity.this.registerReceiver(receiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		SecondActivity.this.registerReceiver(receiver, filter);

		// filter = new
		// IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		// SecondActivity.this.registerReceiver(receiver, filter);
	}

	// 搜索的点击事件
	public void onclick(View v) {
		// 如果正在搜索，点击及停止搜索
		// 否则开启搜索功能
		if (bt_adapter.isDiscovering()) {
			bt_adapter.cancelDiscovery();
		} else {
			bt_adapter.startDiscovery();
		}
	}

	// 链表点击事件
	public class MyOnItemClick implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// LayoutInflater inflater = getLayoutInflater();
			// view = inflater.inflate(android.R.layout.simple_list_item_1,
			// null);
			// TextView text = (TextView) view.findViewById(android.R.id.text1);
			// text.setBackgroundResource(R.drawable.ic_launcher);

			String name = arrayadapter.getItem(position);
			try {
				address = name.substring(name.indexOf("\n") + 1).trim();
				new connect_theard().start();
				if (bt_adapter.isDiscovering()) {
					bt_adapter.cancelDiscovery();
				}
				if (client_Socket.isConnected()) {
					Toast.makeText(SecondActivity.this, "设备已连接", 500).show();
					Intent intent = new Intent();
					intent.setClass(SecondActivity.this, ThirdActivity.class);
					intent.putExtra("Address", address);
					SecondActivity.this.startActivity(intent);
				} else {
					Toast.makeText(SecondActivity.this, "设备未连接", 500).show();
				}

			} catch (Exception e) {
				Toast.makeText(SecondActivity.this, e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

		}
	}

	// 广播
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			// TODO Auto-generated method stub
			// try {
			String action = intent.getAction();

			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// 在Intent中获取设备对象
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					String message = device.getName() + "\n"
							+ device.getAddress();
					if (!list_device.contains(message)) {
						list_device.add(message);
						arrayadapter.notifyDataSetChanged();
					}
				}
			}
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}
	};

	private class connect_theard extends Thread {

		@Override
		public void run() {

			// TODO Auto-generated method stub

			try {
				// 如果远程设备为null
				if (bt_device == null) {
					bt_device = bt_adapter.getRemoteDevice(address);
					System.out.println(address);

				}
				if (client_Socket == null) {
					// 通过UUID连接蓝牙
					client_Socket = bt_device
							.createRfcommSocketToServiceRecord(SecondActivity.MY_UUID);
					System.out.println(SecondActivity.MY_UUID);

					// 蓝牙的连接
					client_Socket.connect();
					// os = client_Socket.getOutputStream();
				}

				// os = client_Socket.getOutputStream();
				// if (os != null) {
				// os.write(data.getBytes("utf-8"));
				// Toast.makeText(SecondActivity.this, "信息发送成功", 0).show();
			} catch (IOException e) {
				// Toast.makeText(SecondActivity.this, "信息发送失败", 0).show();
			}

		}

		// 连接
		// public void connect() {
		// try {
		// // 如果远程设备为null
		// if (bt_device == null) {
		// bt_device = bt_adapter.getRemoteDevice(address);
		// }
		// if (client_Socket == null) {
		// // 通过UUID连接蓝牙
		// client_Socket = bt_device
		// .createRfcommSocketToServiceRecord(SecondActivity.MY_UUID);
		// // 蓝牙的连接
		// client_Socket.connect();
		// // os = client_Socket.getOutputStream();
		// }
		//
		// // os = client_Socket.getOutputStream();
		// // if (os != null) {
		// // os.write(data.getBytes("utf-8"));
		// // Toast.makeText(SecondActivity.this, "信息发送成功", 0).show();
		// } catch (IOException e) {
		// // Toast.makeText(SecondActivity.this, "信息发送失败", 0).show();
		// }
		// }
		//
	}

}
