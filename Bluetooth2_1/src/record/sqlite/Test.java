package record.sqlite;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.exmaple2.bluetooth2.R;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Test extends Activity {

	int stepnum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	}

	// 发送并保存按钮点击事件
	public void onclick(View v) {
		Date date = new Date();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		EditText ed_step = (EditText) findViewById(R.id.editText1);
		if (ed_step.getText().toString().equals("")) {
			stepnum = 0;
		} else {
			stepnum = Integer.parseInt(ed_step.getText().toString());
		}

		SQlite helper = new SQlite(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "insert into step(stepnum,time) values('" + stepnum
				+ "','" + time + "') ";
		db.execSQL(sql);

		Intent intent = new Intent();
		intent.setClass(Test.this, SQLite_Activity.class);
		Test.this.startActivity(intent);

	}
}
