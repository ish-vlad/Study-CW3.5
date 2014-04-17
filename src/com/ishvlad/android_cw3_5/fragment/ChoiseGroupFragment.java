package com.ishvlad.android_cw3_5.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.dialog.CreateDialog;
import com.ishvlad.android_cw3_5.dialog.CreateDialog.NewData;
import com.ishvlad.android_cw3_5.layer.ClassBase;

public class ChoiseGroupFragment extends Fragment {
	
	public interface onGroupSelect {
		public void groupSelect(int groupId);
	}
	
	onGroupSelect mGroupSelect;
	
	private final String ATTRIBUTE_NAME_TEXT = "text";
	private final String ATTRIBUTE_YEAR_TEXT = "year";
	private final String ATTRIBUTE_ID_TEXT = "id";
	private String[] mFrom = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_YEAR_TEXT, ATTRIBUTE_ID_TEXT};
	private int[] mTo = {R.id.item_choise_title, R.id.item_choise_year};
	
	private ArrayList<Map<String, String>> mData = null;
	private ListView mView;
	
	public ChoiseGroupFragment() {}
	
	public ChoiseGroupFragment(LinkedList<ClassBase> arrayGroup) {
		
		mData = new ArrayList<Map<String, String>>(arrayGroup.size());
		
		Map<String, String> m;
		for(ClassBase item : arrayGroup) {
			m = new HashMap<String, String>();
			m.put(ATTRIBUTE_NAME_TEXT, item.name);
			m.put(ATTRIBUTE_YEAR_TEXT, Integer.toString(item.year));
			m.put(ATTRIBUTE_ID_TEXT, Integer.toString(item.groupId));
			Log.d("GROUP", item.toString());
			mData.add(m);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    try {
	        mGroupSelect = (onGroupSelect) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString() + " must implement onGroupSelect");
	    }
	  }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_choise_list, null);
		Button add = (Button)v.findViewById(R.id.choise_btn_add);
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CreateDialog dialog = new CreateDialog(NewData.GROUP);
				dialog.show(getActivity().getFragmentManager(), "openFileDialog");
			}
		});
		mView = (ListView)v.findViewById(R.id.list_choise);
		
		if (mData == null) {
			return (View)mView.getParent();
		}
		
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), mData, R.layout.item_list_choise, mFrom, mTo);
		mView.setAdapter(adapter);
		mView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mGroupSelect.groupSelect(Integer.parseInt(mData.get(arg2).get(ATTRIBUTE_ID_TEXT)));
			}
		});
		
		
	((TextView)((View)mView.getParent()).findViewById(R.id.title_choise_list)).setText(R.string.title_choise_group);
		
		return (View)mView.getParent();
	}

	
	
}
