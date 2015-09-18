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
 * ClassName:MainActivity Function: Viewע����� Reason: TODO ADD REASON
 * 
 * @author Mystery
 * @version
 * @since Ver 1.1
 * @Date 2015 2015-7-29 ����9:50:12
 * 
 * @see
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ʼ��activity_main�е���Բ���
		FrameLayout rativitelayout_content = (FrameLayout) findViewById(R.id.ralativlayout_content);
		// LayoutInflater/******����������********/
		/*** ������������һ�ֳ�ʼ����ʽ ***/
		LayoutInflater myinflater = getLayoutInflater();
		/** �����������ڶ��ֳ�ʼ����ʽ **/
		// LayoutInflater myinflater_1 = LayoutInflater.from(MainActivity.this);

		View myview = myinflater.inflate(R.layout.mycontent, null);
		// ��ʼ�����ò����еĿؼ���Ҫ��xxxview.findViewById
		EditText editText_name = (EditText) myview.findViewById(R.id.editText1);
		// // ���õ�ǰҳ��
		// setContentView(myview);
		// �ھֲ�����Բ����� ����myview
		rativitelayout_content.addView(myview);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
