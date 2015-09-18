package com.gem.guessnumber;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EasyActivity extends Activity {

	private int resulte = 0;
	private Object play = new MusicToggle();

	//给提交注册一个事件
	//添加一个标签，让提交点一次就失效 ，点下一关在重启
	boolean mark = true;
	public void click(View view){
		//隐藏键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(this.findViewById(R.id.etResult).getWindowToken(), 0);
		//判断按钮
		if(mark){
			//注册一个数组，放鼓励的话
			EditText etClick = (EditText) this.findViewById(R.id.etResult);
			String inputString = etClick.getEditableText().toString();
			String resulteString = String.valueOf(resulte); if(resulteString.equalsIgnoreCase(inputString)){
				TextView tvClick = (TextView) this.findViewById(R.id.tvAnswerContent);
				tvClick.setText(String.valueOf(resulte));

				Toast.makeText(this, new ToastSay().sayEnc(),Toast.LENGTH_SHORT).show();
				
				//把分数增加上去
				new ScoreWay().addScore(resulte,(TextView)this.findViewById(R.id.tvScoreContent));
			}else{
				//显示 正确结果  证明他错了
				TextView tvClick = (TextView) this.findViewById(R.id.tvAnswerContent);
				tvClick.setText(String.valueOf(resulte));
				
				Toast.makeText(this,new ToastSay().sayHit(), Toast.LENGTH_SHORT).show();
				
				//失败把相应分数减掉
				new ScoreWay().cutScore(resulte,(TextView)this.findViewById(R.id.tvScoreContent));
			}
			//把 mark 标签 改成   false；让提交失效
			if(mark==true){
				mark = false;
			}
			if(reMark==false){
				reMark = true;
			}
		}
	}
	
	
	//给 按钮  下一关 注册事件   
	public void next(View view){
			//如果结果为空，就按下一关， 直接扣分 -50
			EditText etNext = (EditText) this.findViewById(R.id.etResult);
			String inputString = etNext.getEditableText().toString();
			String s = "";
			if(s.equalsIgnoreCase(inputString)){
				//减分
				TextView tvNext = (TextView) this.findViewById(R.id.tvScoreContent);
				String score = tvNext.getText().toString();
				int scoreInt = Integer.parseInt(score);
					scoreInt -= 50;
					score = String.valueOf(scoreInt);
					tvNext.setText(score);
			
		}
//			topicWay();
			TextView tv = (TextView) this.findViewById(R.id.tvContent);
			tv.setTextColor(Color.rgb((int)(Math.random()*200+1),(int)(Math.random()*200+1),(int)(Math.random()*200+1)));
			TextView tv1 = (TextView) this.findViewById(R.id.tvContent1);
			tv1.setTextColor(Color.rgb((int)(Math.random()*200+1),(int)(Math.random()*200+1),(int)(Math.random()*200+1)));
			resulte = new EasyWay().topicWay(resulte,(TextView)this.findViewById(R.id.tvContent));
			clear();
			//让提交变得有效
			if(mark==false){
				mark = true;
			}
			if(reMark==false){
				reMark = true;
			}

	}
	//给 按钮重新再来  注册事件
	boolean reMark = true;
	public void reset(View view){
		if(reMark){
			
//			topicWay();
			resulte = new EasyWay().topicWay(resulte,(TextView)this.findViewById(R.id.tvContent));
			clear();
			restore();	
			reMark = false;
		}
		if(mark==false){
			mark = true;
		}
		
	}
	//清空  输入框  和  正确答案框  原有的值
	public void clear(){
		EditText etClear = (EditText) this.findViewById(R.id.etResult);
		TextView tvClear = (TextView) this.findViewById(R.id.tvAnswerContent);
		String text = "";
		etClear.setText(text);
		tvClear.setText(text);
	}
	//重来的话把原有分数初始化
	public void restore(){
		TextView tvRestore = (TextView) this.findViewById(R.id.tvScoreContent);
		String score = "0";
		tvRestore.setText(score);
	}
	
	//游戏规则介绍
	public void rule(View view){
		String ruleString = "一、阅题目算答案后输入点提交二、%取模运算，即取除法余数" +
				"三、选择相应模式，改变游戏难度四、游戏进行时会有相应分数计算。";
		Toast.makeText(this, ruleString, Toast.LENGTH_LONG).show();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//加载音乐  
		if(ExitApplication.getInstance().getToggle()){
		((MusicToggle)play).startM(this,0x7f040001);
		}
		
		//把改activity 加入到 activityList 里面方便关闭
		ExitApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_main);
		//让输入框 只输入 数字
		EditText et = (EditText) this.findViewById(R.id.etResult);
		//设置输入法，只能输入哪些数字
		et.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		//调用方法，让它一开始就运行一次
		
		//如果是继续游戏，就取得保存的分数
		//创建一个 取 data.xml  保存 分数   的值
		TextView tvScore = (TextView) EasyActivity.this.findViewById(R.id.tvScoreContent);
		tvScore.setText(getScore());
		
		//创建计算题
		resulte = new EasyWay().topicWay(resulte,(TextView)this.findViewById(R.id.tvContent));
		
		
	}
	
	

	//检测返回事件
		@Override
		public void onBackPressed(){
			TextView tvScore = (TextView) EasyActivity.this.findViewById(R.id.tvScoreContent);
			String scoreString = tvScore.getText().toString();
		    dataXml("2",scoreString);
			Intent intent = new Intent(EasyActivity.this,StartUi.class);
			startActivity(intent);
			if(ExitApplication.getInstance().getToggle()){
				((MusicToggle) play).stopM();
			}
			finish();
		}
		
	//音乐开关
	public void mute(View view){
		((MusicToggle)play).toggleM(ExitApplication.getInstance().getToggle(),EasyActivity.this,0x7f040001,
				(ToggleButton)this.findViewById(R.id.tbMusic));
	}
		//保存数据按钮  功能实现=====================
		public void save(View view){
			new AlertDialog.Builder(this).setTitle("系统提示")//设置对话框标题  
			  
		     .setMessage("您确认退出游戏吗？")//设置显示的内容  
		  
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
		          
		         @Override  
		         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
		        	 TextView tvScore = (TextView) EasyActivity.this.findViewById(R.id.tvScoreContent);
		        	 String scoreString = tvScore.getText().toString();
		        	 //存最高分数， 存之前先做判断   是否比以前的分数高
		        	 if(Integer.parseInt(getMaxScore("Easy"))<Integer.parseInt(scoreString)){
		        		 scoreXml("Easy",scoreString);
		        	 }
		        	 //关闭所有的activity
		        	 dataXml("2",scoreString);
		        	 ExitApplication.getInstance().exit();
		         }  
		  
		     }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮  
		  
		         @Override  
		         public void onClick(DialogInterface dialog, int which) {//响应事件  
		  
		         }  
		  
		     }).show();//在按键响应事件中显示此对话框  
		}
public void dataXml(String i,String y){
			SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("value", i);
			edit.putString("score", y);
			edit.commit();
		}

//创建一个 取 data.xml  保存 分数   的值
public String getScore(){
	SharedPreferences sp = this.getSharedPreferences(
			"data",Context.MODE_PRIVATE);
	return sp.getString("score", "");
}
public void scoreXml(String name,String score){
	SharedPreferences sp = getSharedPreferences("score", Context.MODE_PRIVATE);
	Editor edit = sp.edit();
	edit.putString("Hard","0" );
	edit.putString(name, score);
	edit.commit();
}

//创建一个 取 score.xml  保存 分数   的值
public String getMaxScore(String name){
	SharedPreferences sp = this.getSharedPreferences(
			"score",Context.MODE_PRIVATE);
	return sp.getString(name, "0");
}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	

}
