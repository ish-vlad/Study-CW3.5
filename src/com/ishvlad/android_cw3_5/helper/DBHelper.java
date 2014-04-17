package com.ishvlad.android_cw3_5.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	
	
	public static SQLiteDatabase mDB;
	private static OpenDBHelper mODBHelper;
	private static DBHelper instance;
	private static Context mContext;
	
	private DBHelper(Context context) {
		mContext = context;
		open();
	}
	
	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			return new DBHelper(context);
		} else {
			return instance;
		}
	}
	
	public void open() {
		if (mODBHelper == null) {
			mODBHelper = new OpenDBHelper(mContext);
		    mDB = mODBHelper.getWritableDatabase();
		}
	}
	
	public void close() {
		if (mODBHelper != null) {
			mODBHelper.close();
			mContext = null;
		}
	}
	
	public Cursor getAll(String table) {
		return mDB.query(table, null, null, null, null, null, null);
	}
	
	public Cursor findById(String table, int id) {
		String[] selectionArgs = new String[] {Integer.toString(id)};
		return mDB.query(table, null, "_id == ?", selectionArgs, null, null, null);
	}

	
}
