package com.ishvlad.android_cw3_5.layer;

public class LectureView extends LectureBase{
	public String comment;
	public int classLectureId;
	public int studentLectureId;
	
	
	public LectureView(int id, String name, String coment, long date, int CLID, int SLID) {
		super(id, name, date);
		comment = coment;
		classLectureId = CLID;
		studentLectureId = SLID;
	}
	
}
