package com.ishvlad.android_cw3_5.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.Base;
import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Lecture;

public class LectureHelper extends DataHelper {
	private final Lecture TABLE = DBNames.TABLES.LECTURE;
	
	public LectureHelper(Context context) {
		DBHelper.getInstance(context);
	}
	
	private void updateDate(Base lecture) throws ParseException {
		DBHelper.mDB.execSQL(
				"UPDATE " + DBNames.TABLES.CLASS_LECTURE +
				" SET " + DBNames.TABLES.CLASS_LECTURE.CELLS.DATE + " = \"" + Long.toString(new SimpleDateFormat("dd.MM.yyyy").parse(lecture.date).getTime()) + 
				"\" WHERE " + DBNames.TABLES.CLASS_LECTURE.CELLS.ID + " == " + Integer.toString(lecture.dateFromId) 
			);
	}
	
	private void updateComment(Base lecture) {
		DBHelper.mDB.execSQL(
				"UPDATE " + DBNames.TABLES.STUDENT_LECTURE +
				" SET " + DBNames.TABLES.STUDENT_LECTURE.CELLS.COMMENT + " = \"" + (lecture.other) + 
				"\" WHERE " + DBNames.TABLES.STUDENT_LECTURE.CELLS.ID + " == " + Integer.toString(lecture.otherId) 
		);
	}
	
	@Override
	public Base upgradeBase(Base lecture, int studentId) {
		String[] selectionArgs; 
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		selectionArgs = new String[] {Integer.toString(lecture.id), Integer.toString(studentId)};
		Cursor cursor = DBHelper.mDB.rawQuery(
					"SELECT  " + 	"  sl."+ DBNames.TABLES.STUDENT_LECTURE.CELLS.COMMENT +
									" ,cl."+ DBNames.TABLES.CLASS_LECTURE.CELLS.DATE +
									" ,cl."+ DBNames.TABLES.CLASS_LECTURE.CELLS.ID +
									" ,sl."+ DBNames.TABLES.STUDENT_LECTURE.CELLS.ID +
					" FROM " + DBNames.TABLES.STUDENT_LECTURE  + " AS sl " +
							" INNER JOIN " + DBNames.TABLES.CLASS_LECTURE + " AS cl " +
								" ON sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.CLASS_LECTURE_ID + 
								" == " +
								" cl." + DBNames.TABLES.CLASS_LECTURE.CELLS.ID +
					" WHERE ( cl." + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID + " == ? AND " +
							"sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + " == ?);"
				, selectionArgs
		);
			
		if(cursor.moveToFirst()) {
			lecture.other = cursor.getString(0);
			lecture.date = df.format(new Date(cursor.getLong(1)));
			lecture.dateFromId = cursor.getInt(2);
			lecture.otherId = cursor.getInt(3);
		} else {
			lecture.other = "";
			lecture.date = "";
			lecture.otherId = 0;
			lecture.dateFromId = 0;
		}
		cursor.close();
		
		return lecture;
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
		int cls_id = 0;
		
		String[] selectionArgs = new String[] {Integer.toString(whereId), Integer.toString(groupId)};
		Cursor cursor = DBHelper.mDB.rawQuery(
				"SELECT  " + 	"  cl."+ DBNames.TABLES.CLASS_LECTURE.CELLS.ID +
								" ,sl."+ DBNames.TABLES.STUDENT_LECTURE.CELLS.ID +
								" ,cl."+ DBNames.TABLES.CLASS_LECTURE.CELLS.DATE +
				" FROM " + DBNames.TABLES.STUDENT_LECTURE  + " AS sl " +
						" INNER JOIN " + DBNames.TABLES.CLASS_LECTURE + " AS cl " +
							" ON sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.CLASS_LECTURE_ID + 
							" == " +
							" cl." + DBNames.TABLES.CLASS_LECTURE.CELLS.ID +
				" WHERE ( cl." + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID + " == ? AND " +
						"sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + " IN (" +
								"SELECT " + DBNames.TABLES.STUDENT.CELLS.ID + " FROM " + DBNames.TABLES.STUDENT +
								" WHERE " + DBNames.TABLES.STUDENT.CELLS.CLASS_ID + " == ? " +
				"));"
				, selectionArgs
				);
	
		if(cursor.moveToFirst()) {
			cls_id = cursor.getInt(0);
			student.date = new SimpleDateFormat("dd.MM.yyyy").format(cursor.getLong(2));
		} else {
			student.date = new SimpleDateFormat("dd.MM.yyyy").format(new Date().getTime());
			
			DBHelper.mDB.execSQL(
					"INSERT INTO " + DBNames.TABLES.CLASS_LECTURE + 
					"(" + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID +
					"," + DBNames.TABLES.CLASS_LECTURE.CELLS.DATE +
					" )VALUES(" 
					+ Integer.toString(whereId) + ", "  
					+ Long.toString(new Date().getTime()) + 
					");"
			);
			
			Cursor cursorNew = DBHelper.mDB.rawQuery(
					"SELECT * FROM " + DBNames.TABLES.CLASS_LECTURE  + ";"
					, null
					);
			cls_id = 1;
			if(cursorNew.moveToLast()) {
				cls_id = cursorNew.getInt(DBNames.TABLES.CLASS_LECTURE.CELLS.ID.getIndex());
			}
			cursorNew.close();
			
		}
		cursor.close();
		
		DBHelper.mDB.execSQL(
			"INSERT INTO " + DBNames.TABLES.STUDENT_LECTURE + 
			"(" + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID +
			"," + DBNames.TABLES.STUDENT_LECTURE.CELLS.CLASS_LECTURE_ID +
			" )VALUES(" 
			+ Integer.toString(student.id) + ", "  
			+ Integer.toString(cls_id) + 
			");"
		);
			
		Cursor cursorNew = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + DBNames.TABLES.STUDENT_LECTURE  + ";"
				, null
				);
		int std_lec_id = 0;
		if(cursorNew.moveToLast()) {
			std_lec_id = cursorNew.getInt(DBNames.TABLES.STUDENT_LECTURE.CELLS.ID.getIndex());
		}
		cursorNew.close();
		
		student.dateFromId = cls_id;
		student.otherId = std_lec_id;
	}

	@Override
	public void update(Base itemNew, Base itemOld) throws ParseException {
		if (!itemNew.date.equals(itemOld.date)) {
			try {
				updateDate(itemNew);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
		if (!itemNew.other.equals(itemOld.other)) {
			updateComment(itemNew);
		}
	}

	@Override
	public void delete(Base item) {
		DBHelper.mDB.execSQL(
				"DELETE FROM " + DBNames.TABLES.STUDENT_LECTURE + 
				" WHERE " + DBNames.TABLES.STUDENT_LECTURE.CELLS.ID + " == "  + Integer.toString(item.otherId)
		);
		
		String[] selectionArgs = new String[] {Integer.toString(item.dateFromId)};
		Cursor cursor = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + DBNames.TABLES.STUDENT_LECTURE +
				" WHERE " + DBNames.TABLES.STUDENT_LECTURE.CELLS.CLASS_LECTURE_ID + " == ? ", selectionArgs
		);
		
		if(!cursor.moveToFirst()) {
			DBHelper.mDB.execSQL(
					"DELETE FROM " + DBNames.TABLES.CLASS_LECTURE + 
					" WHERE " + DBNames.TABLES.CLASS_LECTURE.CELLS.ID + " == "  + Integer.toString(item.dateFromId)
			);	
		}
		cursor.close();
	}

	@Override
	public Base upgradeStudent(Base student, int whatId) {
		int buf = student.id;
		student.id = whatId;
		
		student = upgradeBase(student, buf);
		student.id = buf;
		
		return student;
	}
}
