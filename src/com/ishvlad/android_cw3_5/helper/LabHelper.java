package com.ishvlad.android_cw3_5.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.Base;
import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Lab;

public class LabHelper extends DataHelper {
private final Lab TABLE = DBNames.TABLES.LAB;
	
	public LabHelper(Context context) {
		DBHelper.getInstance(context);
	}

	@Override
	public Base upgradeBase(Base lab, int studentId) {
		String[] selectionArgs; 
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		selectionArgs = new String[] {Integer.toString(lab.id), Integer.toString(studentId)};
		Cursor cursor = DBHelper.mDB.rawQuery(
					"SELECT  " + 	"  v."+ DBNames.TABLES.VARIANT.CELLS.NUMBER +
									" ,sl."+ DBNames.TABLES.STUDENT_LAB.CELLS.DATE_START +
									" ,sl."+ DBNames.TABLES.STUDENT_LAB.CELLS.ID +
									" ,v."+ DBNames.TABLES.VARIANT.CELLS.ID +
									" ,sl."+ DBNames.TABLES.STUDENT_LAB.CELLS.MARK +
					" FROM " + DBNames.TABLES.STUDENT_LAB  + " AS sl " +
							" INNER JOIN " + DBNames.TABLES.VARIANT + " AS v " +
								" ON sl." + DBNames.TABLES.STUDENT_LAB.CELLS.VARIANT_ID + 
								" == " +
								" v." + DBNames.TABLES.VARIANT.CELLS.ID +
					" WHERE ( v." + DBNames.TABLES.VARIANT.CELLS.TABLE_OWNER + " == '" + TABLE  +"' AND " +
							" v." + DBNames.TABLES.VARIANT.CELLS.OWNER_ID + " == ? AND " +
							"sl." + DBNames.TABLES.STUDENT_LAB.CELLS.STUDENT_ID + " == ?);"
				, selectionArgs
		);
			
		if(cursor.moveToFirst()) {
			lab.other = Integer.toString(cursor.getInt(0));
			lab.date = df.format(new Date(cursor.getLong(1)));
			lab.dateFromId = cursor.getInt(2);
			lab.otherId = cursor.getInt(3);
			lab.mark = cursor.getInt(4);
		} else {
			lab.other = "";
			lab.date = "";
			lab.otherId = 0;
			lab.dateFromId = 0;
			lab.mark = -1;
		}
		cursor.close();
		
		return lab;
	}
	
	@Override
	public LinkedList<Base> getAllBase(int collectionId) {
		String[] selectionArgs = new String[] {Integer.toString(collectionId)};
		
		Cursor cursor = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + TABLE + 
				" WHERE " + TABLE.CELLS.OBJECT_ID + " == ?", selectionArgs
				);
		
		LinkedList<Base> lectures = new LinkedList<Base>();
		if(cursor.moveToFirst()) {
			do {
				lectures.add(new Base(
						cursor.getInt(TABLE.CELLS.ID.getIndex()),
						cursor.getString(TABLE.CELLS.NAME.getIndex()),
						cursor.getLong(TABLE.CELLS.DATE.getIndex())
						));
			} while (cursor.moveToNext());
		}
		cursor.close();
		
		return lectures;
	}
	
	@Override
	public void insert(Base student, int whereId, int groupId) {
		if (student.otherId == 0) {
			Cursor cursor = DBHelper.mDB.rawQuery(
					"SELECT * FROM " + DBNames.TABLES.VARIANT  
					+ " WHERE "+ DBNames.TABLES.VARIANT.CELLS.TABLE_OWNER + " == '" + TABLE +"' AND " 
					+ DBNames.TABLES.VARIANT.CELLS.OWNER_ID + " == " + Integer.toString(whereId)
					, null
			);
			cursor.moveToFirst();
			student.otherId = cursor.getInt(DBNames.TABLES.VARIANT.CELLS.ID.getIndex());
			student.other = Integer.toString(cursor.getInt(DBNames.TABLES.VARIANT.CELLS.NUMBER.getIndex()));
			cursor.close();
		}
		
		student.date = new SimpleDateFormat("dd.MM.yyyy").format(new Date().getTime());
			
		DBHelper.mDB.execSQL(
					"INSERT INTO " + DBNames.TABLES.STUDENT_LAB + 
						"(" + DBNames.TABLES.STUDENT_LAB.CELLS.DATE_START +
						"," + DBNames.TABLES.STUDENT_LAB.CELLS.STUDENT_ID +
						"," + DBNames.TABLES.STUDENT_LAB.CELLS.VARIANT_ID +
					" )VALUES("  
						+ Long.toString(new Date().getTime()) + ", "
						+ Integer.toString(student.id) + ", "
						+ Long.toString(student.otherId) +
					");"
		);
			
		Cursor cursor = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + DBNames.TABLES.STUDENT_LAB  
				+ " WHERE "+ DBNames.TABLES.STUDENT_LAB.CELLS.STUDENT_ID + " == " + Integer.toString(student.id)
				, null
		);
		cursor.moveToLast();
		student.dateFromId = cursor.getInt(DBNames.TABLES.STUDENT_LAB.CELLS.ID.getIndex());
		cursor.close();
		
	}

	@Override
	public void update(Base itemNew, Base itemOld) throws ParseException {
		if (itemNew.mark != itemOld.mark) {
			DBHelper.mDB.execSQL(
					"UPDATE " + DBNames.TABLES.STUDENT_LAB +
					" SET " + DBNames.TABLES.STUDENT_LAB.CELLS.MARK + " = " + Integer.toString(itemNew.mark) + 
					" WHERE " + DBNames.TABLES.STUDENT_LAB.CELLS.ID + " == " + Long.toString(itemNew.dateFromId) 
			);
		}
	}

	@Override
	public void delete(Base item) {
		DBHelper.mDB.execSQL(
				"DELETE FROM " + DBNames.TABLES.STUDENT_LAB + 
				" WHERE " + DBNames.TABLES.STUDENT_LAB.CELLS.ID + " == "  + Long.toString(item.dateFromId)
		);
	}

	@Override
	public Base upgradeStudent(Base student, int whatId) {
		int buf = student.id;
		student.id = whatId;
		
		student = upgradeBase(student, buf);
		student.id = buf;
		
		return student;
	}
	
	public boolean canStartAll(int labId) {
		Cursor cursor = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + DBNames.TABLES.VARIANT  
				+ " WHERE "+ DBNames.TABLES.VARIANT.CELLS.TABLE_OWNER + " == '" + TABLE +"' AND " 
				+ DBNames.TABLES.VARIANT.CELLS.OWNER_ID + " == " + Integer.toString(labId)
				, null
		);
		int size = cursor.getCount();
		cursor.close();
		
		switch (size) {
		case 0:
			DBHelper.mDB.execSQL(
					"INSERT INTO " + DBNames.TABLES.VARIANT + 
						"(" + DBNames.TABLES.VARIANT.CELLS.NUMBER +
						"," + DBNames.TABLES.VARIANT.CELLS.TABLE_OWNER +
						"," + DBNames.TABLES.VARIANT.CELLS.OWNER_ID +
					" )VALUES(" 
						+ Integer.toString(-1) + ", "  
						+ "'" + TABLE + "'" + ", "
						+ Integer.toString(labId) + 
					");"
			);
		case 1:
			return true;
		}
		return false;
	}
	
	public LinkedList<String> getMarks(int labId) {
		LinkedList<String> result = new LinkedList<String>();
		
		Cursor cursor = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + TABLE  
				+ " WHERE " +  TABLE.CELLS.ID + " == "  + Integer.toString(labId)
				, null
		);
		cursor.moveToFirst();
		int min = cursor.getInt(TABLE.CELLS.MIN.getIndex());
		int max = cursor.getInt(TABLE.CELLS.MAX.getIndex());
		for(int i = min; i <= max; i++) {
			result.add(Integer.toString(i));
		}
		cursor.close();
		return result;
	}
}
