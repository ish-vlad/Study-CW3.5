package com.ishvlad.android_cw3_5.layer;

import java.util.ArrayList;

import android.database.Cursor;

import com.ishvlad.android_cw3_5.helper.DBHelper;
import com.ishvlad.android_cw3_5.helper.ObjectHelper;
import com.ishvlad.android_cw3_5.layer.NewObject.Lab.Variant;

public class NewObject {
	public String name;
	public int year;
	public ArrayList<Lecture> lectures = new ArrayList<Lecture>();
	public ArrayList<Lab> labs = new ArrayList<Lab>();
	
	public class Lecture {
		public String name;
		public long date;
	}
	public class Lab {
		public String name;
		public long date;
		public int min, max;
		public ArrayList<Variant> variants = new ArrayList<Variant>();
		
		public class Variant {
			public String name;
			public int number;
		}
	}
	
	public String save(int groupId) {
		DBHelper dbh = null;
		try {
			dbh = DBHelper.getInstance(null);
		} catch (NullPointerException e) {
			return "null!!";
		}
		
		dbh.mDB.execSQL("INSERT INTO Object(name, year) VALUES ('"+ name +"', " + Integer.toString(year) + ");");
		
		Cursor cursor = dbh.getAll(DBNames.TABLES.OBJECT.toString());
		String objectId = null;
		if(cursor.moveToLast()) {
			objectId = Integer.toString(cursor.getInt(DBNames.TABLES.OBJECT.CELLS.ID.getIndex()));
		}
		cursor.close();
		
		for (Lecture item:lectures) {
			dbh.mDB.execSQL("INSERT INTO Lecture(name, date, object_id) VALUES ('" + item.name + "', " 
																				   + item.date + " , "
																				   + objectId  + "); ");
		}
		for (Lab item:labs) {
			dbh.mDB.execSQL("INSERT INTO Lab(name, date, min, max, object_id) VALUES ('" + item.name + "', " 
																						  + item.date + " , "
																						  + item.min  + " , "
																						  + item.max  + " , "
																						  + objectId  + ");");
			if (item.variants.size() > 0) {
				cursor = dbh.getAll(DBNames.TABLES.LAB.toString());
				String labId = null;
				if(cursor.moveToLast()) {
					labId = Integer.toString(cursor.getInt(DBNames.TABLES.LAB.CELLS.ID.getIndex()));
				}
				cursor.close();
				
				for(Variant var:item.variants) {
					dbh.mDB.execSQL("INSERT INTO Variant(name, number, table_owner, owner_id) VALUES ('" 
							 + var.name  + "', " 
						     + var.number+ " , "
						     + "Lab"     + " , "
						     + labId     + ");");
				}
			}
		}
		new ObjectHelper(null).addObject(groupId, Integer.parseInt(objectId));
		return "Complete!";
	}
}
