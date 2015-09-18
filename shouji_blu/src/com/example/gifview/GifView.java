/**
 * GifView.java
 * com.example.gifplay
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-8-28 		我的文档
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package com.example.gifview;
import com.zhilian.blu.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
/**
 * ClassName:GifView
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   我的文档
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-8-28		下午12:17:02
 *
 * @see 	 
 */

public class GifView extends View implements Runnable {

	private GIFFrameManager mGIFFrameManager = null;
	private Paint mPaint = null;

	public GifView(Context context) {
		super(context);
		//在这里加入gif图片
		mGIFFrameManager = GIFFrameManager.CreateGifImage(this.fileConnect(this
				.getResources().openRawResource(R.drawable.welcome)));
		new Thread(this).start();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint();
		mGIFFrameManager.nextFrame();
		Bitmap bitmap = mGIFFrameManager.getImage();
		if (bitmap != null) {
			//这个方法里的两个数字参数分别可以
			//设置左侧距离和顶部距离
			//自己调整
			canvas.drawBitmap(bitmap, 0, 0, mPaint);
		}
	}

	public byte[] fileConnect(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch = 0;
		try {
			while ((ch = in.read()) != -1) {
				out.write(ch);
			}
			byte[] b = out.toByteArray();
			out.close();
			out = null;
			in.close();
			in = null;
			return b;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(1000);
				this.postInvalidate();
			} catch (Exception ex) {
				ex.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
