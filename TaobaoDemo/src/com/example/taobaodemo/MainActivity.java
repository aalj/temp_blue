package com.example.taobaodemo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//实现 在欢迎界面停止2miao 跳转到第二个页面；	
     Timer timer=new Timer();
     TimerTask task=new TimerTask() {
	
	@Override
	public void run() {
		
		Intent intent=new Intent(MainActivity.this,SecondActivity.class);
		startActivity(intent);
		MainActivity.this.finish();
		
	}
};
//第一个参数是
   timer.schedule(task, 2000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
//imageview的点击事件
	public void mainclick(View v){
		
		startActivity(new Intent(MainActivity.this,SecondActivity.class));
		finish();
	}
}
