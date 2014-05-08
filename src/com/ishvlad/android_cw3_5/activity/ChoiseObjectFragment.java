package com.ishvlad.android_cw3_5.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.dialog.CreateDialog;
import com.ishvlad.android_cw3_5.dialog.CreateDialog.NewData;
import com.ishvlad.android_cw3_5.helper.ObjectHelper;
import com.ishvlad.android_cw3_5.layer.ObjectBase;

public class ChoiseObjectFragment extends Fragment {
	public interface onObjectSelect {
		public void objectSelect(int objectId);
	}
	
	private ListView mView;
	onObjectSelect mObjectSelect;
	
	private final String ATTRIBUTE_NAME_TEXT = "text";
	private final String ATTRIBUTE_YEAR_TEXT = "year";
	private final String ATTRIBUTE_ID_TEXT = "id";
	private String[] mFrom = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_YEAR_TEXT, ATTRIBUTE_ID_TEXT};
	private int[] mTo = {R.id.item_choise_title, R.id.item_choise_year};
	private int mGroupId;
	
	private ArrayList<Map<String, String>> mData = null;
	
	public ChoiseObjectFragment() {}
	
	public ChoiseObjectFragment(LinkedList<ObjectBase> arrayObject, int groupId) {
		mGroupId = groupId;
		mData = new ArrayList<Map<String, String>>(arrayObject.size());
		
		Map<String, String> m;
		for(ObjectBase item : arrayObject) {
			m = new HashMap<String, String>();
			m.put(ATTRIBUTE_NAME_TEXT, item.name);
			m.put(ATTRIBUTE_YEAR_TEXT, Integer.toString(item.year));
			m.put(ATTRIBUTE_ID_TEXT, Integer.toString(item.objectId));
			
			mData.add(m);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    try {
	        mObjectSelect = (onObjectSelect) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString() + " must implement onObjectSelect");
	    }
	  }

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_choise_list, null);
		Button add = (Button)v.findViewById(R.id.choise_btn_add);

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
				
				adb.setTitle(R.string.title_dialog_append);
				
				final LinkedList<ObjectBase> objects = new ObjectHelper(getActivity()).getAllBaseFromNotGroupId(mGroupId);

//				final LinkedList<String> bases = new LinkedList<String>();
//				for(ObjectBase item:objects) {
//					bases.add(item.toString());
//				}
//				
				
				ArrayAdapter<ObjectBase> mAdapter = new ArrayAdapter<ObjectBase>(
						getActivity(), 
						android.R.layout.simple_list_item_1, 
						objects
				);

				adb.setAdapter(mAdapter, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new ObjectHelper(getActivity()).addObject(mGroupId, objects.get(which).objectId);
						dialog.dismiss();
						getActivity().recreate();
					}
				});
				
				
				
				AlertDialog dialog = adb.create(); 
				dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.btn_new_object), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						CreateDialog mDialog = new CreateDialog(NewData.OBJECT, mGroupId);
						mDialog.show(getActivity().getFragmentManager(), "openFileDialog");
					}
				});
				
				dialog.show();
				
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
				mObjectSelect.objectSelect(Integer.parseInt(mData.get(arg2).get(ATTRIBUTE_ID_TEXT)));
			}
		});

		((TextView)v.findViewById(R.id.title_choise_list)).setText(R.string.title_choise_object);

		mView = (ListView)v.findViewById(R.id.list_choise);
		

		return v;
	}
}

