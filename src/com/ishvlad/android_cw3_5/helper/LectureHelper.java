package com.ishvlad.android_cw3_5.helper;

import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Lecture;
import com.ishvlad.android_cw3_5.layer.LectureBase;
import com.ishvlad.android_cw3_5.layer.LectureView;

public class LectureHelper  {
	private final Lecture TABLE = DBNames.TABLES.LECTURE;
	private Context mCtx;
	
	public LectureHelper(Context context) {
		mCtx = context;
	}
	
	private LinkedList<LectureBase> unpack(Cursor cursor) {
		LinkedList<LectureBase> lectures = new LinkedList<LectureBase>();
		if(cursor.moveToFirst()) {
			do {
				lectures.add(new LectureBase(
						cursor.getInt(TABLE.CELLS.ID.getIndex()),
						cursor.getString(TABLE.CELLS.NAME.getIndex()),
						cursor.getLong(TABLE.CELLS.DATE.getIndex())
						));
			} while (cursor.moveToNext());
		}
		cursor.close();
		
		return lectures;
	}

	public LinkedList<LectureBase> getUnreadLectures(int groupId, int objectId) {
		String[] selectionArgs = new String[] {Integer.toString(objectId), Integer.toString(groupId)};
		
		DBHelper.getInstance(mCtx);
		Cursor cursorLect = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + TABLE + 
				" WHERE (" + TABLE.CELLS.OBJECT_ID + " == ? AND " + TABLE.CELLS.ID + " NOT IN (" +
						" SELECT " + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID + " AS " + TABLE.CELLS.ID +
						" FROM " + DBNames.TABLES.CLASS_LECTURE +
						" WHERE " + DBNames.TABLES.CLASS_LECTURE.CELLS.CLASS_ID + " == ?))", selectionArgs
				);
		
		return unpack(cursorLect);
	}
	
	public LinkedList<LectureBase> getLectures(int objectId) {
		String[] selectionArgs = new String[] {Integer.toString(objectId)};
		
		DBHelper.getInstance(mCtx);
		Cursor cursorLect = DBHelper.mDB.rawQuery(
				"SELECT * FROM " + TABLE + 
				" WHERE " + TABLE.CELLS.OBJECT_ID + " == ?", selectionArgs
				);
		
		return unpack(cursorLect);
	}
	
	public LinkedList<LectureView> getLectures(int objectId, int studentId) {
		
		LinkedList<LectureBase> lectures = getLectures(objectId);
		
		LinkedList<LectureView> result = new LinkedList<LectureView>();
		
		for(LectureBase item:lectures) {
			LectureView lect = getLecture(item.lectureId, studentId);
			if (lect != null) {
				result.add(lect);
			}
		}
		
		return result;
		
	} 
	
	public int size(int objectId) {
		String[] selectionArgs = new String[] {Integer.toString(objectId)};
		
		
		DBHelper.getInstance(mCtx);
		Cursor cursorLect = DBHelper.mDB.rawQuery(
				"SELECT COUNT(*) FROM " + TABLE + 
				" WHERE " + TABLE.CELLS.OBJECT_ID + " == ?"
				, selectionArgs
				);
		
		cursorLect.moveToFirst();
		return cursorLect.getInt(0);
	}

	
	public LectureView getLecture(int lectureId, int studentId) {
		String[] selectionArgs = new String[] {Integer.toString(lectureId), Integer.toString(studentId)};
		
		DBHelper.getInstance(mCtx);
		Cursor cursorLect = DBHelper.mDB.rawQuery(
				"SELECT  " + 	" l." + TABLE.CELLS.ID +
								" ,l." + TABLE.CELLS.NAME +
								" ,sl."+ DBNames.TABLES.STUDENT_LECTURE.CELLS.COMMENT +
								" ,cl."+ DBNames.TABLES.CLASS_LECTURE.CELLS.DATE +
								" ,cl."+ DBNames.TABLES.CLASS_LECTURE.CELLS.ID +
								" ,sl."+ DBNames.TABLES.STUDENT_LECTURE.CELLS.ID +
				" FROM  (" + 
						TABLE + " AS l " +
						" INNER JOIN " + DBNames.TABLES.CLASS_LECTURE + " AS cl " +
							" ON l." + TABLE.CELLS.ID + 
							" == " +
							" cl." + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID +
				" ) " +
				" INNER JOIN " +  DBNames.TABLES.STUDENT_LECTURE + " AS sl "  +
						" ON sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.LECTURE_ID + 
						" == " +
						" cl." + DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID +
				" WHERE ( sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.LECTURE_ID + " == ? AND " +
						"sl." + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + " == ?);"
				, selectionArgs
				);
		
		LectureView result = null; 
		if(cursorLect.moveToFirst()) {
			result = new LectureView(
					cursorLect.getInt(0),
					cursorLect.getString(1),
					cursorLect.getString(2),
					cursorLect.getLong(3),
					cursorLect.getInt(4),
					cursorLect.getInt(5)
					);
		}
		return result;
	}

	public void update(LectureView lecture, int studentId) {
		DBHelper.mDB.execSQL(
				"UPDATE " + TABLE +
				" SET " + TABLE.CELLS.NAME + " = \"" + lecture.name +
				"\" WHERE " + DBNames.TABLES.LECTURE.CELLS.ID + " == " + Integer.toString(lecture.lectureId)
		);
		
		DBHelper.mDB.execSQL(
				"UPDATE " + DBNames.TABLES.STUDENT_LECTURE +
				" SET " + DBNames.TABLES.STUDENT_LECTURE.CELLS.COMMENT + " = \"" + (lecture.comment == null ? "":lecture.comment) + 
				"\" WHERE " + DBNames.TABLES.STUDENT_LECTURE.CELLS.ID + " == " + Integer.toString(lecture.studentLectureId) 
		);
		
		DBHelper.mDB.execSQL(
				"UPDATE " + DBNames.TABLES.CLASS_LECTURE +
				" SET " + DBNames.TABLES.CLASS_LECTURE.CELLS.DATE + " = \"" + Long.toString(lecture.date) + 
				"\" WHERE " + DBNames.TABLES.CLASS_LECTURE.CELLS.ID + " == " + Integer.toString(lecture.classLectureId) 
		);
	}
	
}
