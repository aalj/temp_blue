package com.aalj35.aa11.Receiver;

import com.aalj35.aa11.BlueToothList;
import com.aalj35.aa11.BluetoothFuntion;
import com.aalj35.aa11.mystart.Changliang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class InCallReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, final Intent intent){

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		switch (tm.getCallState()){
		case TelephonyManager.CALL_STATE_RINGING: //响铃 
			String incomingNumber = intent.getStringExtra("incoming_number");
			System.out.println(incomingNumber);
			Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
			BluetoothFuntion.dataInit(Changliang.SHAKE);
			

			break;
		case TelephonyManager.CALL_STATE_OFFHOOK://接听电话
			Toast.makeText(context, "接听", Toast.LENGTH_LONG).show();
			BluetoothFuntion.dataInit(Changliang.CANCALSHAKE);
			Log.d("call_state", "接听");
			break;

		case TelephonyManager.CALL_STATE_IDLE://挂断电话
			BluetoothFuntion.dataInit(Changliang.CANCALSHAKE);
			Toast.makeText(context,"挂断", Toast.LENGTH_LONG).show();

		}

	}
}
