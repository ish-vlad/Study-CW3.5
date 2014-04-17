package com.ishvlad.android_cw3_5.layer;


public class StudentBase {
	public int studentId;
	public String name;
	
	public StudentBase(int ID, String Name) {
		studentId = ID;
		name = Name;
	}
	
	@Override
	public boolean equals(Object o) {
		return studentId == ((StudentBase)o).studentId;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
