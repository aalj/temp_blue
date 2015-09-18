package com.gem.guessnumber;

import android.widget.TextView;

public class EasyWay {
	//生成题目的方法
	public int topicWay(int resulte,TextView tvTopicWay){
				//把生成的随机数放到数组里面
				int[] arrayNu = new int[11];
					arrayNu[0] = 0;
					arrayNu[1] = (int)(Math.random()*5+1);
					arrayNu[2] = (int)(Math.random()*5+1);
					arrayNu[3] = (int)(Math.random()*5+1);
					arrayNu[4] = (int)(Math.random()*5+1);
					arrayNu[5] = (int)(Math.random()*5+1);
					arrayNu[6] = (int)(Math.random()*10+1);
					arrayNu[7] = (int)(Math.random()*10+1);
					arrayNu[8] = (int)(Math.random()*10+1);
					arrayNu[9] = (int)(Math.random()*20+1);
					arrayNu[10] = (int)(Math.random()*20+1);
				//生成一个随机数看选择哪套方案
				int a1 = arrayNu[(int)(Math.random()*10+1)];
				int a2 = arrayNu[(int)(Math.random()*10+1)];
				int a3 = arrayNu[(int)(Math.random()*10+1)];
				int a4 = arrayNu[(int)(Math.random()*10+1)];
				int a5 = arrayNu[(int)(Math.random()*10+1)];
				switch((int)(Math.random()*5+1)){
					case 1: resulte = (int)a1*a2+a3-a4%a5;
					tvTopicWay.setText(a1+"*"+a2+"+"+a3+"-"+a4+"%"+a5+"=?");break;
					case 2: resulte = (int)a1%a2*a3+a4-a5;
				 	tvTopicWay.setText(a1+"%"+a2+"*"+a3+"+"+a4+"-"+a5+"=?");break;
				 	
					case 3: resulte = (int)a1-a2%a3*a4+a5;
				 	tvTopicWay.setText(a1+"-"+a2+"%"+a3+"*"+a4+"+"+a5+"=?");break;
				 	
					case 4: resulte = (int)a1+a2-a3%a4*a5;
				 	tvTopicWay.setText(a1+"+"+a2+"-"+a3+"%"+a4+"*"+a5+"=?");break;
				 	
					case 5: resulte = (int)a1*a2-a3%a4+a5;
				 	tvTopicWay.setText(a1+"*"+a2+"-"+a3+"%"+a4+"+"+a5+"=?");break;
//					default
				}
				System.out.println(resulte);
				return resulte;
//				
	}

}
