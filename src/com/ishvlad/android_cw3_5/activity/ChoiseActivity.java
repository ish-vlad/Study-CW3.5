package com.ishvlad.android_cw3_5.activity;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.activity.ChoiseGroupFragment.onGroupSelect;
import com.ishvlad.android_cw3_5.activity.ChoiseObjectFragment.onObjectSelect;
import com.ishvlad.android_cw3_5.helper.ClassHelper;
import com.ishvlad.android_cw3_5.helper.ObjectHelper;
import com.ishvlad.android_cw3_5.layer.ObjectBase;

public class ChoiseActivity extends Activity implements onGroupSelect, onObjectSelect {
	public static final String TAG = "com.ishvlad.androidCW3.5.choiseActivity";
	public static final String EXTRA_GROUP = TAG + "_group";
	public static final String EXTRA_OBJECT = TAG + "_object";
	
	private int mGroupId = -1;
	private int mObjectId = -1;

	private ClassHelper clsHelp = new ClassHelper(this);
	private ObjectHelper objHelp = new ObjectHelper(this);

	private ChoiseObjectFragment choiseObjectFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choise);
		
		if (savedInstanceState != null) {
			if (savedInstanceState.getInt(EXTRA_GROUP) != -1) {
				mGroupId = savedInstanceState.getInt(EXTRA_GROUP);
				groupSelect(mGroupId);
				
				if (savedInstanceState.getInt(EXTRA_OBJECT) != -1) {
					mObjectId = savedInstanceState.getInt(EXTRA_OBJECT);
					objectSelect(mObjectId);
				}
			}
		}
		
		ChoiseGroupFragment choiseGroupFragment = new ChoiseGroupFragment(clsHelp.getAllBase());
		
		getFragmentManager().beginTransaction()
			.add(R.id.fragment_choise_group, choiseGroupFragment)
			.commit();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(EXTRA_GROUP, mGroupId);
		outState.putInt(EXTRA_OBJECT, mObjectId);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void groupSelect(int groupId) {
		mGroupId = groupId;
		mObjectId = -1;
		objHelp = new ObjectHelper(getBaseContext());
		LinkedList<ObjectBase> mArrayObject = objHelp.getAllBaseFromGroupId(mGroupId);
		ChoiseObjectFragment choiseObjectFragmentNew = new ChoiseObjectFragment(mArrayObject, mGroupId);
		
		if(choiseObjectFragment != null) 
			getFragmentManager().beginTransaction()
				.hide(choiseObjectFragment)
				.commit();
		
		getFragmentManager().beginTransaction()
			.add(R.id.fragment_choise_object, choiseObjectFragmentNew)
			.commit();
		
		choiseObjectFragment = choiseObjectFragmentNew;
	}
	
	public void objectSelect(int objectId) {
		mObjectId = objectId;
		
		Bundle extras = new Bundle();
		extras.putInt(ViewActivity.EXTRA_GROUP, mGroupId);
		extras.putInt(ViewActivity.EXTRA_OBJECT, mObjectId);
		
		Intent intent = new Intent(this, ViewActivity.class);
		intent.putExtras(extras);
		startActivity(intent);
	}
}
