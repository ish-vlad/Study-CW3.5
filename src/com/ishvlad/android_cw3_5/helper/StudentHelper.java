package com.ishvlad.android_cw3_5.helper;

import java.util.Date;

import android.content.Context;

import com.ishvlad.android_cw3_5.layer.DBNames;
import com.ishvlad.android_cw3_5.layer.DBNames.Tables.Student;
import com.ishvlad.android_cw3_5.layer.StudentBase;
import com.ishvlad.android_cw3_5.layer.StudentFull;

public class StudentHelper {
	private final Student TABLE = DBNames.TABLES.STUDENT;
	private Context mCtx;
	
	public StudentHelper(Context context) {
		mCtx = context;
	}
	
	public void setPresence(int studentId, int lectureId) {
		DBHelper.getInstance(mCtx);
		DBHelper.mDB.execSQL(
				"INSERT INTO " + DBNames.TABLES.STUDENT_LECTURE + 
				"(" + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID +   
				"," + DBNames.TABLES.STUDENT_LECTURE.CELLS.LECTURE_ID +
				" )VALUES(" 
				+ Integer.toString(studentId) + ", "  
				+ Integer.toString(lectureId) + 
				");"
		);
		
	}

	
	
	public StudentFull getFull(StudentBase studentBase, int objectId) {
		StudentFull student = new StudentFull(studentBase.studentId, studentBase.name);
		
		updateLectures(student, objectId);
		
		return student;
	}
	
	public void updateLectures(StudentFull student,int objectId) {
		LectureHelper lectureHelper = new LectureHelper(mCtx);
		int allCount = lectureHelper.size(objectId);
		
		
		student.lectures = lectureHelper.getLectures(objectId, student.studentId);
		student.procentLectures = allCount == 0 ? 0 : student.lectures.size() * 100 / allCount ;
	}

	public void deletePresence(int lectureId, int studentId) {
		DBHelper.getInstance(mCtx);
		DBHelper.mDB.execSQL(
				"DELETE FROM " + DBNames.TABLES.STUDENT_LECTURE + 
				" WHERE (" + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + " == "  + Integer.toString(studentId) +  
				" AND " + DBNames.TABLES.STUDENT_LECTURE.CELLS.LECTURE_ID + " == " + Integer.toString(lectureId) + 
				");"
		);
	}
	
	public void setComment(int studentId, int lectureId, String comment) {
		DBHelper.mDB.execSQL(
				"UPDATE " + DBNames.TABLES.STUDENT_LECTURE +
				" SET " + DBNames.TABLES.STUDENT_LECTURE.CELLS.COMMENT + " = \"" + comment + 
				"\" WHERE (" + DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + " == "  + Integer.toString(studentId) +  
				" AND " + DBNames.TABLES.STUDENT_LECTURE.CELLS.LECTURE_ID + " == " + Integer.toString(lectureId) + " );"
		);
	}
	
	
}
