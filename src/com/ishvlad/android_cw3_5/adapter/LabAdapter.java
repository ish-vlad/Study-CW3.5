package com.ishvlad.android_cw3_5.adapter;

import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.layer.Base;

public class LabAdapter extends DataAdapter {
	private int mRight;
	
	
	public LabAdapter(Activity context, LinkedList<Base> data) {
		super(context, data);
		mRight = context.getResources().getDimensionPixelSize(R.dimen.step_1_2);
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
		((TextView)view.findViewById(R.id.item_student_date)).setVisibility(View.INVISIBLE);
		((TextView)view.findViewById(R.id.item_student_date)).setPadding(0, 0, 0, 0);
		((TextView)view.findViewById(R.id.item_student_other)).setVisibility(View.INVISIBLE);
		((TextView)view.findViewById(R.id.item_student_separator_comment)).setVisibility(View.INVISIBLE);
		((TextView)view.findViewById(R.id.item_student_btn)).setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
		((TextView)view.findViewById(R.id.item_student_btn)).setText("");
		
		if (!data.get(ATTRIBUTE_OTHER_TEXT).equals("") && !data.get(ATTRIBUTE_OTHER_TEXT).equals("-1")) {
			((TextView)view.findViewById(R.id.item_student_other)).setVisibility(View.VISIBLE);
			((TextView)view.findViewById(R.id.item_student_other)).setText("Вариант " + data.get(ATTRIBUTE_OTHER_TEXT));
		}
		if (!data.get(ATTRIBUTE_MARK_TEXT).equals("")) {
			((TextView)view.findViewById(R.id.item_student_btn)).setText(data.get(ATTRIBUTE_MARK_TEXT));
		} else {
			if (!data.get(ATTRIBUTE_DATE_TEXT).equals("")) {
				((TextView)view.findViewById(R.id.item_student_date)).setVisibility(View.VISIBLE);
				((TextView)view.findViewById(R.id.item_student_date)).setPadding(mRight, 0, 0 ,0);
				((TextView)view.findViewById(R.id.item_student_date)).setText(data.get(ATTRIBUTE_DATE_TEXT));
				((TextView)view.findViewById(R.id.item_student_btn)).setBackgroundResource(R.drawable.finish);
			} 
		}
		
		
	}
}
