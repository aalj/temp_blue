package com.gem.guessnumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class ChangeModle extends Activity {
	private Button btnEasy,btnNormal,btnHard;
	private ToggleButton tbButton ;
	private Object play = new MusicToggle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		
		//加载音乐
		if(ExitApplication.getInstance().getToggle()){
			((MusicToggle) play).startM(this,0x7f040000);
		}
		
		setContentView(R.layout.activity_change);
		//把改activity 加入到 activityList 里面方便关闭
		ExitApplication.getInstance().addActivity(this);
		//设置分数初始值。
		SharedPreferences sp = ChangeModle.this.getSharedPreferences(
    			"data",Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("score","0");
		edit.commit();
		
		btnEasy = (Button) this.findViewById(R.id.btnEasy);
		btnHard = (Button) this.findViewById(R.id.btnHard);
		btnNormal = (Button) this.findViewById(R.id.btnNormal);
		tbButton = (ToggleButton) this.findViewById(R.id.tbMusic);
		//进入easy
		btnEasy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(ExitApplication.getInstance().getToggle()){
					((MusicToggle) play).stopM();
				}
					
				Intent intent =  new Intent(ChangeModle.this,EasyActivity.class);
				startActivity(intent);
			}
		});
		
		//进入normal
		btnNormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(ExitApplication.getInstance().getToggle()){
					((MusicToggle) play).stopM();
				}
				Intent intent =  new Intent(ChangeModle.this,NormalActivity.class);
				startActivity(intent);
			}
		});
		
		//进hard入
		btnHard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ExitApplication.getInstance().getToggle()){
					((MusicToggle) play).stopM();
				}
				Intent intent =  new Intent(ChangeModle.this,HardActivity.class);
				startActivity(intent);
			}
		});
		
		//音乐开关按钮功能实现
		tbButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MusicToggle) play).toggleM(ExitApplication.getInstance().getToggle(),ChangeModle.this,0x7f040000,(ToggleButton)ChangeModle.this.findViewById(R.id.tbMusic));
			}	
				
		});
		
		
	}
	//返回事件监听
	@Override
	public void onBackPressed(){
		Intent intent = new Intent(ChangeModle.this,StartUi.class);
		startActivity(intent);
		if(ExitApplication.getInstance().getToggle()){
			((MusicToggle) play).stopM();
		}
		finish();
	}
	
	

	
}
