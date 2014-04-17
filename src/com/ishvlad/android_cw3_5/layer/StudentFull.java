package com.ishvlad.android_cw3_5.layer;

import java.util.LinkedList;

public class StudentFull extends StudentBase{
	public LinkedList<LectureView> lectures;
	public int procentLectures;
	public int pointSum;
	
	
	public StudentFull(int ID, String Name) {
		super(ID, Name);
	}


}
