package com.ishvlad.android_cw3_5.helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.ClassBase;
import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Class;
import com.ishvlad.android_cw3_5.layer.StudentBase;

public class ClassHelper {
	private final Class TABLE = DBNames.TABLES.CLASS;
	private Context mCtx;
	
	public ClassHelper(Context context) {
		mCtx = context;
	}
	
	public void setLecture(int groupId, int lectureId) {
		DBHelper.getInstance(mCtx);
		DBHelper.mDB.execSQL(
				"INSERT INTO " + DBNames.TABLES.CLASS_LECTURE + 
				"(" + DBNames.TABLES.CLASS_LECTURE.CELLS.DATE +  
				"," + DBNames.TABLES.CLASS_LECTURE.CELLS.CLASS_ID +
				"," + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID + 
				" )VALUES(" 
				+ Long.toString(new Date().getTime()) + ", "  
				+ Integer.toString(groupId) + ", "  
				+ Integer.toString(lectureId) + 
				");"
		);
	}
	
	public LinkedList<ClassBase> getAllBase() {
		
		Cursor cursorGroup = DBHelper.getInstance(mCtx).getAll(TABLE.toString());
		LinkedList<ClassBase> result = new LinkedList<ClassBase>();
		
		if(cursorGroup.moveToFirst()) {
			do {
				result.add(new ClassBase(
								cursorGroup.getInt(TABLE.CELLS.ID.getIndex()),
								cursorGroup.getString(TABLE.CELLS.NAME.getIndex()),
								cursorGroup.getInt(TABLE.CELLS.YEAR.getIndex())
								));
			} while (cursorGroup.moveToNext());
		}
		cursorGroup.close();
		
		Collections.sort(result, new Comparator<ClassBase>() {

			@Override
			public int compare(ClassBase lhs, ClassBase rhs) {
				Integer secondYear = rhs.year;
				
				return secondYear.compareTo(lhs.year) != 0  ? secondYear.compareTo(lhs.year) 
														    : lhs.name.compareTo(rhs.name); 
			}
		});
		
		return result;
	}
	
	public LinkedList<StudentBase> getStudents(int groupId) {
		LinkedList<StudentBase> result = new LinkedList<StudentBase>();
		
		String[] selectionArgs = new String[] {Integer.toString(groupId)};
		DBHelper.getInstance(mCtx);
		Cursor cursorStudents = DBHelper.mDB.rawQuery(
					"SELECT * FROM " + DBNames.TABLES.STUDENT +
					" WHERE " + DBNames.TABLES.STUDENT.CELLS.CLASS_ID + " == ? "
											, selectionArgs);
		
		if(cursorStudents.moveToFirst()) {
			do {
				result.add(new StudentBase(
						cursorStudents.getInt(TABLE.CELLS.ID.getIndex()),
						cursorStudents.getString(TABLE.CELLS.NAME.getIndex())
						));
			} while (cursorStudents.moveToNext());
		}
		cursorStudents.close();
		
		Collections.sort(result, new Comparator<StudentBase>() {

			@Override
			public int compare(StudentBase lhs, StudentBase rhs) {
				return lhs.toString().compareTo(rhs.toString());
			}
		});
		
		return result;
	}

	public ClassBase getBase(int groupId) {
		Cursor cursorGroup = DBHelper.getInstance(mCtx).findById(TABLE.toString(), groupId);
		
		ClassBase result = null;
		if(cursorGroup.moveToFirst()) {
			result =  new ClassBase(
					cursorGroup.getInt(TABLE.CELLS.ID.getIndex()),
					cursorGroup.getString(TABLE.CELLS.NAME.getIndex()),
					cursorGroup.getInt(TABLE.CELLS.YEAR.getIndex())
					);
		}
		cursorGroup.close();
		
		return result;
	}
}
