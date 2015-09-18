package com.example.taobaodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends Activity {
	EditText et;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		et=(EditText) findViewById(R.id.edit_numner);
//		intent=getIntent();
		viewInit();
	}
  @Override
public void onBackPressed() {
	
	// TODO Auto-generated method stub
	  
	super.onBackPressed();
	String str=et.getText().toString();
	Intent intent1=new Intent();
	if(TextUtils.isEmpty(str)){
		str=0+"";
	}
	//获取回来的数据
	intent1.putExtra("str", str);
//	setResult(0, intent1);
	startActivity( intent1.setClass(ThirdActivity.this, SecondActivity.class));
	  finish();
}
  public void viewInit(){
	  ImageView iv=(ImageView) findViewById(R.id.ima_3);
	  TextView tv_dsc=(TextView) findViewById(R.id.text_3_dsc);
	  
	  TextView tv_price=(TextView) findViewById(R.id.text_3_price);
	  
	  
	  intent=getIntent();
	  Bundle bundle=intent.getBundleExtra("bundle");
	  iv.setImageResource(bundle.getInt("pic"));
	  tv_dsc.setText( bundle.getString("dsc"));
	  tv_price.setText(bundle.getString("￥"+"price"));
	  
	  
	  
  }
  public void third_click(View v){
	  
	  TextView tv_number=(TextView) findViewById(R.id.text_3_number);
	  if(TextUtils.isEmpty(et.getText().toString())){
		  Toast.makeText(this,"请输入数量", Toast.LENGTH_SHORT).show();
	  }else {tv_number.setText(et.getText().toString());}
		String str=et.getText().toString();
//		Intent intent=new Intent();
		if(TextUtils.isEmpty(str)){
			str=0+"";
		}
		System.out.println(str);
		intent.putExtra("str", str);
		setResult(0, intent);
  }
}
