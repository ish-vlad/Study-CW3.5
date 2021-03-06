package com.ishvlad.android_cw3_5.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.helper.JsonParser;
import com.lamerman.FileDialog;
import com.lamerman.SelectionMode;

public class CreateDialog extends DialogFragment implements OnClickListener {
	private View mainView;
	private NewData newData;
	private int mGroupId;
	
	public CreateDialog() {}
	
	public CreateDialog(NewData arg0, int groupId) {
		newData = arg0;
		mGroupId = groupId;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("NewData", newData);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			newData = (NewData)savedInstanceState.getSerializable("NewData");
		}
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle(getResources().getText(R.string.title_dialog_open));
		
		mainView = inflater.inflate(R.layout.dialog_open, null);
		mainView.findViewById(R.id.dialog_btn_open).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_btn_ok).setOnClickListener(this);
		mainView.findViewById(R.id.dialog_btn_cancel).setOnClickListener(this);
	    return mainView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		((EditText)mainView.findViewById(R.id.dialog_edit_text)).setText(data.getStringExtra(FileDialog.RESULT_PATH));
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.dialog_btn_open:
			Intent intent = new Intent(getActivity().getBaseContext(), FileDialog.class);
            intent.putExtra(FileDialog.START_PATH,  Environment.getExternalStorageDirectory().getPath());
            intent.putExtra(FileDialog.CAN_SELECT_DIR, false);
            intent.putExtra(FileDialog.SELECTION_MODE, SelectionMode.MODE_OPEN);
            intent.putExtra(FileDialog.FORMAT_FILTER, new String[]{"json"});
            startActivityForResult(intent, 1);
            break;
		case R.id.dialog_btn_ok:
			if (!((EditText)mainView.findViewById(R.id.dialog_edit_text)).getText().toString().equals("")) {
				new JsonParser(
						((EditText)mainView.findViewById(R.id.dialog_edit_text)).getText().toString(),
						getActivity(),
						newData,
						mGroupId
				).parse();
			}
			dismiss();
			break;
		case R.id.dialog_btn_cancel:
			dismiss();
			break;
		}
	}
	

	public enum NewData { GROUP,OBJECT }

}
