package com.ishvlad.android_cw3_5.helper;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ishvlad.android_cw3_5.layer.DBNames;

public class OpenDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "com.ishvlad.android_cw3_5";
	
	
	public OpenDBHelper(Context context) {
		super(context, "iTeacherDB.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d( TAG, "--- Create database ---" + db.getPath());
		
		db.execSQL("create table "+ DBNames.TABLES.CLASS + " ("
		      + DBNames.TABLES.CLASS.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			  + DBNames.TABLES.CLASS.CELLS.NAME + " TEXT NOT NULL,"
		      + DBNames.TABLES.CLASS.CELLS.YEAR + " INTEGER CHECK (" +
		      		DBNames.TABLES.CLASS.CELLS.YEAR + " > 2000 AND " +
		      		DBNames.TABLES.CLASS.CELLS.YEAR + " < 2100) NOT NULL"
		      + ");"
		      );
	      
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.OBJECT + " ("
	    	  + DBNames.TABLES.OBJECT.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    	  + DBNames.TABLES.OBJECT.CELLS.NAME + " TEXT NOT NULL,"
	    	  + DBNames.TABLES.OBJECT.CELLS.YEAR + " INTEGER CHECK (" +
	    	  		DBNames.TABLES.OBJECT.CELLS.YEAR + " > 2000 AND " +
	    	  		DBNames.TABLES.OBJECT.CELLS.YEAR + " < 2100) NOT NULL"
	    	  + ");"
	          );
	    
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.CLASS_OBJECT + " ("
	    		  + DBNames.TABLES.CLASS_OBJECT.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	    		  + DBNames.TABLES.CLASS_OBJECT.CELLS.CLASS_ID + " INTEGER," 
	    		  + DBNames.TABLES.CLASS_OBJECT.CELLS.OBJECT_ID + " INTEGER,"
		          + "FOREIGN KEY(" +
		           		DBNames.TABLES.CLASS_OBJECT.CELLS.CLASS_ID + ") REFERENCES " +
		           		DBNames.TABLES.CLASS + "(" + 
		           		DBNames.TABLES.CLASS.CELLS.ID +  "),"
		          + "FOREIGN KEY(" +
		          		DBNames.TABLES.CLASS_OBJECT.CELLS.OBJECT_ID + ") REFERENCES " +
		          		DBNames.TABLES.OBJECT + "(" +
		          		DBNames.TABLES.OBJECT.CELLS.ID + ")"
		          + ");"
		          );
	    
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.STUDENT + " ("
		      + DBNames.TABLES.STUDENT.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
		      + DBNames.TABLES.STUDENT.CELLS.NAME + " TEXT NOT NULL,"
		      + DBNames.TABLES.STUDENT.CELLS.CLASS_ID + " INTEGER,"
		      + "FOREIGN KEY(" +
		      		DBNames.TABLES.STUDENT.CELLS.CLASS_ID + ") REFERENCES " +
		      		DBNames.TABLES.CLASS + "(" +
		      		DBNames.TABLES.CLASS.CELLS.ID + ")"
		      + ");"
		      );
	    
	    db.execSQL("CREATE TABLE " + DBNames.TABLES.LECTURE + " ("
	    		+ DBNames.TABLES.LECTURE.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ DBNames.TABLES.LECTURE.CELLS.NAME + " TEXT NOT NULL,"
	    		+ DBNames.TABLES.LECTURE.CELLS.DATE  + " LONG NOT NULL,"
			    + DBNames.TABLES.LECTURE.CELLS.OBJECT_ID + " INTEGER,"
			    + "FOREIGN KEY(" +
			      	DBNames.TABLES.LECTURE.CELLS.OBJECT_ID + ") REFERENCES " +
			      	DBNames.TABLES.OBJECT + "(" +
			      	DBNames.TABLES.OBJECT.CELLS.ID + ")"
			    + ");"
			    );
	    
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.CLASS_LECTURE + " ("
	    		+ DBNames.TABLES.CLASS_LECTURE.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ DBNames.TABLES.CLASS_LECTURE.CELLS.DATE  + " LONG NOT NULL,"
		    	+ DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID + " INTEGER,"
			    + "FOREIGN KEY(" +
			    	DBNames.TABLES.CLASS_LECTURE.CELLS.LECTURE_ID + ") REFERENCES " +
			    	DBNames.TABLES.LECTURE + "(" +
			    	DBNames.TABLES.LECTURE.CELLS.ID + ")"  
			    + ");"
			    );
	    
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.STUDENT_LECTURE + " ("
	    		+ DBNames.TABLES.STUDENT_LECTURE.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ DBNames.TABLES.STUDENT_LECTURE.CELLS.COMMENT  + " TEXT NULL,"
		    	+ DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + " INTEGER,"
		    	+ DBNames.TABLES.STUDENT_LECTURE.CELLS.CLASS_LECTURE_ID + " INTEGER,"
			    + "FOREIGN KEY(" +
			    	DBNames.TABLES.STUDENT_LECTURE.CELLS.STUDENT_ID + ") REFERENCES " +
			    	DBNames.TABLES.STUDENT + "(" +
			    	DBNames.TABLES.STUDENT.CELLS.ID +	"),"
			    + "FOREIGN KEY(" +
			    	DBNames.TABLES.STUDENT_LECTURE.CELLS.CLASS_LECTURE_ID + ") REFERENCES " +
			    	DBNames.TABLES.CLASS_LECTURE + "(" +
			    	DBNames.TABLES.CLASS_LECTURE.CELLS.ID + ")"  
			    + ");"
			    );
	   
	    db.execSQL("INSERT INTO Class(name, year) VALUES ('group2010', 2010);");
	    db.execSQL("INSERT INTO Class(name, year) VALUES ('group2020', 2020);");
	    db.execSQL("INSERT INTO Class(name, year) VALUES ('group2030', 2030);");
	    db.execSQL("INSERT INTO Class(name, year) VALUES ('group2040', 2040);");
	   
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 1.1', 1);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 1.2', 1);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 1.3', 1);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 2.1', 2);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 2.2', 2);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 2.3', 2);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.1', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.2', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.3', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.1', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.2', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.3', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.4', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.5', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.6', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.7', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 3.8', 3);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 4.1', 4);");
	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Student 4.2', 4);");
	    
	    db.execSQL("INSERT INTO Object(name, year) VALUES ('OpenGL', 2010);");
	    db.execSQL("INSERT INTO Object(name, year) VALUES ('OpenCV', 2010);");
	    db.execSQL("INSERT INTO Object(name, year) VALUES ('DB', 2020);");
	    db.execSQL("INSERT INTO Object(name, year) VALUES ('Compilers', 2020);");
	    db.execSQL("INSERT INTO Object(name, year) VALUES ('Difuri', 2030);");
	    
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (1, 1);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (1, 2);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (1, 3);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (1, 4);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (1, 5);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (2, 3);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (2, 4);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (2, 5);");
	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (3, 5);");
	    
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in CV', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in CV', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in CV', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in GL', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in CV', "+ Long.toString(new Date().getTime()) + ", 1);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in CV', "+ Long.toString(new Date().getTime()) + ", 2);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in CV', "+ Long.toString(new Date().getTime()) + ", 2);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in CV', "+ Long.toString(new Date().getTime()) + ", 2);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in CV', "+ Long.toString(new Date().getTime()) + ", 2);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in DB', "+ Long.toString(new Date().getTime()) + ", 3);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in DB', "+ Long.toString(new Date().getTime()) + ", 3);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in DB', "+ Long.toString(new Date().getTime()) + ", 3);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in DB', "+ Long.toString(new Date().getTime()) + ", 3);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in CMPL', "+ Long.toString(new Date().getTime()) + ", 4);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in CMPL', "+ Long.toString(new Date().getTime()) + ", 4);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in CMPL', "+ Long.toString(new Date().getTime()) + ", 4);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in CMPL', "+ Long.toString(new Date().getTime()) + ", 4);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro in DIF', "+ Long.toString(new Date().getTime()) + ", 5);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro1 in DIF', "+ Long.toString(new Date().getTime()) + ", 5);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro2 in DIF', "+ Long.toString(new Date().getTime()) + ", 5);");
	    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Intro3 in DIF', "+ Long.toString(new Date().getTime()) + ", 5);");
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
//	    db.execSQL("INSERT INTO Class(name, year) VALUES ('ИУ9-61', 2011);");
//	   
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Ашуха Арсений', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Батусов Павел', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Белёв Константин', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Вирцева Наталья', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Волосникова Марина', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Волхонский Денис', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Гришин Денис', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Ишимцев Влад', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Мансурова Милена', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Петкевич Денис', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Сафонова Александра', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Синицын Егор', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Сорокина Софья', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Федорчук Стасян', 5);");
//	    db.execSQL("INSERT INTO Student(name, class_id) VALUES ('Цветков Владимир', 5);");
//	    
//	    
//	    db.execSQL("INSERT INTO Object(name, year) VALUES ('Алгоритмы компьютерной графики', 2013);");
//	    db.execSQL("INSERT INTO ClassObject(class_id, object_id) VALUES (5, 6);");
//	    
//	    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
//	    try {
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Введение', "+ Long.toString(df.parse("13.02.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Программные срадства МГ', "+ Long.toString(df.parse("20.02.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Математические основы МГ', "+ Long.toString(df.parse("27.02.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Геометрическое моделирование', "+ Long.toString(df.parse("06.03.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Полигональные сетки', "+ Long.toString(df.parse("13.03.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Аппроксимация', "+ Long.toString(df.parse("20.03.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Алгоритмы растровой графики', "+ Long.toString(df.parse("03.04.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Алгоритмы отсечения', "+ Long.toString(df.parse("10.04.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Алгоритмы удаления часть 1', "+ Long.toString(df.parse("17.04.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Алгоритмы удаления часть 2', "+ Long.toString(df.parse("24.04.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Реалистические изображения часть 1', "+ Long.toString(df.parse("08.05.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Реалистические изображения часть 2', "+ Long.toString(df.parse("15.05.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Оптимизация приложений OpenGL', "+ Long.toString(df.parse("22.05.2013").getTime()) + ", 6);");
//		    db.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('Шейдеры', "+ Long.toString(df.parse("29.05.2013").getTime()) + ", 6);");
//
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("13.02.2013").getTime()) + ", 5, 21);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("20.02.2013").getTime()) + ", 5, 22);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("27.02.2013").getTime()) + ", 5, 23);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("06.03.2013").getTime()) + ", 5, 24);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("13.03.2013").getTime()) + ", 5, 25);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("20.03.2013").getTime()) + ", 5, 26);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("03.04.2013").getTime()) + ", 5, 27);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("10.04.2013").getTime()) + ", 5, 28);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("17.04.2013").getTime()) + ", 5, 29);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("24.04.2013").getTime()) + ", 5, 30);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("08.05.2013").getTime()) + ", 5, 31);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("15.05.2013").getTime()) + ", 5, 32);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("22.05.2013").getTime()) + ", 5, 33);");
//		    db.execSQL("INSERT INTO ClassLecture(date, class_id, lecture_id) VALUES ("+ Long.toString(df.parse("29.05.2013").getTime()) + ", 5, 34);");
//		    
//		    
//	    } catch(ParseException e) {
//	    	e.printStackTrace();
//	    }
//	    
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,21);");
//	    db.execSQL("INSERT INTO StudentLecture(comment, student_id, lecture_id) VALUES ('Опоздал', 19,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,21);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,21);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,22);");
//	    db.execSQL("INSERT INTO StudentLecture(comment, student_id, lecture_id) VALUES ('Опоздал', 19,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,22);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,22);");
//
//	    
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,23);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,23);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,24);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,24);");
//
//	    db.execSQL("INSERT INTO StudentLecture(comment, student_id, lecture_id) VALUES ('Опоздал', 13,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,25);");
//	    db.execSQL("INSERT INTO StudentLecture(comment, student_id, lecture_id) VALUES ('Опоздал', 19,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,25);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,25);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,26);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,26);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,27);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,27);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,28);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,28);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,29);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,29);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,30);");
//	    db.execSQL("INSERT INTO StudentLecture(comment, student_id, lecture_id) VALUES ('Опоздал', 21,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,30);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,30);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,31);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,31);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,32);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,32);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,33);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,33);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,34);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,34);");
//
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (13,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (14,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (15,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (16,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (17,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (18,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (19,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (20,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (21,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (22,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (23,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (24,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (25,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (26,35);");
//	    db.execSQL("INSERT INTO StudentLecture(student_id, lecture_id) VALUES (27,35);");
	    
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
	
	
}
