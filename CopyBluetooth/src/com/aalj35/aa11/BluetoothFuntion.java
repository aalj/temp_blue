/**
 * BluetoothFuntion.java
 * com.aalj35.aa11
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��24�� 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.aalj35.aa11.Receiver.InCallReceiver;
import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.utils.SendData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ClassName:BluetoothFuntion
 * Function: ʵ����������
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015��8��24��		����9:43:23
 *
 * @see 	 

 */
public class BluetoothFuntion extends Activity {
	BluetoothAdapter bluetoothAdapter = null;
	BluetoothDevice  bluetoothDevice = null;
	BluetoothSocket bluetoothSocket= null;
	public static BluetoothServerSocket bluetoothServerSocket = null;
	private OutputStream os;
	boolean exit= false;
	/**
	 * ������Ҫ��
	 */
	Calendar cal=Calendar.getInstance();
	Vibrator vibrator = null;
	/**
	 * ���������
	 */
//	 private AlarmManager alarmManager=null;
	/**
	 * ������ʾ����
	 */
	private TextView text  = null;
	/**
	 * ���ڴ���ģ�������
	 */
	private EditText edit  = null;
	/**
	 * ��ʾ�˶���
	 */
	TextView address= null;
	/**
	 * ��ȡ��ص����İ�ť
	 */
	Button battery = null;
	/**
	 * ��ȡ�ֻ�����İ�ť
	 */
	Button alarm = null;
	/**
	 * ��ʶ�������ѵĿ���
	 */
	
	
	boolean call = false;
	/**
	 * �����㲥������
	 */
	private InCallReceiver inCallReceiver = null;
	public static int currentIntent=0;
	int time = 0;
	private Timer timer = null;
    private TimerTask task = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funtion);
		viewInit();
	}
	
	
	/**
	 * 
	 * viewInit:(ҳ��ؼ���ʼ��)
	 * @param      �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	
	private void viewInit() {
		//�绰�Ĺ㲥�����߶���
		   inCallReceiver=new InCallReceiver();
		//��̬ע������㲥
//		IntentFilter filter = new IntentFilter();
//		BluetoothFuntion.this.registerReceiver(new AlarmReceiver(), filter);
		address = (TextView) findViewById(R.id.address);
		//�������ӵİ�ť
		alarm = (Button) findViewById(R.id.alarm);
		//��ȡ�����Ĺ�����
//		alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		//��ȡ��ذ�ť
		battery = (Button) findViewById(R.id.Battery);
		//�ֻ��𶯳�ʼ��
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		//��������������
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		text = (TextView) findViewById(R.id.text);
		edit = (EditText) findViewById(R.id.editText1);
		text.setText(getIntent().getStringExtra(Changliang.NAME));
		//�����߳�
		new BlueThread().start();
	}
	/**
	 * 
	 * onclick:(��ť���������)
	 * TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)
	 * TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @param  @param v    �趨�ļ�
	 * @return void    DOM����
	 * @throws 
	 * @since  CodingExample��Ver 1.1
	 */
	public void onclick(View v){
		
		System.out.println("��ť�Ѿ����");
		switch (v.getId()) {
		case R.id.send:
			SendData.dataInit(Changliang.SEND );
			break;
		case R.id.call://��������
			Button button = (Button) findViewById(R.id.call);
			IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
			//ͬһ����ť��ʵ����������
			//�����һ���Ǵ򿪵绰����
			//����ڶ����ǹرյ绰����
			if(!call){
				BluetoothFuntion.this.registerReceiver(inCallReceiver , intentFilter);
				button.setText("�Ѵ�");
				call= true;
			}else{
				
				BluetoothFuntion.this.unregisterReceiver(inCallReceiver);
				button.setText("�ѹر�");
				call= false;
			}
			break;
		
			
		case R.id.shake://��
			SendData.dataInit(Changliang.SHAKE);
			break;
		case R.id.cancalshake://ȡ����
			vibrator.cancel();
			SendData.dataInit(Changliang.CANCALSHAKE);
			break;
		case R.id.lock://����
			SendData.dataInit(Changliang.LOCK);
			break;
		case R.id.unlock://����
			SendData.dataInit(Changliang.UNLOCK);
			break;
		case R.id.Battery://
			
			SendData.dataInit(Changliang.BATTERYNUM);
			break;
		case R.id.alarm:
			popdia();
			
//			SendData.dataInit(Changliang.ALARM);
			break;

		default:
			break;
		}
		
	}
	/**
	 * 
	 * popdia:(���������ü�ʱ)
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
	public void popdia(){
		AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothFuntion.this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("���ü�ʱ��ʱ��");
		
		LayoutInflater inflater = LayoutInflater.from(BluetoothFuntion.this);
		View view = inflater.inflate(R.layout.text, null);
		final TextView num = (TextView) view.findViewById(R.id.jitime);
		builder.setView(view);
		
		builder.setCancelable(false);
		
		builder.setPositiveButton("�ύ", new DialogInterface. OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String temp = num.getText().toString().trim();
				time= Integer.parseInt(temp);
				System.out.println(time);
				new JiTime(time).start();
			}
		});
		
		
		
		builder.create().show();
		
	}
	/**
	 * 
	 * ClassName:JiTime
	 * Function: ��ʱ���߳�
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  BluetoothFuntion
	 * @since    Ver 1.1
	 * @Date	 2015	2015��8��31��		����3:43:59
	 *
	 * @see
	 */
	public class JiTime extends Thread {
		int num = 0;
		public JiTime(int num) {
			this.num = num;
			// TODO Auto-generated constructor stub
		}
		@Override
		public void run() {
			try {
				System.out.println("��ʱ��ʼ");
				Thread.sleep(num);
				Message msg = Message.obtain();
				//������Ϣ������    obj��ʾ Object
				msg.obj = "��ʱ";
				//������Ϣ
				handler.sendMessage(msg);
				
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	/******************************************�������������յĲ���      �Է��÷�������*************************/
	
	
	/**
	 * ��Ϣ����   ���ڰɽ��յ�����Ϣ���ص���ǰ�Ĳ�������
	 */
	private  Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			long[] pattern = { 100, 400, 100, 400 };
			String s = (String)msg.obj;
			System.out.println("SSSSSSSSSSSSS"+s);
			//��ȡ�������ж�
			if(s.substring(s.indexOf(":")+1).trim().equals("1")){
				battery.setText("����ʣ�ࣺ"+s.substring(0,s.indexOf(":")).trim());
				System.out.println("�����Ƿ񴫵�");
				new DianLiangThread().start();
			}
			if(s.substring(s.indexOf(":")+1).trim().equals("2")){
				address.setText("������˶�����"+s.substring(0,s.indexOf(":")).trim());
			}
			/**
			 * ���û�ȡ���Ӱ�ť��
			 */
			
			if(s.equals("��ȡ����")){
				
				battery.setText("��ȡ����");
			}
			if(s.equals("��ʱ")){
				
				SendData.dataInit(Changliang.SHAKE);
			}
			switch (s) {
			case Changliang.SEND://Ŀǰ����һ�����Ӳ���
				System.out.println("�������ڷ������棬���ڽ�������");
				SendData.dataInit(edit.getText().toString()+":2");
				break;
			
				
			case Changliang.SHAKE://��
				 
				vibrator.vibrate(pattern, 2); 
				Toast.makeText(getApplicationContext(), s,0).show();
				break;
			case Changliang.CANCALSHAKE://ȡ����
				vibrator.cancel();
				Toast.makeText(getApplicationContext(), s,0).show();
				break;
			case Changliang.LOCK://�����ֻ�
				myLock();
				Toast.makeText(getApplicationContext(), s,0).show();
				break;
			case Changliang.UNLOCK://�����ֻ�
				
				Toast.makeText(getApplicationContext(), s,0).show();
				break;
			case Changliang.ALARM://�ֻ����� ������������������ȡ��
				
				Toast.makeText(getApplicationContext(), s,0).show();
				break;
			case Changliang.BATTERYNUM://�����ķ���
				getEnergy(battery);
				Toast.makeText(getApplicationContext(), s,0).show();
				break;

			default:
				break;
			}
			
		};
	};
	
	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;
	/**
	 * 
	 * myLock:(�����ֻ��ķ���)
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
	public void myLock() {
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(getApplicationContext(), com.aalj35.aa11.Receiver.LockReceiver.class);
		// �ж��Ƿ���Ȩ��
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow();
		} else {
			activeManager();
		}
	}

	private void activeManager() {
		// �����豸��������ȡȨ��
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "��˵�е�һ������");
		startActivity(intent);
		
	}
	
	
	/**
	 * 
	 * ClassName:BlueThread
	 * Function: ����һ���߳�ִ�н������ӹ���
	 * Reason:	 TODO ADD REASON
	 *
	 * @author   view
	 * @version  BluetoothFuntion
	 * @since    Ver 1.1
	 * @Date	 2015	2015��8��24��		����11:32:02
	 *
	 * @see
	 */
	private class BlueThread extends Thread {

		private InputStream is;

		public BlueThread() {

			try {
				// �˷�����������BluetoothServerSocket�Ķ���
				// ��һ��������ʾ������������ƣ������������ַ���
				// �ڶ���������UUID
				bluetoothServerSocket = bluetoothAdapter
						.listenUsingRfcommWithServiceRecord(
								Changliang.NAME,
								Changliang.MY_UUID);
			} catch (IOException e) {

			}
		}

		public void run() {

			try {
				// ͨ��BluetoothServerSocket.accept();�����յ��ͻ��˵������
				// accept()�����᷵��һ��BluetoothSocket���󣬿���ͨ���ö����ö�д���ݵ�InputStream��OutputStream����
				// �ȴ����������ͻ��˵�����
				bluetoothSocket = bluetoothServerSocket.accept();

				
				// ͨ��ѭ�����Ͻ��տͻ��˷����������ݡ�����ͻ�����ʱû�����ݣ���read������������״̬
				while (!exit) {
					is = bluetoothSocket.getInputStream();
					os = bluetoothSocket.getOutputStream();
					byte[] buffer = new byte[2048];
					int count = is.read(buffer);

					String recvData = new String(buffer, 0, count, "utf-8");
					//����Ϣ�������淢������
					//message  ������Ϣ�ĳ����� 
					Message msg = Message.obtain();
					//������Ϣ������    obj��ʾ Object
					msg.obj = recvData;
					//������Ϣ
					handler.sendMessage(msg);
				}

			} catch (Exception e) {

			}
		}
	}
	/******************************************�������������յĲ���      �Է��÷�������*************************/

	
	
	
	
	//*************************************��ȡ�ֻ�����*****************
	/**
	 * ��ȡ�ֻ�����
	 */

	public void getEnergy(View v) {

			IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			
	}
	
	//**********************************ע���صĹ㲥*********************
//	private class BatteryReceiver extends BroadcastReceiver {
	private BroadcastReceiver receiver1 = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("���������㲥");
			String BatteryStatus = ""; // ���״̬
			String BatteryTemp = ""; // ���ʹ�����

			int current = intent.getExtras().getInt("level");// ��õ�ǰ����
			int total = intent.getExtras().getInt("scale");// ����ܵ���
			System.out.println("��õ�ǰ����"+ current);
			SendData.dataInit(current+":1");
			BluetoothFuntion.this.unregisterReceiver(receiver1);
			int temperature = intent.getIntExtra("temperature", 0); // �¶ȵĵ�λ��10��
			String technology = intent.getStringExtra("technology"); // ��ؼ�����
			int percent = current * 100 / total;
			String phoneData = ("��ؼ��� : " + technology + BatteryStatus + BatteryTemp + "���ڵĵ�����"
					+ percent + "% �¶�" + temperature);
			System.out.println("phoneData    "+phoneData);
		}
	};
	
	
	
	
	
	

	/*************************�ı��ص���ʾ****/
	private class DianLiangThread extends Thread{
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				
				Message msg = Message.obtain();
				//������Ϣ������    obj��ʾ Object
				msg.obj = "��ȡ����";
				//������Ϣ
				handler.sendMessage(msg);
				
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				
			}
			
			super.run();
			
		}
		
	}
	

	
	
	
	
}
