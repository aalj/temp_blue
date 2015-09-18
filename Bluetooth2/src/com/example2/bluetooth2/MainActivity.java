/**
 * MainActivity.java
 * test
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��24�� 		AYI
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package com.example2.bluetooth2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.exmaple2.bluetooth2.R;

/**
 * ClassName:MainActivity
 * 
 * @Function: ��ҳ
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015��8��24�� ����6:35:17
 *
 * @see
 */
public class MainActivity extends Activity {
	BluetoothAdapter bt_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt_adapter = BluetoothAdapter.getDefaultAdapter();
		bt_adapter.enable();

	}

	// Ѱ���ֻ���ť�ĵ���¼�
	public void onclick(View v) {
		if (bt_adapter.isEnabled()) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SecondActivity.class);
			MainActivity.this.startActivity(intent);
		} else {
			bt_adapter.enable();
		}

	}

}
