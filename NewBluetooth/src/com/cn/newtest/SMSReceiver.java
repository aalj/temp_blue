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
			//获得收到的短信数据，所有的短信数据都需要通过一个叫pdus的key获取
			Object[] objArray = (Object[]) bundle.get("pdus");
			//定义封装短信内容的smsmessage对象数组
			SmsMessage[] messages = new SmsMessage[objArray.length];

			for (int i = 0; i < objArray.length; i++){
				//将每条短信数据转换成smsmessage对象
				messages[i] = SmsMessage.createFromPdu((byte[]) objArray[i]);
				String s = "手机号：" + messages[i].getOriginatingAddress() + "\n";
				s += "短信内容：" + messages[i].getDisplayMessageBody();
				Toast.makeText(context, s, Toast.LENGTH_LONG).show();
			}
		}
	}


}
