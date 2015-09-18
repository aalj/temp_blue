package com.ljq.activity;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btn = null;
	private AlarmManager alarmManager = null;
	Calendar cal = Calendar.getInstance();
	final int DIALOG_TIME = 0; // ���öԻ���id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				showDialog(DIALOG_TIME);

				// ��ʾʱ��ѡ��Ի���
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_TIME:
			dialog = new TimePickerDialog(

			this, new TimePickerDialog.OnTimeSetListener() {
				public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
					Calendar c = Calendar.getInstance();// ��ȡ���ڶ���
					c.setTimeInMillis(System.currentTimeMillis());
					// ����Calendar����
					c.set(Calendar.HOUR, hourOfDay);
					// ��������Сʱ��
					c.set(Calendar.MINUTE, minute);
					// �������ӵķ�����
					c.set(Calendar.SECOND, 0);
					// �������ӵ�����
					c.set(Calendar.MILLISECOND, 0);
					// �������ӵĺ�����
					Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
					// ����Intent����
					PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
					// ����PendingIntent
//					 alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), pi);
					// ��������
					alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
					// �������ӣ���ǰʱ��ͻ���
					Toast.makeText(MainActivity.this, "�������óɹ�", Toast.LENGTH_LONG).show();
					// ��ʾ�û�
				}
			}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
			break;
		}
		return dialog;
	}
}
