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
	 * Function: 打开蓝牙并进行跳转到蓝牙搜索页面
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  
	 * @since    Ver 1.1
	 * @Date	 2015	2015年8月23日		下午8:45:36
	 *
	 * @see
	 */
	public class OpenBluetooth extends Activity {
	
		BluetoothSocket bluetoothSocket = null;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			//开启蓝牙接收的服务
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
			Toast.makeText(OpenBluetooth.this, "已为你自动打开蓝牙", 0).show();
			
		}else{
			Toast.makeText(OpenBluetooth.this, "该手机没有蓝牙或蓝牙已经打开", 0).show();
		}
		
//		//页面跳转  跳转到蓝牙搜索界面
//		Intent intent = new Intent(OpenBluetooth.this,BlueToothList.class);
//		startActivity(intent);
		
	}








	/**
	 * 
	 * openBlueTooth:(打开蓝牙    )
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
