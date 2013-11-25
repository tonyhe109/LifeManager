package com.lifemanager.phone.ui;

import android.os.Bundle;

import com.lifemanager.logging.Logger;
import com.lifemanager.phone.LifeManagerActivity;
import com.lifemanager.phone.ui.view.TaskFrame;

public class TaskPanelActivity extends LifeManagerActivity {
	private static final Logger LOG = Logger
			.getLogger(LifeManagerActivity.MAIN_TASK_PANEL.toString());

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, LifeManagerActivity.MAIN_TASK_PANEL);
		TaskFrame layout = new TaskFrame(this);
		setContentView(layout);
	}
	
	
	@Override
	public void onBackPressed() {
		LOG.debug("onBackPressed ");
	}

	@Override
	protected void onResume() {
		super.onResume();

		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
