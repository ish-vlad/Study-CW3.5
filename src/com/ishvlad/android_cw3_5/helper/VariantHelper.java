package com.ishvlad.android_cw3_5.helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.layer.DBNames;

public class VariantHelper {
	public static String ID = "variant_ID";
	public static String NUMBER = "variant_NUM";
	private int position;
	
	private String[] tables = {
			"",
			DBNames.TABLES.LAB.toString(),
			"",
			"",
			""
	};
	
	private int[] resources = {
			-1,
			R.layout.item_variant_lab,
			-1,
			-1,
			-1
	};
	
	private String[][] from = new String[][] {
		new String[] {},
		new String[] {NUMBER, ID, "lab_name", "lab_other"},
		new String[] {},
		new String[] {},
		new String[] {},
	};
	
	private int[][] to = {
			{},
			{R.id.item_var_lab_num, R.id.item_var_lab_id, R.id.item_var_lab_name, R.id.item_var_lab_other},
			{},
			{},
			{}
	};
	
	public VariantHelper(Context context, int modeIndex) {
		position = modeIndex;
		DBHelper.getInstance(context);
	}

	public LinkedList<HashMap<String, String>> getData(int whatId, int groupId) {
		LinkedList<HashMap<String, String>> mData = new LinkedList<HashMap<String, String>>();
		
		String[] selectionArgs = new String[] { Integer.toString(whatId)};
		Cursor cursorVar = DBHelper.mDB.rawQuery(
					"SELECT * FROM " + DBNames.TABLES.VARIANT +
					" WHERE " + DBNames.TABLES.VARIANT.CELLS.TABLE_OWNER + " == '" + tables[position] +"' AND " 
							  + DBNames.TABLES.VARIANT.CELLS.OWNER_ID + " == ?;"
				, selectionArgs
		);
			
		
		
		if(cursorVar.moveToFirst()) {
			do {
				HashMap<String, String> m = new HashMap<String, String>();
				
				switch (position) {
				case 1:
					String varId = Long.toString(cursorVar.getLong(DBNames.TABLES.VARIANT.CELLS.ID.getIndex()));
					m.put(from[position][0], Integer.toString(cursorVar.getInt(DBNames.TABLES.VARIANT.CELLS.NUMBER.getIndex())));
					m.put(from[position][1], varId);
					m.put(from[position][2], cursorVar.getString(DBNames.TABLES.VARIANT.CELLS.NAME.getIndex()));
					
					selectionArgs = new String[] {Integer.toString(groupId), varId};
					Cursor cursor = DBHelper.mDB.rawQuery(
							"SELECT  " + 	"  s."+ DBNames.TABLES.STUDENT.CELLS.NAME +
							" FROM " + DBNames.TABLES.STUDENT  + " AS s " +
									" INNER JOIN " + DBNames.TABLES.STUDENT_LAB + " AS sl " +
										" ON sl." + DBNames.TABLES.STUDENT_LAB.CELLS.STUDENT_ID + 
										" == " +
										" s." + DBNames.TABLES.STUDENT.CELLS.ID +
							" WHERE ( s." + DBNames.TABLES.STUDENT.CELLS.CLASS_ID + " == ? AND " +
									"sl." + DBNames.TABLES.STUDENT_LAB.CELLS.VARIANT_ID + " == ?);"
						, selectionArgs
					);
					String people = "";
					if(cursor.moveToFirst()) {
						do {
							if (!people.equals("")) {
								people += ", ";
							}
							people += cursor.getString(0);
						} while (cursor.moveToNext());
					}
					cursor.close();
					m.put(from[position][3], people);
					break;
				default:
					break;
				}
				
				mData.add(m);
			} while (cursorVar.moveToNext());
		}
		cursorVar.close();
		
		Collections.sort(mData, new Comparator<HashMap<String, String>>() {

			@Override
			public int compare(HashMap<String, String> lhs,
					HashMap<String, String> rhs) {
				return Integer.valueOf(lhs.get(NUMBER)).compareTo(Integer.valueOf(rhs.get(NUMBER)));
			}
		});
		
		return mData;
	}

	public int getResourse() {
		return resources[position];
	}

	public String[] getFrom() {
		return from[position];
	}

	public int[] getTo() {
		return to[position];
	}
}
