package com.aalj35.aa11.Receiver;

import com.aalj35.aa11.BlueToothList;
import com.aalj35.aa11.mystart.Changliang;
import com.aalj35.aa11.utils.SendData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
    	System.out.println("���ӹ㲥");
//    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       SendData.dataInit(Changliang.SHAKE);
    }
}