package com.gem.guessnumber;

import java.util.Random;

public class ToastSay {
	//给激励的话语 数组加值
	private String[] enc = new String[11];
	private String[] hit = new String[11];
	public String sayEnc(){
			enc[0] = "恭喜你！";
			enc[1] = "真棒！";
			enc[2] = "帅呆了！";
			enc[3] = "智商爆表";
			enc[4] = "Fantastic！";
			enc[5] = "Wonderful!";
			enc[6] = "NB的人生不需要解释！";
			enc[7] = "机智的你无人能敌！";
			enc[8] = "这么聪明还有谁？";
			enc[9] = "被你的智慧打败！";
			enc[10] = "对你的崇拜如滔滔江水！";
			int i = (int)(Math.random()*(int)(enc.length/2)+1);
			int j = 0;
			while(true){
				j = (int)(new Random().nextInt(enc.length-1));
				if(j<=(int)(enc.length/2)){
					continue;
				}else{
					break;
				}
			}
			return enc[i]+enc[j];
	}
	public String sayHit(){
		//给打击数组加值
			
			hit[0] = "你错了！";
			hit[1] = "你是猪嘛?";
			hit[2] = "站起来！";
			hit[3] = "干巴爹！";
			hit[4] = "不理想的答案！";
			hit[5] = "难得糊涂！";
			hit[6] = "不敢相信这就是你的实力！";
			hit[7] = "这么简单却错了！";
			hit[8] = "相信你很快成功！";
			hit[9] = "给你勇气,再来一次！";
			hit[10] = "只差一点点！";
			
			int i = (int)(Math.random()*(int)(hit.length/2)+1);
			int j = 0;
			while(true){
				j = (int)(new Random().nextInt(hit.length-1));
				if(j<=(int)(hit.length/2)){
					continue;
				}else{
					break;
				}
			}
			
			return hit[i]+hit[j];
	
	}
}
