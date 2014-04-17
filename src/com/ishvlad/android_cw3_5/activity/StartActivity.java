package com.ishvlad.android_cw3_5.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.ishvlad.android_cw3_5.R;
import com.ishvlad.android_cw3_5.helper.DBHelper;

public class StartActivity extends ActionBarActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		findViewById(R.id.block_start_view).setOnClickListener(this);
		findViewById(R.id.block_start_lecture).setOnClickListener(this);
		findViewById(R.id.block_start_labs).setOnClickListener(this);
		findViewById(R.id.btn_start_view).setOnClickListener(this);
		findViewById(R.id.btn_start_lecture).setOnClickListener(this);
		findViewById(R.id.btn_start_labs).setOnClickListener(this);
		
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		Bundle extras = new Bundle();
		int id = v.getId();
		
		if (id == R.id.block_start_view || id == R.id.btn_start_view) {
			
			extras.putSerializable(ChoiseActivity.EXTRA_CHOISE, ChoiseActivity.Choise.VIEW);
			Toast.makeText(this, "Watch!", Toast.LENGTH_SHORT).show();
		
		} else if (id == R.id.block_start_lecture || id == R.id.btn_start_lecture) {
			
			extras.putSerializable(ChoiseActivity.EXTRA_CHOISE, ChoiseActivity.Choise.LECT);
	    	Toast.makeText(this, "Start lect!", Toast.LENGTH_SHORT).show();
	        
		} else if (id == R.id.block_start_labs || id == R.id.btn_start_labs) {
			
	    	extras.putSerializable(ChoiseActivity.EXTRA_CHOISE, ChoiseActivity.Choise.LAB);
	    	Toast.makeText(this, "Start Lab!", Toast.LENGTH_SHORT).show();
	        
		}
		
		Intent intent = new Intent(this, ChoiseActivity.class);
		intent.putExtras(extras);
		startActivity(intent);
	}
}
