package com.ishvlad.android_cw3_5.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ishvlad.android_cw3_5.dialog.CreateDialog.NewData;
import com.ishvlad.android_cw3_5.layer.NewGroup;
import com.ishvlad.android_cw3_5.layer.NewObject;
import com.ishvlad.android_cw3_5.layer.NewObject.Lab;
import com.ishvlad.android_cw3_5.layer.NewObject.Lab.Variant;
import com.ishvlad.android_cw3_5.layer.NewObject.Lecture;

public class JsonParser {
	private String mPath;
	private Context mContext;
	private NewData what;
	private int mGroupId;
	
	private static final String TYPE = "type"; 
	private static final String DATA = "data"; 
	
	
	private static final String GROUP_NAME = "name"; 
	private static final String GROUP_YEAR = "year"; 
	private static final String GROUP_STUDENTS = "students"; 
	
	
	private static final String OBJECT_NAME = "name"; 
	private static final String OBJECT_YEAR = "year"; 
	
	private static final String OBJECT_LECTURES = "lectures"; 
	private static final String OBJECT_LEC_NAME = "name"; 
	private static final String OBJECT_LEC_DATE = "date"; 
	
	private static final String OBJECT_LABS = "labs"; 
	private static final String OBJECT_LABS_NAME = "name";
	private static final String OBJECT_LABS_DATE = "date";
	private static final String OBJECT_LABS_MIN = "min"; 
	private static final String OBJECT_LABS_MAX = "max";
	private static final String OBJECT_LABS_VARIANTS = "variants";
	
	
	private static final String OBJECT_LABS_VARIANTS_NAME = "name";
	private static final String OBJECT_LABS_VARIANTS_NUMBER = "number";
	
	public JsonParser(String path, Activity activity, NewData newData, int groupId) {
		mPath = path;
		mContext = activity;
		what = newData;
		mGroupId = groupId;
	}

	public void parse() {
		new ParseTask().execute();
	}

	private class ParseTask extends AsyncTask<String, Void, String> {
		private ProgressDialog dialog = new ProgressDialog(mContext);
		
		
		@Override
		protected void onPreExecute() {
			if (!dialog.isShowing()) {
				dialog.show();
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(dialog.isShowing()) {
				dialog.dismiss();
			}
			
			Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
			if (result.equals("Complete!")) {
				((Activity)mContext).recreate();
			}
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject jObject = null;
			try {
				jObject = new JSONObject(getJson());
			} catch (JSONException e) {
				return "Syntax error";
			}
			try {
				String type = jObject.getString(TYPE);
				if (type == null) {
					throw new JSONException("");
				} 
				if (type.toLowerCase().equals("group")) {
					if (what.equals(NewData.OBJECT)) {
						return "Your file is new Subject!";
					}
					return parseGroup(jObject.getJSONObject(DATA));
				}
				if (type.toLowerCase().equals("object")) {
					if (what.equals(NewData.GROUP)) {
						return "Your file is new Group!";
					}
					return parseObject(jObject.getJSONObject(DATA));
				}
				throw new JSONException("");
			} catch (JSONException e) {
				return "Error in metadata";
			}
		}
		
		private String getJson() {
			String result = "";
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mPath)));
					String line;
					while ((line = br.readLine()) != null) {
						result += line;
					}
					br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			return result;
		}
		
		private String parseObject(JSONObject data) {
			NewObject object = new NewObject();
			
			try {
				object.name = data.getString(OBJECT_NAME);
			} catch (JSONException e) {
				return "Error in subject name";
			}
			try {
				object.year = data.getInt(OBJECT_YEAR);
			} catch (JSONException e) {
				return "Error in subject year";
			}
			JSONArray lectures = null;
			try {
				lectures = data.getJSONArray(OBJECT_LECTURES);
			} catch (JSONException e) {
				return "Error in subject lectures array";
			}
			
			for (int i = 0; i < lectures.length(); i++) {
				Lecture lecture = object.new Lecture();
				JSONObject jLecture;
				try {
					jLecture = lectures.getJSONObject(i);
				} catch (JSONException e) {
					return "Error in lecture ("+i+")";
				}
				
				try {
					lecture.name = jLecture.getString(OBJECT_LEC_NAME);
				} catch (JSONException e) {
					return "Error in lecture name ("+i+")";
				}
				try {
					lecture.date = new SimpleDateFormat("dd.mm.yyyy").parse(jLecture.getString(OBJECT_LEC_DATE)).getTime();
				} catch (JSONException e) {
					return "Error in lecture date ("+i+")";
				} catch (ParseException e) {
					return "Invalid date at lecture ("+ i +")";
				}
				object.lectures.add(lecture);
			}
			JSONArray labs = null;
			try {
				labs = data.getJSONArray(OBJECT_LABS);
			} catch (JSONException e) {
				return "Error in subject labs array";
			}
			
			for (int i = 0; i < labs.length(); i++) {
				Lab lab = object.new Lab();
				JSONObject jLab;
				try {
					jLab = labs.getJSONObject(i);
				} catch (JSONException e) {
					return "Error in lab ("+i+")";
				}
				
				try {
					lab.name = jLab.getString(OBJECT_LABS_NAME);
				} catch (JSONException e) {
					return "Error in lab name ("+i+")";
				}
				try {
					lab.date = new SimpleDateFormat("dd.mm.yyyy").parse(jLab.getString(OBJECT_LABS_DATE)).getTime();
				} catch (JSONException e) {
					return "Error in lab date ("+i+")";
				} catch (ParseException e) {
					return "Invalid date at lab ("+ i +")";
				}
				try {
					lab.min = jLab.getInt(OBJECT_LABS_MIN);
				} catch (JSONException e) {
					return "Error in lab min ("+i+")";
				}
				try {
					lab.max = jLab.getInt(OBJECT_LABS_MAX);
				} catch (JSONException e) {
					return "Error in lab max ("+i+")";
				}
				JSONArray variants = null;
				try {
					variants = data.getJSONArray(OBJECT_LABS_VARIANTS);
				} catch (JSONException e) {
					//return "Error in lab ("+i+") variants array";
				}
				if (variants != null) {
					for (int j = 0; j < variants.length(); j++) {
						Variant var = lab.new Variant();
						JSONObject jVar;
						try {
							jVar = variants.getJSONObject(i);
						} catch (JSONException e) {
							return "Error in lab ("+i+") variant ("+j+")";
						}
						
						try {
							var.name = jVar.getString(OBJECT_LABS_VARIANTS_NAME);
						} catch (JSONException e) {
							return "Error in lab ("+i+") variant ("+j+") name";
						}
						try {
							var.number = jVar.getInt(OBJECT_LABS_VARIANTS_NUMBER);
						} catch (JSONException e) {
							return "Error in lab ("+i+") variant ("+j+") number";
						}
						lab.variants.add(var);
					}
				}
				object.labs.add(lab);
			}
			
			
			return object.save(mGroupId);
		}

		private String parseGroup(JSONObject data) {
			NewGroup group = new NewGroup();
			
			try {
				group.name = data.getString(GROUP_NAME);
			} catch (JSONException e) {
				return "Error in group name";
			}
			try {
				group.year = data.getInt(GROUP_YEAR);
			} catch (JSONException e) {
				return "Error in group year";
			}
			JSONArray students = null;
			try {
				students = data.getJSONArray(GROUP_STUDENTS);
			} catch (JSONException e) {
				return "Error in group students array";
			}
			
			for (int i = 0; i < students.length(); i++) {
				try {
					group.students.add(students.getString(i));
				} catch (JSONException e) {
					return "Error in student name (" + Integer.toString(i) + ")";
				}
			}
			
			return group.save();
		}
	}
	
}
