package com.ishvlad.android_cw3_5.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import android.database.Cursor;

import com.ishvlad.android_cw3_5.helper.DBHelper;

public class NewGroup {
	public String name;
	public int year;
	public ArrayList<String> students = new ArrayList<String>();
	
	public String save() {
		DBHelper dbh = null;
		try {
			dbh = DBHelper.getInstance(null);
		} catch (NullPointerException e) {
			return "null!!";
		}
		
		dbh.mDB.execSQL("INSERT INTO Class(name, year) VALUES ('"+ name +"', " + Integer.toString(year) + ");");
		
		Cursor cursorGroup = dbh.getAll(DBNames.TABLES.CLASS.toString());
		String classId = null;
		if(cursorGroup.moveToLast()) {
			classId = Integer.toString(cursorGroup.getInt(DBNames.TABLES.CLASS.CELLS.ID.getIndex()));
		}
		cursorGroup.close();
		
		for (String item:students) {
			dbh.mDB.execSQL("INSERT INTO Student(name, class_id) VALUES ('" + item + "', " + classId + ");");
		}
		return "Complete!";
	}
}
