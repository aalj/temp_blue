package com.gem.guessnumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

public class ListScore extends Activity{
		private ToggleButton tbButton;
		private Object play=new MusicToggle();
		@Override
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			//加载音乐
			if(ExitApplication.getInstance().getToggle()){
			((MusicToggle)play).startM(this,0x7f040004);
			}
			
			setContentView(R.layout.list_score);
			//创建获取数组的 最高分数
			String[] courses = new String[4];
			SharedPreferences sp = this.getSharedPreferences("score", Context.MODE_PRIVATE);
			String scoreString1 = sp.getString("Easy", "");
			String scoreString2 = sp.getString("Normal", "");
			String scoreString3 = sp.getString("Hard", "");
			courses[0] = "最高分数";
			courses[1] = "简单模式："+scoreString1;
			courses[2] = "正常模式："+scoreString2;
			courses[3] = "地狱模式："+scoreString3;
			
			final ListView lvScore = (ListView) this.findViewById(R.id.lvScore);
			
			ListAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.tvScore, courses);
			
			lvScore.setAdapter(adapter);
			
			//音乐开关按钮功能实现
			tbButton = (ToggleButton) ListScore.this.findViewById(R.id.tbMusic);
			tbButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					((MusicToggle) play).toggleM(ExitApplication.getInstance().getToggle(),ListScore.this,0x7f040004,
							(ToggleButton)ListScore.this.findViewById(R.id.tbMusic));
				}	
					
			});
		}
	
		//检测返回事件
		@Override
		public void onBackPressed(){
			Intent intent = new Intent(ListScore.this,StartUi.class);
			startActivity(intent);
			if(ExitApplication.getInstance().getToggle()){
				((MusicToggle) play).stopM();
			}
			finish();
		}
		
}
