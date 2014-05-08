package com.ishvlad.android_cw3_5.helper;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.Base;
import com.ishvlad.android_cw3_5.layer.DBNames;

public abstract class DataHelper {
	
	public abstract Base upgradeBase(Base what, int studentId);
	public abstract Base upgradeStudent(Base student, int whatId);
	public abstract LinkedList<Base> getAllBase(int collectionId);
	public abstract void insert(Base student, int whereId, int groupId);
	public abstract void update(Base itemNew, Base itemOld) throws ParseException;
	public abstract void delete(Base item);
	
	
	
	public LinkedList<Base> getAllStudents(int groupId) {
		LinkedList<Base> result = new LinkedList<Base>();
		
		String[] selectionArgs = new String[] {Integer.toString(groupId)};
		
		Cursor cursorStudents = DBHelper.mDB.rawQuery(
					"SELECT * FROM " + DBNames.TABLES.STUDENT +
					" WHERE " + DBNames.TABLES.STUDENT.CELLS.CLASS_ID + " == ? "
					, selectionArgs);
		
		if(cursorStudents.moveToFirst()) {
			do {
				result.add(new Base(
						cursorStudents.getInt(DBNames.TABLES.STUDENT.CELLS.ID.getIndex()),
						cursorStudents.getString(DBNames.TABLES.STUDENT.CELLS.NAME.getIndex()),
						new Date().getTime()
						));
			} while (cursorStudents.moveToNext());
		}
		cursorStudents.close();
		
		Collections.sort(result, new Comparator<Base>() {
			@Override
			public int compare(Base lhs, Base rhs) {
				return lhs.name.compareTo(rhs.name);
			}
		});
		
		return result;
	}
}
