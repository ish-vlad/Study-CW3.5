package com.ishvlad.android_cw3_5.layer;

public class ObjectBase {
	public int objectId;
	public String name;
	public int year;
	
	public ObjectBase(int ID, String Name, int Year) {
		objectId = ID;
		name = Name;
		year = Year;
	}
	
	
	@Override
	public String toString() {
		return name + "(" + Integer.toString(year) + ")";
	}
}
