package com.example.blu;


import com.example.gifview.GifView;
import com.zhilian.blu.shouhuan.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	//
	BluetoothAdapter bt_adapter;
	LinearLayout layout;
	ToggleButton toggleButton;
	ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        bt_adapter = BluetoothAdapter.getDefaultAdapter();
		bt_adapter.enable();
        layout=(LinearLayout)findViewById(R.id.layoutS);
        //按钮初始化
        toggleButton=(ToggleButton)findViewById(R.id.togg);
		imageView=(ImageView)findViewById(R.id.tim);
		//获得视图对象
				GifView gifView = new GifView(this);
				layout.addView(gifView);
//				按钮监听事件
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
//			   判断按钮是否开启状态  是就跳转
				if (isChecked) {
					if (bt_adapter.isEnabled()) {
						Intent intent=new Intent();
					    intent.setClass(MainActivity.this, TwoMainActivity.class);
					    startActivity(intent);
					    finish();
					} else {
						bt_adapter.enable();
					}
			    	
				}
//				按钮状态变化
				toggleButton.setChecked(isChecked);
//				按钮换背景
				imageView.setImageResource(isChecked?R.drawable.on:R.drawable.offf);
				
			}
		});
       
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
