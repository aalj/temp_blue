package com.gem.guessnumber;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MusicToggle {
	private MediaPlayer player;
	//音乐总开关
	public void startM(Context c,int id){
		player = MediaPlayer.create(c , id);
		player.setLooping(true);
		player.start();
	}
	public void stopM(){
		player.stop();
	}
	
	public void toggleM(boolean b,Context c,int id,ToggleButton tbButton ){
		if(b == true){
//			停止音乐
			Toast.makeText(c,"音乐已停止", Toast.LENGTH_SHORT).show();
			//设置全局变量的值，方便其他界面判断是否音乐开启
			ExitApplication.getInstance().setToggle(false);
			tbButton.setBackgroundResource(R.drawable.music_off);
			player.stop();
		}else if(b == false){
			Toast.makeText(c,"音乐已打开", Toast.LENGTH_SHORT).show();
			player = MediaPlayer.create(c,id);
			player.setLooping(true);
			player.start();
			tbButton.setBackgroundResource(R.drawable.music_on);
			ExitApplication.getInstance().setToggle(true);
		}
	}	
}
