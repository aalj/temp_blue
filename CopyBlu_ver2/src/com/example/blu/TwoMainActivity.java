package com.example.blu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.utils.SendData;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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

public class TwoMainActivity extends Activity {

	
	//����ȫ�ֱ���  ����ȫ��ʹ��
		public static String  address = null;
	// (����)����������
	private BluetoothAdapter bt_adapter = null;
	// ������Ե��豸���Ƽ���ַ
	private List<String> list_device = new ArrayList<String>();;
	// ������������
	private ArrayAdapter<String> arrayadapter = null;

	// ʹ��Ψһ��UUID��
//	public final static UUID MY_UUID = UUID.fromString("2d266186-01fb-47c2-8d9f-10b8ec891363");
	// ����λ��ʹ�õ��ַ�����ʶ
//	public final static String NAME = "NewBluetooth_Socket";

	/*****************************************************/
	List<String> mList = new ArrayList<String>();;
	ListView list;
	ArrayAdapter<String> myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_main);
		
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
				list_device.add(btdevice.getName() + "\n" + btdevice.getAddress());
			}
		}

	}

	public void viewInit() {
		// ��ʼ������
		ListView lv_device = (ListView) findViewById(R.id.device);

		// ������������
		arrayadapter = new ArrayAdapter<String>(TwoMainActivity.this, android.R.layout.simple_list_item_1, list_device);
		// װ��������
		lv_device.setAdapter(arrayadapter);
		lv_device.setOnItemClickListener(new MyOnItemClick());

		// ע��㲥
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		TwoMainActivity.this.registerReceiver(receiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		TwoMainActivity.this.registerReceiver(receiver, filter);

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

	// ��������¼�
	public class MyOnItemClick implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			// TODO Auto-generated method stub

			String name = arrayadapter.getItem(position);
			try {
				 address = name.substring(name.indexOf("\n") + 1).trim();
				
				if (bt_adapter.isDiscovering()) {
					bt_adapter.cancelDiscovery();
				}
				
//				new connThread().start();
				
				Intent intent = new Intent();
				intent.setClass(TwoMainActivity.this, ThreeMainActivity.class);
				intent.putExtra(Changliang.NAME, address);
				TwoMainActivity.this.startActivity(intent);

			} catch (Exception e) {
				Toast.makeText(TwoMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					String message = device.getName() + "\n" + device.getAddress();
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

	
	
	
}