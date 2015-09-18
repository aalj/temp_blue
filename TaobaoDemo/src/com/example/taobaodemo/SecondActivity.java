package com.example.taobaodemo;

import infobean.InfoBean;

import java.util.ArrayList;
import java.util.List;

import TextUtils.CartNumber;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.MyBaseAdapter.MyBaseAdapter;

public class SecondActivity extends Activity {
	ListView listview;
	List<InfoBean> list = new ArrayList<InfoBean>();
    int  number=0;
    
     static  TextView tvnumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		dataADD();
		spinAdd();
		viewInit();
		
		
		Intent intent_back=getIntent();
		 tvnumber=(TextView) findViewById(R.id.text_number);
       	tvnumber.setText(intent_back.getStringExtra("str"));
		
		
	}



	public void spinAdd() {
		String[] spi = { "服装", "美食", "旅游", "其他" };
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SecondActivity.this, android.R.layout.simple_spinner_item, spi);
		spinner.setAdapter(adapter);

	}

	public void viewInit() {

		listview = (ListView) findViewById(R.id.listView1);
		
		listview.setAdapter(new MyBaseAdapter(SecondActivity.this,SecondActivity.this, list));

		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                Bundle extras=new Bundle();
                extras.putInt("pic", list.get(arg2).getPic());
                extras.putString("dsc", list.get(arg2).getDsc());
                extras.putFloat("price", list.get(arg2).getPrice());
				intent.putExtra("bundle", extras);
//				startActivityForResult(intent, 1);
				startActivity(intent);
//				finish();
				
			}
		});
	}

	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		
//		// TODO Auto-generated method stub
//		
//		if(resultCode==0){
//			TextView tvnumber=(TextView) findViewById(R.id.text_number);
//			tvnumber.setText(data.getStringExtra("str"));
//		}
//		
//		super.onActivityResult(requestCode, resultCode, data);
//		
//	} 
	public void dataADD() {
		
		String[] storename = { "小成吉蒙汉风味涮坊", "萨博将军韩式快餐", "爱地方餐饮（总店）", "高丽旺狗肉馆",
				"兰州烧饼店" };
		int[] ima = { R.drawable.meinv1, R.drawable.meinv4, R.drawable.meinv7,
				R.drawable.meinv5, R.drawable.meinv3, R.drawable.meinv2 };
		float[] price = { 12.23f, 34.3f, 23.33f, 129.00f, 132.2f};
		
		for (int i = 0; i < price.length; i++) {
			InfoBean bean=new InfoBean();
			bean.setDsc(storename[i]);
			bean.setPic(ima[i]);
			bean.setPrice(price[i]);
			list.add(bean);
		}
		
		
	}
	
	
	public static  void setMyText(String  mytext)
	{
		tvnumber.setText(mytext);
	}


}
