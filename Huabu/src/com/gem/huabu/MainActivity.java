package com.gem.huabu;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btn;
	private Button btn1;
	// 为了清理画布，获取view，创建MyView对象
	private MyView view;
	View inflate = null;
	private Bitmap bitmap=null;
	LinearLayout layout = null;
	FrameLayout frameLayout = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		  layout = (LinearLayout) findViewById(R.id.container);
		//放画板的空间
//		  frameLayout = (FrameLayout) findViewById(R.id.fra_drw);
//		LayoutInflater inflater = getLayoutInflater();
//		  inflate = inflater.inflate(R.layout.myview, null);
//		frameLayout.addView(inflate);
		
		System.out.println("asdjjnflknfglnfdgja");
		// 因为只有一个Button，做一个监听事件
		btn = (Button) findViewById(R.id.btn);
		//图片保存按钮
		btn1 = (Button) findViewById(R.id.btn1);
//		// 获取画板控件控件
		view = (MyView) findViewById(R.id.fra_drw);
		  
		btn.setOnClickListener(new MyOnClickListener());
		btn1.setOnClickListener(new MyOnClickListener());

	}
	/**
	 * 
	 * convertViewToBitmap:(得到视图的bitmap对象)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param view
	 * @param  @return    设定文件
	 * @return Bitmap      
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public static Bitmap convertViewToBitmap(View view){
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	        view.buildDrawingCache();
	        Bitmap bitmap = view.getDrawingCache();

	        return bitmap;
	}

	/**
	 * 
	 * ClassName:MyOnClickListener Function: 按钮点击监听器 Reason: TODO ADD REASON
	 *
	 * @author view
	 * @version MainActivity
	 * @since Ver 1.1
	 * @Date 2015 2015年8月26日 下午10:01:14
	 *
	 * @see
	 */
	public class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn:
				System.out.println("qingli");
				view.clear();
				break;
			case R.id.btn1:
				Bitmap convertViewToBitmap = convertViewToBitmap(btn);
				getpIc();
				System.out.println("dfhsfoiushpfoigdsop");
//				Bitmap bitmap = view.getCanvas();
				String fileName = "mark.png";
				savePic(convertViewToBitmap, fileName);
				break;

			default:
				break;
			}

		}

	}

	public void getpIc() {
//		view.setDrawingCacheEnabled(true);
		try {
			
			FileOutputStream fos = new FileOutputStream("/sdcard/JetMobileDev/mark.png");
			System.out.println("bitmap     "+bitmap);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
				
		Toast.makeText(MainActivity.this, "我在村图", 0).show();
		
		
		
		
		
//		View view = getWindow().getDecorView();
//		Display display = MainActivity.this.getWindowManager().getDefaultDisplay();
//		view.layout(0, 0, display.getWidth(), display.getHeight());
//		view.setDrawingCacheEnabled(true);
//		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//		String fileName = "mark.png";
//		savePic(bitmap, fileName);
	}

	protected void savePic(Bitmap bitmap, String fileName) {
		
		
		
		
		
		FileOutputStream fos = null;
		String path = "/sdcard/JetMobileDev/";
		File file = new File(path);
		file.mkdirs();
		File file2 = new File(file, fileName);
		try {
			fos = new FileOutputStream(file2);
			if (null != fos) {
				bitmap.compress(CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
