package com.cn.newtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent){
		Bundle bundle = intent.getExtras();

		if (bundle != null){
			//����յ��Ķ������ݣ����еĶ������ݶ���Ҫͨ��һ����pdus��key��ȡ
			Object[] objArray = (Object[]) bundle.get("pdus");
			//�����װ�������ݵ�smsmessage��������
			SmsMessage[] messages = new SmsMessage[objArray.length];

			for (int i = 0; i < objArray.length; i++){
				//��ÿ����������ת����smsmessage����
				messages[i] = SmsMessage.createFromPdu((byte[]) objArray[i]);
				String s = "�ֻ��ţ�" + messages[i].getOriginatingAddress() + "\n";
				s += "�������ݣ�" + messages[i].getDisplayMessageBody();
				Toast.makeText(context, s, Toast.LENGTH_LONG).show();
			}
		}
	}


}
