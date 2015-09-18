package com.gem.huabu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Welcome extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		FrameLayout welcome_f = (FrameLayout) findViewById(R.id.welcome_f);
		welcome_f.setOnClickListener(new MyOnClickListener());
		
	}
	public class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(Welcome.this,MainActivity.class);
			startActivity(intent);
//			finish();
		}
	}
}
