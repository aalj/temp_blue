package com.aalj35.aa11;

import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.services.BluetoothAccpte;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
	/**
	 * 
	 * ClassName:OpenBluetooth
	 * Function: ��������������ת����������ҳ��
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  
	 * @since    Ver 1.1
	 * @Date	 2015	2015��8��23��		����8:45:36
	 *
	 * @see
	 */
	public class OpenBluetooth extends Activity {
	
		BluetoothSocket bluetoothSocket = null;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			//�����������յķ���
			Intent ser = new Intent(getApplicationContext(),BluetoothAccpte.class );
			startService(ser);
			
			
		}
	
	
	
	
	
	
	
	
	

	public void onclick(View v) {
		BluetoothAdapter  adapter = BluetoothAdapter.getDefaultAdapter();
		
		if(adapter!=null){
			Intent intent = new Intent();
			
//			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			adapter.enable();
			intent.setClass(OpenBluetooth.this,BlueToothList.class );
			
			startActivity(intent);
			Toast.makeText(OpenBluetooth.this, "��Ϊ���Զ�������", 0).show();
			
		}else{
			Toast.makeText(OpenBluetooth.this, "���ֻ�û�������������Ѿ���", 0).show();
		}
		
//		//ҳ����ת  ��ת��������������
//		Intent intent = new Intent(OpenBluetooth.this,BlueToothList.class);
//		startActivity(intent);
		
	}








	/**
	 * 
	 * openBlueTooth:(������    )
	 * TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)
	 * TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param      �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	private void openBlueTooth() {
		
		
		
	}








	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
