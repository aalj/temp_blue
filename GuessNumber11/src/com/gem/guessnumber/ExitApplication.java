package com.gem.guessnumber;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ExitApplication extends Application{
		
	private List<Activity> activityList = new LinkedList<Activity>();
	//建立一个全局变量
	private boolean toggle = true;
	private static ExitApplication instance;
	public ExitApplication(){
	}
	//单例模式中获取唯一的myApplication实例
	public static ExitApplication getInstance(){
		if(null==instance){
			instance = new ExitApplication();
		}
		return instance;
	}
	//把所有的activity 添加道数组里面
	public void addActivity(Activity activity){
		activityList.add(activity);
	}
	//遍历所有Activity 并finish（）
	public void exit(){
		for(Activity activity : activityList){
			activity.finish();
		}
		System.exit(0);
	}
	
	//设置toggle 的值
	public void setToggle(boolean toggle){
		this.toggle = toggle;
	}
	 public boolean getToggle(){
		 return toggle;
	 }
}

