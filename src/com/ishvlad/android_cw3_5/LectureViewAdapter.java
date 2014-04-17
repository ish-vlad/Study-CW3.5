package com.ishvlad.android_cw3_5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.ishvlad.android_cw3_5.layer.LectureView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LectureViewAdapter extends SimpleAdapter {
	private static ArrayList<Map<String, String>> mData = new ArrayList<Map<String, String>>();
	private Activity mContext;
	private static final String ATTRIBUTE_NAME_TEXT = "text";
	private static final String ATTRIBUTE_DATE_TEXT = "date";
	private static final String ATTRIBUTE_COMMENT_TEXT = "comment";
	private static String[] mFrom = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_DATE_TEXT, ATTRIBUTE_COMMENT_TEXT};
	private static int[] mTo = {R.id.item_lecture_name, R.id.item_lecture_date, R.id.item_lecture_comment};
	
	public LectureViewAdapter(Activity context,
			LinkedList<LectureView> data) {
		super(context, mData, R.layout.item_list_lecture_view, mFrom, mTo);
		
		mData.clear();
		mContext = context;
		
		Collections.sort(data);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Map<String, String> m;
		
		for(LectureView item : data) {
			m = new HashMap<String, String>();
			m.put(ATTRIBUTE_NAME_TEXT, item.name);
			m.put(ATTRIBUTE_DATE_TEXT, df.format(new Date(item.date)));
			m.put(ATTRIBUTE_COMMENT_TEXT, item.comment);
			mData.add(m);
		}
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Map<String, String> data = mData.get(position);
		RelativeLayout view = (RelativeLayout)mContext.getLayoutInflater().inflate(R.layout.item_list_lecture_view, null);
		
		((TextView)view.findViewById(R.id.item_lecture_name)).setText(data.get(ATTRIBUTE_NAME_TEXT));
		((TextView)view.findViewById(R.id.item_lecture_date)).setText(data.get(ATTRIBUTE_DATE_TEXT));
		if (data.get(ATTRIBUTE_COMMENT_TEXT) == null || data.get(ATTRIBUTE_COMMENT_TEXT).equals("")) {
			((TextView)view.findViewById(R.id.item_lecture_comment)).setVisibility(View.INVISIBLE);
		}
		
		return view;
	}

}
