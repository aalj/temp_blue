package com.example.arcmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView lv;
	List<String> mylist=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		dataInit();
		viewInit();
		
		ArrayAdapter<String > myadapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,mylist);
		lv.setAdapter(myadapter);
	
	}
	private void viewInit() {
		lv=(ListView)findViewById(R.id.listView1);
		// TODO Auto-generated method stub
		
	}
	private void dataInit() {
			mylist.add("a");
			mylist.add("b");
			mylist.add("c");
			mylist.add("d");
			mylist.add("e");
			mylist.add("f");
			mylist.add("g");
			mylist.add("h");
			mylist.add("i");
			mylist.add("j");
			mylist.add("k");
			mylist.add("l");
			mylist.add("m");
			mylist.add("n");
			mylist.add("o");
			mylist.add("p");
			mylist.add("q");
			mylist.add("r");
		// TODO Auto-generated method stub
		
	}

}
