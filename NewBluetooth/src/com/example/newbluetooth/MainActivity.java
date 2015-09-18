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
				Toast.makeText(this, "�����ѿ���", Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * ������ 
	 */
	public void intentBlueTooth(View v) {
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(intent, RESULT_FIRST_USER); // �����û�����Ļ�Ľ����
		// ��Ҫ����
		// BluetoothAdapter.enable();
		// ��������ֱ�Ӵ�����
		// BluetoothAdapter.disable();�ɹر�����
		// ���Ϸ�������չʾ�Ի���ֻ������Ϣ�ؿ��������豸
	}
	
	public void closeBlueTooth(View v){
		adapter.disable();
	}

	/**
	 * ���뵽���������豸����
	 * @param v
	 */
	public void searchBluetoothDevice(View v) {
		// �����������豸ͨ��֮ǰ��Ҫ������Χ�������豸����Ҫ�Լ����ֻ������������豸����������Ҫ�����������ý��棬ѡ�пɼ���͸�ѡ��
		// ����ֻ����Ѿ���ĳЩ�����豸�󶨣�����ʹ��BluetoothAdapter.getBondedDevices��������Ѿ��󶨵������豸�б�
		// ������Χ�������豸ʹ��BluetoothAdapter.startDiscovery�������������������豸ͨ���㲥���أ������Ҫע��㲥
		// ��������������ѵ��������豸

		Intent intent = new Intent(this, SearchBlueDevice.class);
		startActivity(intent);
	}

	/**
	 * ����ͨѶ����
	 * @param v
	 */
	public void BluetoothSocket(View v) {
		Intent intent = new Intent(this, BlueSocketActivity.class);
		startActivity(intent);
	}

}
