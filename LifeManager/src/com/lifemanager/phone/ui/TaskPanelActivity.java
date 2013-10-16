package com.lifemanager.phone.ui;

import android.os.Bundle;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.LifeManagerActivity;

public class TaskPanelActivity extends LifeManagerActivity {
	private static final Logger LOG = Logger.getLogger(LifeManagerActivity.MAIN_TASK_PANEL.toString());

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, LifeManagerActivity.MAIN_TASK_PANEL);
		super.setContentView(R.layout.task_panel);
	}

}
