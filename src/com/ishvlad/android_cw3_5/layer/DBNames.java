package com.ishvlad.android_cw3_5.layer;


public class DBNames {
	public final static Tables TABLES = new Tables();
	
	
	public static class Tables {
		
		public static class Class {
			public final ClassCells CELLS = new ClassCells();
			
			@Override
			public String toString() {
				return "Class";
			}
			
			public static class ClassCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell NAME = new Cell("name", 1);
				public final Cell YEAR = new Cell("year", 2);
			}
		}
		public static class Object {
			public final ObjectCells CELLS = new ObjectCells();
			
			@Override
			public String toString() {
				return "Object";
			}
			
			public static class ObjectCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell NAME = new Cell("name", 1);
				public final Cell YEAR = new Cell("year", 2);
			}
		}
		public static class ClassObject {
			public final ClassObjectCells CELLS = new ClassObjectCells();
			
			@Override
			public String toString() {
				return "ClassObject";
			}
			
			public static class ClassObjectCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell CLASS_ID = new Cell("class_id", 1);
				public final Cell OBJECT_ID = new Cell("object_id", 2);
				
			}
		}
		public static class Student {
			public final StudentCells CELLS = new StudentCells();
			
			@Override
			public String toString() {
				return "Student";
			}
			
			public static class StudentCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell NAME = new Cell("name", 1);
				public final Cell CLASS_ID = new Cell("class_id", 2);
			}
		}
		public static class Lecture {
			public final LectureCells CELLS = new LectureCells();
			
			@Override
			public String toString() {
				return "Lecture";
			}
			
			public static class LectureCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell NAME = new Cell("name", 1);
				public final Cell DATE = new Cell("date", 2);
				public final Cell OBJECT_ID = new Cell("object_id", 3);
			}
		}
		public static class ClassLecture {
			public final ClassLectureCells CELLS = new ClassLectureCells();
			
			@Override
			public String toString() {
				return "ClassLecture";
			}
			
			public static class ClassLectureCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell DATE = new Cell("date", 1);
				public final Cell LECTURE_ID = new Cell("lecture_id", 2);
				
			}
		}
		public static class StudentLecture {
			public final StudentLectureCells CELLS = new StudentLectureCells();
			
			@Override
			public String toString() {
				return "StudentLecture";
			}
			
			public static class StudentLectureCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell COMMENT = new Cell("comment", 1);
				public final Cell STUDENT_ID = new Cell("student_id", 2);
				public final Cell CLASS_LECTURE_ID = new Cell("class_lecture_id", 3);
				
			}
		}
		public static class Lab {
			public final LabCells CELLS = new LabCells();
			
			@Override
			public String toString() {
				return "Lab";
			}
			
			public static class LabCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell NAME = new Cell("name", 1);
				public final Cell DATE = new Cell("date", 2);
				public final Cell MIN = new Cell("min", 3);
				public final Cell MAX = new Cell("max", 4);
				public final Cell OBJECT_ID = new Cell("object_id", 5);
			}
		}
		public static class StudentLab {
			public final StudentLabCells CELLS = new StudentLabCells();
			
			@Override
			public String toString() {
				return "StudentLab";
			}
			
			public static class StudentLabCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell MARK = new Cell("mark", 1);
				public final Cell DATE_START = new Cell("date_start", 2);
				public final Cell VARIANT_ID = new Cell("variant_id", 3);
				public final Cell STUDENT_ID = new Cell("student_id", 4);
			}
		}
		public static class Variant {
			public final VariantCells CELLS = new VariantCells();
			
			@Override
			public String toString() {
				return "Variant";
			}
			
			public static class VariantCells {
				public final Cell ID = new Cell("_id", 0);
				public final Cell NAME = new Cell("name", 1);
				public final Cell NUMBER = new Cell("number", 2);
				public final Cell TABLE_OWNER = new Cell("table_owner", 3);
				public final Cell OWNER_ID = new Cell("owner_id", 4);
			}
		}
		
		
		
		public final Class 					CLASS = new Class();
		public final ClassObject 			CLASS_OBJECT = new ClassObject();
		public final ClassLecture 			CLASS_LECTURE = new ClassLecture();
		
		public final Lab					LAB = new Lab();
		public final Lecture 				LECTURE = new Lecture();
		
		public final Object 				OBJECT = new Object();
		
		public final Student 				STUDENT = new Student();
		public final StudentLab 			STUDENT_LAB = new StudentLab();
		public final StudentLecture 		STUDENT_LECTURE = new StudentLecture();
		
		public final Variant				VARIANT = new Variant();
	}
	
	public static class Cell {
		private String Tag;
		private int Index;
		
		public Cell(String tag, int index) {
			Tag = tag;
			Index = index;
		}
		
		public int getIndex() {
			return Index;
		}
		
		@Override
		public String toString() {
			return Tag;
		}
	}
}
