package com.ishvlad.android_cw3_5.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.layer.Base;

public class DataAdapter extends SimpleAdapter {
	private static ArrayList<Map<String, String>> mData = new ArrayList<Map<String, String>>();
	private Activity mContext;
	private static final String ATTRIBUTE_NAME_TEXT = "text";
	private static final String ATTRIBUTE_DATE_TEXT = "date";
	private static final String ATTRIBUTE_OTHER_TEXT = "other";
	private static String[] mFrom = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_DATE_TEXT, ATTRIBUTE_OTHER_TEXT};
	private static int[] mTo = {R.id.item_student_name, R.id.item_student_date, R.id.item_student_other};
	
	private OnItemClick mClick;
	
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
			m.put(ATTRIBUTE_OTHER_TEXT, item.other == null ? "" : item.other);
			mData.add(m);
		}
	}
	
	public void setClick(OnItemClick click) {
		mClick = click;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		
		final LinearLayout view = (LinearLayout)mContext.getLayoutInflater().inflate(R.layout.item_list_view, null);
		
		checkData(pos, view);
		
		view.findViewById(R.id.item_student_block).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mClick.onBodyClick(pos);
			}
		});
		view.findViewById(R.id.item_student_btn).setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				mClick.onBtnLongClick();
				notifyDataSetChanged();
				return false;
			}
		});
		view.findViewById(R.id.item_student_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mClick.onBtnClick(pos);
				checkData(pos, (View)v.getParent());
				notifyDataSetChanged();
			}
			
		});
		return view;
	}
	
	private void checkData(int position, View view) {
		Map<String, String> data = mData.get(position);
		
		((TextView)view.findViewById(R.id.item_student_name)).setText(data.get(ATTRIBUTE_NAME_TEXT));
		
		if (!data.get(ATTRIBUTE_DATE_TEXT).equals("")) {
			((TextView)view.findViewById(R.id.item_student_date)).setVisibility(View.VISIBLE);
			((TextView)view.findViewById(R.id.item_student_date)).setText(data.get(ATTRIBUTE_DATE_TEXT));
			((TextView)view.findViewById(R.id.item_student_btn)).setBackgroundResource(R.drawable.check);
			if (!data.get(ATTRIBUTE_OTHER_TEXT).equals("")) {
				((TextView)view.findViewById(R.id.item_student_other)).setVisibility(View.VISIBLE);
				((TextView)view.findViewById(R.id.item_student_separator_comment)).setVisibility(View.VISIBLE);
				((TextView)view.findViewById(R.id.item_student_other)).setText(data.get(ATTRIBUTE_OTHER_TEXT));
			} else {
				((TextView)view.findViewById(R.id.item_student_other)).setVisibility(View.INVISIBLE);
				((TextView)view.findViewById(R.id.item_student_separator_comment)).setVisibility(View.INVISIBLE);
			}
		} else {
			((TextView)view.findViewById(R.id.item_student_date)).setVisibility(View.INVISIBLE);
			((TextView)view.findViewById(R.id.item_student_other)).setVisibility(View.INVISIBLE);
			((TextView)view.findViewById(R.id.item_student_separator_comment)).setVisibility(View.INVISIBLE);
			((TextView)view.findViewById(R.id.item_student_btn)).setBackgroundColor(mContext.getResources().getColor(android.R.color.background_light));
		}
		
	}
	
	public interface OnItemClick {
		public void onBtnLongClick();
		public void onBodyClick(int position);
		public void onBtnClick(int position);
	}
}
