/**
 * MyBaseAdapter.java
 * com.example.MyBaseAdapter
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-8-2 		疯子
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package com.example.MyBaseAdapter;

import infobean.InfoBean;

import java.util.ArrayList;
import java.util.List;

import TextUtils.CartNumber;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobaodemo.R;
import com.example.taobaodemo.SecondActivity;

/**
 * ClassName:MyBaseAdapter Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author 疯子
 * @version
 * @since Ver 1.1
 * @Date 2015-8-2 下午1:07:34
 * 
 * @see
 */
public class MyBaseAdapter extends BaseAdapter {
	LayoutInflater inflater;
	EditText et;
	List<InfoBean> mylist = new ArrayList<InfoBean>();
	Context context1;
	
	Context mycontext;

	public MyBaseAdapter(Context context, Context context2,
			List<InfoBean> outlist) {
		context1 = context2;
		mylist = outlist;
		inflater = LayoutInflater.from(context);
		
		mycontext=context;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return mylist.size();

	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return mylist.get(position);

	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.listitems, null);

		//et = (EditText) convertView.findViewById(R.id.edit);
		// et.setFocusableInTouchMode(false); //设置不可编辑状态
    	TextView tv_dsc = (TextView) convertView.findViewById(R.id.text_dsc);
		TextView tv_price = (TextView) convertView
				.findViewById(R.id.text_price);
		Button bt = (Button) convertView.findViewById(R.id.button_addcart);
		
	final	EditText et=(EditText)convertView.findViewById(R.id.editText1);
		

		tv_price.setText("￥" + mylist.get(position).getPrice() + "");
		bt.setFocusable(false);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SecondActivity.setMyText(et.getText().toString());
				Toast.makeText(context1, "点击了按钮", Toast.LENGTH_SHORT).show();
				
				
				et.setFocusable(false);
				
                
//                View myview=inflater.inflate(R.layout.activity_second, null);
//               // View myview1=inflater1.inflate(R.layout.activity_third, null);
//                TextView  tv_cart=(TextView) myview.findViewById(R.id.text_number);
//               // TextView  tv_cart1=(TextView) myview1.findViewById(R.id.text_3_number);
//                tv_cart.setText(
//                		"22"
//                		);
//				System.out.println("进入了按钮点击事件");
				
				
//				 AlertDialog.Builder builder= new
//			 AlertDialog.Builder(context1).setTitle("请输入数量").setIcon(R.drawable.meinv5).setMessage("请输入正确的商品数量");

			}
		});
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
		iv.setImageResource(mylist.get(position).getPic());
		tv_dsc.setText(mylist.get(position).getDsc());
		return convertView;

	}
//	 private AlertDialog.Builder setPositiveButton(AlertDialog.Builder
//	 builder){
//	 return builder.setPositiveButton("确定", new OnClickListener() {
//		
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			
//			// TODO Auto-generated method stub
//			
//		}
//	});
	
	 
}
