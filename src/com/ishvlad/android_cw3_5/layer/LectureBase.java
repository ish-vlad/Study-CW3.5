package com.ishvlad.android_cw3_5.layer;

import java.text.SimpleDateFormat;


public class LectureBase implements Comparable<LectureBase>{
	public int lectureId;
	public String name;
	public long date;
	
	public LectureBase(int ID, String Name, long Date) {
		lectureId = ID;
		name = Name;
		date = Date;
	}
	
	@Override
	public String toString() {
		return name + " (" + new SimpleDateFormat("dd.MM.yyyy").format(date) + ")";
	}

	@Override
	public int compareTo(LectureBase another) {
		return Long.valueOf(another.date).compareTo(date);
	}
}
