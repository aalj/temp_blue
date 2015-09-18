package com.gem.guessnumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class StartUi extends Activity{
//	private Button btnStart,btnContinue,btnRank;
	private ToggleButton tbButton ;
	private Object play = new MusicToggle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		//创建音乐  ,加载音乐
		if(ExitApplication.getInstance().getToggle()){
			((MusicToggle) play).startM(this, 0x7f040005);
		}
		
		//音乐开关按钮功能实现
		tbButton = (ToggleButton) StartUi.this.findViewById(R.id.tbMusic);
			tbButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((MusicToggle) play).toggleM(ExitApplication.getInstance().getToggle(),
							StartUi.this,0x7f040005,(ToggleButton)StartUi.this.findViewById(R.id.tbMusic));
//					boolean sign = ExitApplication.getInstance().getToggle();
//					if(sign == true){
//						ExitApplication.getInstance().setToggle(false);
//					}else{
//						ExitApplication.getInstance().setToggle(true);
//					}
				}	
			});
}

	
	//创建一个 取 data.xml  activity的值
	public String getData(){
		SharedPreferences sp = this.getSharedPreferences(
    			"data",Context.MODE_PRIVATE);
		return sp.getString("value","" );
	}
	
	//返回事件监听
	@Override
	public void onBackPressed(){
		new AlertDialog.Builder(this).setTitle("系统提示")//设置对话框标题  
		  
	     .setMessage("您确认退出游戏吗？")//设置显示的内容  
	  
	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
	          
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
	        	 //关闭所有的activity
	        	 ExitApplication.getInstance().exit();
	         }  
	  
	     }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮  
	  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//响应事件  
	  
	         }  
	  
	     }).show();//在按键响应事件中显示此对话框  
	}

	//开始游戏
	public void startClick(View view) {
		//设置分数初始值。
		SharedPreferences sp = StartUi.this.getSharedPreferences(
    			"data",Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("score","0");
		edit.commit();
		
		Intent intent = new Intent(StartUi.this,ChangeModle.class);
		startActivity(intent);
		if(ExitApplication.getInstance().getToggle()){
			((MusicToggle) play).stopM();
		}
		finish();
	}
//继续游戏。。。
	
	public void continueClick(View view) {
		String value = getData();
		if(value.equals("3")){
			Intent intent = new Intent(StartUi.this,HardActivity.class);
			startActivity(intent);
		}else if(value.equals("2")){
			Intent intent = new Intent(StartUi.this,EasyActivity.class);
			startActivity(intent);
		}else if(value.equals("1")){
			Intent intent = new Intent(StartUi.this,NormalActivity.class);
			startActivity(intent);
		}else {
			Intent intent = new Intent(StartUi.this,ChangeModle.class);
			startActivity(intent);	
		}
		if(ExitApplication.getInstance().getToggle()){
			((MusicToggle) play).stopM();
		}
		finish();
	}

//排行榜 获取最高分
	
	public void rankClick(View view) {
		//跳转到 最高分 的activity
		Intent intent = new Intent(StartUi.this,ListScore.class);
//		System.out.println("进来没");
		startActivity(intent);
		if(ExitApplication.getInstance().getToggle()){
			((MusicToggle) play).stopM();
		}
		finish();
	}
	


}
