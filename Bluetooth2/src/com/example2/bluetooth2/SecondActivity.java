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
 * @Function: �������� ��ʾ��ƥ����豸
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015��8��25�� ����9:21:13
 *
 * @see
 */
public class SecondActivity extends Activity {

	// (����)����������
	public static BluetoothAdapter bt_adapter = null;
	// ������Ե��豸���Ƽ���ַ
	private List<String> list_device = new ArrayList<String>();
	// ������������
	private ArrayAdapter<String> arrayadapter = null;

	// ʹ��Ψһ��UUID��
	public final static UUID MY_UUID = UUID
			.fromString("2d266186-01fb-47c2-8d9f-10b8ec891363");
	// ����λ��ʹ�õ��ַ�����ʶ
	public final static String NAME = "NewBluetooth_Socket";

	// Զ���豸
	public static BluetoothDevice bt_device;
	// ��������˿�
	public static BluetoothServerSocket bluetoothServerSocket;
	//
	public static BluetoothSocket bluetoothSocket;
	// �ͻ���
	public static BluetoothSocket client_Socket;
	// ��Եĵ�ַ
	public static String address = "";

	// �����
	// public static OutputStream os;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		dataInit();
		viewInit();

	}

	// ����Դ
	public void dataInit() {
		// (����)����������
		bt_adapter = BluetoothAdapter.getDefaultAdapter();

		// �õ��������豸
		Set<BluetoothDevice> set_device = bt_adapter.getBondedDevices();
		if (set_device.size() > 0) {
			// �õ�ÿһ���豸�����ƺ͵�ַ
			for (BluetoothDevice btdevice : set_device) {
				list_device.add(btdevice.getName() + "\n"
						+ btdevice.getAddress());
			}
		}

	}

	public void viewInit() {
		// ��ʼ������
		ListView lv_device = (ListView) findViewById(R.id.listView1);

		// ������������
		arrayadapter = new ArrayAdapter<>(SecondActivity.this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				list_device);

		// װ��������
		lv_device.setAdapter(arrayadapter);
		lv_device.setOnItemClickListener(new MyOnItemClick());

		// ע��㲥
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		SecondActivity.this.registerReceiver(receiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		SecondActivity.this.registerReceiver(receiver, filter);

		// filter = new
		// IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		// SecondActivity.this.registerReceiver(receiver, filter);
	}

	// �����ĵ���¼�
	public void onclick(View v) {
		// ������������������ֹͣ����
		// ��������������
		if (bt_adapter.isDiscovering()) {
			bt_adapter.cancelDiscovery();
		} else {
			bt_adapter.startDiscovery();
		}
	}

	// �������¼�
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
					Toast.makeText(SecondActivity.this, "�豸������", 500).show();
					Intent intent = new Intent();
					intent.setClass(SecondActivity.this, ThirdActivity.class);
					intent.putExtra("Address", address);
					SecondActivity.this.startActivity(intent);
				} else {
					Toast.makeText(SecondActivity.this, "�豸δ����", 500).show();
				}

			} catch (Exception e) {
				Toast.makeText(SecondActivity.this, e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

		}
	}

	// �㲥
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			// TODO Auto-generated method stub
			// try {
			String action = intent.getAction();

			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// ��Intent�л�ȡ�豸����
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
				// ���Զ���豸Ϊnull
				if (bt_device == null) {
					bt_device = bt_adapter.getRemoteDevice(address);
					System.out.println(address);

				}
				if (client_Socket == null) {
					// ͨ��UUID��������
					client_Socket = bt_device
							.createRfcommSocketToServiceRecord(SecondActivity.MY_UUID);
					System.out.println(SecondActivity.MY_UUID);

					// ����������
					client_Socket.connect();
					// os = client_Socket.getOutputStream();
				}

				// os = client_Socket.getOutputStream();
				// if (os != null) {
				// os.write(data.getBytes("utf-8"));
				// Toast.makeText(SecondActivity.this, "��Ϣ���ͳɹ�", 0).show();
			} catch (IOException e) {
				// Toast.makeText(SecondActivity.this, "��Ϣ����ʧ��", 0).show();
			}

		}

		// ����
		// public void connect() {
		// try {
		// // ���Զ���豸Ϊnull
		// if (bt_device == null) {
		// bt_device = bt_adapter.getRemoteDevice(address);
		// }
		// if (client_Socket == null) {
		// // ͨ��UUID��������
		// client_Socket = bt_device
		// .createRfcommSocketToServiceRecord(SecondActivity.MY_UUID);
		// // ����������
		// client_Socket.connect();
		// // os = client_Socket.getOutputStream();
		// }
		//
		// // os = client_Socket.getOutputStream();
		// // if (os != null) {
		// // os.write(data.getBytes("utf-8"));
		// // Toast.makeText(SecondActivity.this, "��Ϣ���ͳɹ�", 0).show();
		// } catch (IOException e) {
		// // Toast.makeText(SecondActivity.this, "��Ϣ����ʧ��", 0).show();
		// }
		// }
		//
	}

}
