/**
 * MySQLiteOpenHelper.java
 * com.aalj36.aa31.mysqliteopenhelper
 *
 * Function�� TODO 
 *
 *   ver     date      		author
 * ��������������������������������������������������������������������
 *   		 2015��8��26�� 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj36.aa31.mysqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ClassName:MySQLiteOpenHelper
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015��8��26��		����10:38:19
 *
 * @see 	 

 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public MySQLiteOpenHelper(Context context) {
		//ͨ������ϵͳ�ķ�������һ�����ݿ�
		super(context, "info.db", null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//�������ݿ�  ��
		String sql = "create table student_info("
				+ "_id  integer primary key,"
				+ "name text,"
				+ "age text,"
				+ "aihao text);";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		


	}

}

