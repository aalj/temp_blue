/**
 * SQlite.java
 * record.sqlite
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015年8月28日 		AYI
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package record.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ClassName:SQlite
 * 
 * @Function: 数据库
 * @Reason: TODO ADD REASON
 *
 * @author AYI
 * @version
 * @since Ver 1.1
 * @Date 2015年8月28日 下午8:51:06
 *
 * @see
 */
public class SQlite extends SQLiteOpenHelper {

	public SQlite(Context context) {

		// 建数据库
		super(context, "libstep.db", null, 1);
		// TODO Auto-generated constructor stub

	}

	// 创建表
	@Override
	public void onCreate(SQLiteDatabase db) {

		// TODO Auto-generated method stub
		String table = "create table step("
				+ "id integer primary key autoincrement," + "num integer,"
				+ "stepnum integer," + "time data)";

		// 执行
		db.execSQL(table);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// TODO Auto-generated method stub

	}

}
