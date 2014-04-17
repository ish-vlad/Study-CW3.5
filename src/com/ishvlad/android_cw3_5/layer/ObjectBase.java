package com.ishvlad.android_cw3_5.layer;

public class ObjectBase {
	public int objectId;
	public String name;
	public int year;
	public boolean hasCourseWork;
	
	public ObjectBase(int ID, String Name, int Year, int HasCourseWork) {
		objectId = ID;
		name = Name;
		year = Year;
		hasCourseWork = HasCourseWork % 2 == 0 ? false : true;
	}
	
	
	@Override
	public String toString() {
		return name + "(" + Integer.toString(year) + ")";
	}
}
