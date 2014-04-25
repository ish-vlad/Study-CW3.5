package com.ishvlad.android_cw3_5.layer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Base  implements Comparable<Base>, Serializable{
	private static final long serialVersionUID = 2L;
	public int id;
	public String name;
	public String date = "", other = "";
	public int dateFromId = 0, otherId = 0;
	
	
	public Base(int ID, String Name, long DATE) {
		id = ID;
		name = Name;
		date = new SimpleDateFormat("DD.MM.yyyy").format(new Date(DATE));
	}
	
	public Base(int ID, String Name, int DateFromId, String Date, int anotherId, String anotherInfo) {
		id = ID;
		name = Name;
		dateFromId = DateFromId;
		date = Date;
		otherId = anotherId;
		other = anotherInfo;
	}
	
	public Base() {
	}

	@Override
	public boolean equals(Object o) {
		return id == ((Base)o).id;
	}
	
	@Override
	public String toString() {
		return name;
	}
	

	@Override
	public int compareTo(Base another) {
		SimpleDateFormat df = new SimpleDateFormat("DD.MM.YYYY");
		try {
			return df.parse(date).compareTo(df.parse(another.date));
		} catch (ParseException e) {
			return 0;
		}
	}
}
