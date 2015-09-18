package com.gem.guessnumber;

import android.widget.TextView;

public class ScoreWay {
	
	
	//加入得分算法
	//加分
	public void addScore(int resulte,TextView tvScore){
		//获取得分的数据
		String score = tvScore.getText().toString();
		int scoreInt = Integer.parseInt(score);
		if((resulte>=100 && resulte <300) || (resulte>-150 && resulte<=(-50))){
			scoreInt += 50; 
		}else if(resulte>=300 || resulte<=(-150)){
			scoreInt += 100;
		}else {
			scoreInt += 20;
		}
		score = String.valueOf(scoreInt);
		tvScore.setText(score);
	}
	
	//减分
	public void cutScore(int resulte,TextView tvScore){
		//获取得分的数据
		String score = tvScore.getText().toString();
		int scoreInt = Integer.parseInt(score);
		if(resulte<=10 && resulte>=(-10)){
			scoreInt -= 100; 
		}else if((resulte>10 && resulte<=50) || (resulte>=-50 && resulte<-10)){
			scoreInt -= 50;
		}else {
			scoreInt -= 20;
		}
		score = String.valueOf(scoreInt);
		tvScore.setText(score);
	}
}
