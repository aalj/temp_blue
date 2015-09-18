/**
 * BlueToothList.java
 * com.aalj35.aa11
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��23�� 		view
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
 * @Date	 2015��8��23��		����8:44:09
 *
 * @see 	 

 */
public class BlueToothList extends Activity {
	//�����б�������������
	private List<String> bluetoothList = null;
	//����ListView
	private ListView bluetoothListView = null;
	//����������
	private BluetoothAdapter bluetoothAdapter = null;
	//���������豸��
	private BluetoothDevice blueToothDevice = null;
	//����ListView������
	private ArrayAdapter<String> arrayAdapter = null;
	public static BluetoothSocket bluetoothSocket = null;
	
	//����ȫ�ֱ���  ����ȫ��ʹ��
	public static String  address = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetoothlist);
		//�������ݳ�ʼ������
		dataInit();
		//����ҳ���ʼ������
		viewInit();
		
	}
	
	
	
	
	
	/**
	 * 
	 * onclick:(��ť����¼�������)
	 *
	 * @param  @param v    �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	public void onclick(View v){
		if (bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
		
	}
	/**
	 * 
	 * dataInit:(listView���ݵĳ�ʼ��)
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
	private void dataInit() {
		//��ʼ������������ ͬʱ�õ��ֻ�Ӳ��������������
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
	 * viewInit:(ҳ���ʼ��    ����listView��ʼ��)
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
	private void viewInit() {
		
		//��ʼ��ListView
		bluetoothListView = (ListView) findViewById(R.id.listView1);
		//����listView����ļ�����
		bluetoothListView.setOnItemClickListener(new MyOnItemClickListener() );
		
		//����listView����
		arrayAdapter = new ArrayAdapter<String>(BlueToothList.this, android.R.layout.simple_list_item_1,bluetoothList);
		bluetoothListView.setAdapter(arrayAdapter);
		//������ʱ����ʱ ע��㲥
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		BlueToothList.this.registerReceiver(receiver, filter);
		
		
		//��������ɨ���ʱ��ע��㲥
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		BlueToothList.this.registerReceiver(receiver, filter);
		
		
		
	}
	/**
	 * 
	 * ClassName:MyOnItemClickListener
	 * Function: listView��������¼�
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  BlueToothList
	 * @since    Ver 1.1
	 * @Date	 2015	2015��8��24��		����9:57:22
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
	                //��������������������豸��ȡ������
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
	
	
	
	//д�㲥������
	private final BroadcastReceiver receiver = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent) {
			System.out.println("jh;kandkjfasndf;kj");
			try {
				String action = intent.getAction();
				if(BluetoothDevice.ACTION_FOUND.equals(action)){
					BluetoothDevice device = intent.getParcelableExtra(blueToothDevice.EXTRA_DEVICE);
					if(device.getBondState()!=BluetoothDevice.BOND_BONDED){
						//���ӻ�δ��Եĵ��豸
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


	