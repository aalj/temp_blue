package com.gem.huabu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//�Զ��廭��
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
//callbackΪ�ص�����
//OnTouchListener����¼�
public class MyView extends SurfaceView implements Callback,OnTouchListener{
	
	
	//�������ʶ���Paint��ֱ��ʵ����
	private Paint p=new Paint();
	//����Path·����ֱ��ʵ����
	private Path path=new Path();
	private Bitmap bitmap = null;
	
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//ʹ��SurfaceViewҪ���ӻص�����
		getHolder().addCallback(this);
		//��ʼ������
		p.setColor(Color.RED);
		p.setTextSize(0);
		//������ݣ���ǿ����Ч��
		p.setAntiAlias(true);
		p.setStyle(Style.STROKE);
		//���Ӽ����¼�
		 setOnTouchListener(this);
		
	}
	
	
	public Bitmap getCanvas(){
		if(bitmap!=null){
			return bitmap;
		}
		return null;
	}
	//draw����
	public void draw(){
		
		
		
		Canvas canvas=null;
		
		//����canvas������lockCanvas()������������
		canvas=getHolder().lockCanvas();
		canvas.drawColor(Color.WHITE);
		//ͨ��drawPath���л���
		canvas.drawPath(path, p);
		//��������
		getHolder().unlockCanvasAndPost(canvas);
		
		
			
			
		
		
		
	}
	//����������ר���������� 
	public void clear(){
		//�����ǰ·��
		path.reset();
		//Ϊ���ٴν��л��ƣ��������draw()����
		draw();
	}
	
	
	@Override
	public void surfaceCreated(SurfaceHolder holder ) {
		//���÷�����ִ��
		draw();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format,
			int width,int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	//�����ӵ�onTouch����
	@Override
	public boolean onTouch (View v, MotionEvent event) {
		//���������¼�
		//��ȡ��Ļ��x,y����
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			path.moveTo(event.getX(), event.getY());
			draw();
		break;
		
		//�����ƶ��¼������ƶ���ʱ��ͨ��lineTo��������
		case  MotionEvent.ACTION_MOVE:
			path.lineTo(event.getX(), event.getY());
			draw();
			break;
		
		}
	
		return true;
	}

}