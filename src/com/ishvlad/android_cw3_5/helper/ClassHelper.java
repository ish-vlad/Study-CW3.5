package com.ishvlad.android_cw3_5.helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.ClassBase;
import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Class;

public class ClassHelper {
	private final Class TABLE = DBNames.TABLES.CLASS;
	private Context mCtx;
	
	public ClassHelper(Context context) {
		mCtx = context;
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
}
