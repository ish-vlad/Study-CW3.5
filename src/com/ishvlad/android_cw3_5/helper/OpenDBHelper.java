package com.ishvlad.android_cw3_5.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ishvlad.android_cw3_5.layer.DBNames;

public class OpenDBHelper extends SQLiteOpenHelper {
	
	
	public OpenDBHelper(Context context) {
		super(context, "iTeacherDB.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("create table "+ DBNames.TABLES.CLASS + " ("
		      + DBNames.TABLES.CLASS.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			  + DBNames.TABLES.CLASS.CELLS.NAME + " TEXT NOT NULL,"
		      + DBNames.TABLES.CLASS.CELLS.YEAR + " INTEGER NOT NULL"
		      + ");"
		      );
	      
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.OBJECT + " ("
	    	  + DBNames.TABLES.OBJECT.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    	  + DBNames.TABLES.OBJECT.CELLS.NAME + " TEXT NOT NULL,"
	    	  + DBNames.TABLES.OBJECT.CELLS.YEAR + " INTEGER NOT NULL"
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
	    
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.LAB + " ("
	    		+ DBNames.TABLES.LAB.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ DBNames.TABLES.LAB.CELLS.NAME  + " TEXT NOT NULL,"
		    	+ DBNames.TABLES.LAB.CELLS.DATE + " LONG NOT NULL,"
		    	+ DBNames.TABLES.LAB.CELLS.MIN + " INTEGER NOT NULL,"
		    	+ DBNames.TABLES.LAB.CELLS.MAX + " INTEGER NOT NULL,"
		    	+ DBNames.TABLES.LAB.CELLS.OBJECT_ID + " INTEGER,"
			    + "FOREIGN KEY(" +
			    	DBNames.TABLES.LAB.CELLS.OBJECT_ID + ") REFERENCES " +
			    	DBNames.TABLES.OBJECT + "(" +
			    	DBNames.TABLES.OBJECT.CELLS.ID + ")"  
			    + ");"
			    );

	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.VARIANT + " ("
	    		+ DBNames.TABLES.VARIANT.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ DBNames.TABLES.VARIANT.CELLS.NAME  + " TEXT NULL,"
		    	+ DBNames.TABLES.VARIANT.CELLS.NUMBER + " INTEGER NOT NULL,"
		    	+ DBNames.TABLES.VARIANT.CELLS.TABLE_OWNER  + " TEXT NOT NULL,"
		    	+ DBNames.TABLES.VARIANT.CELLS.OWNER_ID + " INTEGER NOT NULL" 
			    + ");"
			    );
	    
	    db.execSQL("CREATE TABLE "+ DBNames.TABLES.STUDENT_LAB + " ("
	    		+ DBNames.TABLES.STUDENT_LAB.CELLS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ DBNames.TABLES.STUDENT_LAB.CELLS.MARK  + " INTEGER NULL,"
		    	+ DBNames.TABLES.STUDENT_LAB.CELLS.DATE_START + " LONG NOT NULL,"
		    	+ DBNames.TABLES.STUDENT_LAB.CELLS.STUDENT_ID + " INTEGER,"
		    	+ DBNames.TABLES.STUDENT_LAB.CELLS.VARIANT_ID + " INTEGER,"
			    + "FOREIGN KEY(" +
			    	DBNames.TABLES.STUDENT_LAB.CELLS.STUDENT_ID + ") REFERENCES " +
			    	DBNames.TABLES.STUDENT + "(" +
			    	DBNames.TABLES.STUDENT.CELLS.ID +	"),"
			    + "FOREIGN KEY(" +
			    	DBNames.TABLES.STUDENT_LAB.CELLS.VARIANT_ID + ") REFERENCES " +
			    	DBNames.TABLES.VARIANT + "(" +
			    	DBNames.TABLES.VARIANT.CELLS.ID + ")"  
			    + ");"
			    );
	   
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
	
	
}
