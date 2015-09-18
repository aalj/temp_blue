package com.example.myimageloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * ClassName:MainActivity
 * 
 * @Function: 大图片加载测试
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015年8月14日 下午1:50:29
 *
 * @see
 */
public class MainActivity extends Activity {

	// 工具类的对象 图片加载工具 从application类中获取
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView img = (ImageView) findViewById(R.id.imageView1);
		// img.setImageResource(R.drawable.pic);
		// ImageView img1 = (ImageView) findViewById(R.id.imageView2);
		// img.setImageResource(R.drawable.pic);
		// ImageView img3 = (ImageView) findViewById(R.id.imageView3);
		// img.setImageResource(R.drawable.pic);

		// 一参是图片的URI地址 （可以是网络图片 可以是文件图片，也可以是drawable图片）
		// 二参是imageview对象
		imageLoader.displayImage("drawable://" + R.drawable.pic, img);
		
		
	}

}
