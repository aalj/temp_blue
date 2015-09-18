package com.example.myimageloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * ClassName:MainActivity
 * 
 * @Function: ��ͼƬ���ز���
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015 2015��8��14�� ����1:50:29
 *
 * @see
 */
public class MainActivity extends Activity {

	// ������Ķ��� ͼƬ���ع��� ��application���л�ȡ
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

		// һ����ͼƬ��URI��ַ ������������ͼƬ �������ļ�ͼƬ��Ҳ������drawableͼƬ��
		// ������imageview����
		imageLoader.displayImage("drawable://" + R.drawable.pic, img);
		
		
	}

}
