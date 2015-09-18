package com.aalj36.aa31;

import com.aalj36.aa31.mysqliteopenhelper.MySQLiteOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/**
 * 
 * ClassName:MainActivity
 * Function: Sqlite数据库练习
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015	2015年8月26日		上午11:04:17
 *
 * @see
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onclick(View v){
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(MainActivity.this);
		SQLiteDatabase database = helper.getWritableDatabase();
		if(database.isOpen()){
			
			ContentValues values = new ContentValues();
			values.put("name", "zhangshan");
			values.put("age", "13");
			values.put("aihao", "chichi ");
			database.insert("student_info", null, values);
			database.close();
		}
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
