package com.ishvlad.android_cw3_5.layer;


public class ClassBase {
	public int groupId;
	public String name;
	public int year;
	
	public ClassBase(int ID, String Name, int Year) {
		groupId = ID;
		name = Name;
		year = Year;
	}
	
	@Override
	public String toString() {
		return name + "(" + Integer.toString(year) + ")";
	}
	
	
}
