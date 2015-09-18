/**
 * MainActivity.java
 * test
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015年8月24日 		AYI
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
 * @Function: 首页
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015年8月24日 下午6:35:17
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

	// 寻找手环按钮的点击事件
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
