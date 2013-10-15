package com.lifemanager.phone.ui;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.BootActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TaskPanelActivity extends FragmentActivity {
	private static final Logger LOG = Logger.getLogger(TaskPanelActivity.class);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.task_panel);
	}
	
	@Override
	public void onBackPressed() {
		LOG.debug("onBackPressed ");
		// TODO
	}

}
