package com.example.newbluetooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cn.newtest.OperateActivity;

public class BlueSocketActivity extends Activity implements OnItemClickListener{

    private ListView lvDevices;
    private BluetoothAdapter bluetoothAdapter;
    private List<String> bluetoothDevicesList = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    public final static UUID MY_UUID = UUID.fromString("2d266186-01fb-47c2-8d9f-10b8ec891363");
    public final static String NAME = "NewBluetooth_Socket";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_blue_socket);
        
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvDevices = (ListView) findViewById(R.id.listDevices);
        //�õ��Ѱ󶨵������豸�����ƺ͵�ַ
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        //�����Ѱ󶨵������豸��ӽ�list��
        if (pairedDevices.size() > 0){
            for (BluetoothDevice device : pairedDevices){
                bluetoothDevicesList.add(device.getName() + ":" + device.getAddress() + "\n");
            }
        }
        
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, 
                android.R.id.text1,
                bluetoothDevicesList
                );

        lvDevices.setAdapter(arrayAdapter);
        lvDevices.setOnItemClickListener(this);
        
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);//ɨ�赽��һԶ�������豸ʱ,�ᷢ�ʹ˹㲥
        BlueSocketActivity.this.registerReceiver(receiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//ȡ�������㲥
        BlueSocketActivity.this.registerReceiver(receiver, filter);

    }

    public void onClick_Search(View view){
        setProgressBarIndeterminateVisibility(true);
        setTitle("����ɨ��...");

        if (bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }
    
    public List<String> getDevicesList(){
    	return bluetoothDevicesList;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id){
        String s = arrayAdapter.getItem(position);
        //���Ҫ���ӵ������豸�ĵ�ַ
        String address = s.substring(s.indexOf(":") + 1).trim();

        try{
        	
            if (bluetoothAdapter.isDiscovering()){
                //��������������������豸��ȡ������
                this.bluetoothAdapter.cancelDiscovery();
            }

            Intent intent=new Intent(BlueSocketActivity.this,OperateActivity.class);
            intent.putExtra("ADDRESS", address);
            startActivity(intent);
           
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)){

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getBondState() != BluetoothDevice.BOND_BONDED){
                    bluetoothDevicesList.add(device.getName() + ":" + device.getAddress() + "\n");
                    arrayAdapter.notifyDataSetChanged();
                }
            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                setProgressBarIndeterminateVisibility(false);
                setTitle("���������豸");
            }
        }
    };

}
