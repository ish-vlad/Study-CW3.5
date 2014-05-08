package com.ishvlad.android_cw3_5.helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Object;
import com.ishvlad.android_cw3_5.layer.ObjectBase;

public class ObjectHelper {
	
	private final Object TABLE = DBNames.TABLES.OBJECT;
	private Context mCtx;
	
	public ObjectHelper(Context context) {
		mCtx = context;
	}

	public LinkedList<ObjectBase> getAllBaseFromGroupId(int groupId) {
		String[] selectionArgs = new String[] {Integer.toString(groupId)};
		DBHelper.getInstance(mCtx);
		Cursor cursorGroup = DBHelper.mDB.rawQuery("SELECT * "
											+" FROM " + TABLE +" AS obj INNER JOIN " + DBNames.TABLES.CLASS_OBJECT + " AS clsObj "
											+" ON obj."+ TABLE.CELLS.ID 
													+ " == " + 
												"clsObj."+ DBNames.TABLES.CLASS_OBJECT.CELLS.OBJECT_ID 
											+" WHERE ? == " +
												"clsObj."+ DBNames.TABLES.CLASS_OBJECT.CELLS.CLASS_ID
											, selectionArgs);
		
		LinkedList<ObjectBase> result = new LinkedList<ObjectBase>();
		
		if(cursorGroup.moveToFirst()) {
			do {
				result.add(new ObjectBase(
						cursorGroup.getInt(TABLE.CELLS.ID.getIndex()),
						cursorGroup.getString(TABLE.CELLS.NAME.getIndex()),
						cursorGroup.getInt(TABLE.CELLS.YEAR.getIndex())
						));
			} while (cursorGroup.moveToNext());
		}
		cursorGroup.close();
		
		Collections.sort(result, new Comparator<ObjectBase>() {

			@Override
			public int compare(ObjectBase lhs, ObjectBase rhs) {
				Integer secondYear = rhs.year;
				
				return secondYear.compareTo(lhs.year) != 0  ? secondYear.compareTo(lhs.year) 
														    : lhs.name.compareTo(rhs.name); 
			}
		});
		
		return result;
	}

	public ObjectBase getBase(int objectId) {
		Cursor cursorGroup = DBHelper.getInstance(mCtx).findById(TABLE.toString(), objectId);
		
		ObjectBase result = null;
		if(cursorGroup.moveToFirst()) {
			result =  new ObjectBase(
								cursorGroup.getInt(TABLE.CELLS.ID.getIndex()),
								cursorGroup.getString(TABLE.CELLS.NAME.getIndex()),
								cursorGroup.getInt(TABLE.CELLS.YEAR.getIndex())
					);
		}
		cursorGroup.close();
		
		return result;
	}

	public LinkedList<ObjectBase> getAllBaseFromNotGroupId(int groupId) {
		String[] selectionArgs = new String[] {Integer.toString(groupId)};
		DBHelper.getInstance(mCtx);
		Cursor cursorGroup = DBHelper.mDB.rawQuery("SELECT * FROM " + TABLE  
											+" WHERE _id NOT IN (" +
											"SELECT " + DBNames.TABLES.CLASS_OBJECT.CELLS.OBJECT_ID + " FROM " +
											DBNames.TABLES.CLASS_OBJECT + " WHERE " + DBNames.TABLES.CLASS_OBJECT.CELLS.CLASS_ID +
											" == ? )"
											, selectionArgs);
		
		LinkedList<ObjectBase> result = new LinkedList<ObjectBase>();
		
		if(cursorGroup.moveToFirst()) {
			do {
				result.add(new ObjectBase(
						cursorGroup.getInt(TABLE.CELLS.ID.getIndex()),
						cursorGroup.getString(TABLE.CELLS.NAME.getIndex()),
						cursorGroup.getInt(TABLE.CELLS.YEAR.getIndex())
						));
			} while (cursorGroup.moveToNext());
		}
		cursorGroup.close();
		
		Collections.sort(result, new Comparator<ObjectBase>() {

			@Override
			public int compare(ObjectBase lhs, ObjectBase rhs) {
				Integer secondYear = rhs.year;
				
				return secondYear.compareTo(lhs.year) != 0  ? secondYear.compareTo(lhs.year) 
														    : lhs.name.compareTo(rhs.name); 
			}
		});
		
		return result;
	}

	public void addObject(int groupId, int objectId) {
		DBHelper.mDB.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES ("+ groupId +", "+ objectId +");");
	}
}
