package com.example.viewinjectiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 
 * ClassName:MainActivity Function: View注入测试 Reason: TODO ADD REASON
 * 
 * @author Mystery
 * @version
 * @since Ver 1.1
 * @Date 2015 2015-7-29 上午9:50:12
 * 
 * @see
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化activity_main中的相对布局
		FrameLayout rativitelayout_content = (FrameLayout) findViewById(R.id.ralativlayout_content);
		// LayoutInflater/******布局引用器********/
		/*** 布局引用器第一种初始化方式 ***/
		LayoutInflater myinflater = getLayoutInflater();
		/** 布局引用器第二种初始化方式 **/
		// LayoutInflater myinflater_1 = LayoutInflater.from(MainActivity.this);

		View myview = myinflater.inflate(R.layout.mycontent, null);
		// 初始化引用布局中的控件需要用xxxview.findViewById
		EditText editText_name = (EditText) myview.findViewById(R.id.editText1);
		// // 设置当前页面
		// setContentView(myview);
		// 在局部的相对布局上 覆盖myview
		rativitelayout_content.addView(myview);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
