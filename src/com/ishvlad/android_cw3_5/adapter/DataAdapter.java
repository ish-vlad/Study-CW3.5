package com.ishvlad.android_cw3_5.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.widget.SimpleAdapter;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.layer.Base;

public class DataAdapter extends SimpleAdapter {
	protected static ArrayList<Map<String, String>> mData = new ArrayList<Map<String, String>>();
	protected Activity mContext;
	protected static final String ATTRIBUTE_NAME_TEXT = "text";
	protected static final String ATTRIBUTE_DATE_TEXT = "date";
	protected static final String ATTRIBUTE_OTHER_TEXT = "other";
	protected static final String ATTRIBUTE_MARK_TEXT = "mark";
	protected static String[] mFrom = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_DATE_TEXT, ATTRIBUTE_OTHER_TEXT, ATTRIBUTE_MARK_TEXT};
	protected static int[] mTo = {R.id.item_student_name, R.id.item_student_date, R.id.item_student_other, R.id.item_student_btn};
	
	protected OnItemClick mClick;
	
	public DataAdapter(Activity context,
			LinkedList<Base> data) {
		super(context, mData, R.layout.item_list_view, mFrom, mTo);
		mContext = context;
		
		setData(data);
	}
	
	public void setData(LinkedList<Base> data) {
		mData.clear();
		
		Map<String, String> m;
		
		for(Base item : data) {
			m = new HashMap<String, String>();
			m.put(ATTRIBUTE_NAME_TEXT, item.name);
			m.put(ATTRIBUTE_DATE_TEXT, item.date == null ? "" : item.date);
			m.put(ATTRIBUTE_OTHER_TEXT, item.other == null  ? "" : item.other);
			m.put(ATTRIBUTE_MARK_TEXT, item.mark < 1 ? "" : Integer.toString(item.mark));
			mData.add(m);
		}
	}
	
	public void setClick(OnItemClick click) {
		mClick = click;
	}

	public interface OnItemClick {
		public void onBtnLongClick();
		public void onBodyClick(int position);
		public void onBtnClick(int position);
	}
}
