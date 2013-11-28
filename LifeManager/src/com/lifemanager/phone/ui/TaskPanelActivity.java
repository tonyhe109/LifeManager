package com.lifemanager.phone.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.LifeManagerActivity;
import com.lifemanager.phone.ui.view.MenuFragment;
import com.lifemanager.phone.ui.view.TaskFragment;
import com.lifemanager.phone.ui.view.TaskPanel;
import com.lifemanager.phone.ui.view.TaskViewManager;

public class TaskPanelActivity extends LifeManagerActivity {
	private static final Logger LOG = Logger
			.getLogger(LifeManagerActivity.MAIN_TASK_PANEL.toString());

	private TaskPanel _taskPanel;
	private FragmentTransaction ft;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, LifeManagerActivity.MAIN_TASK_PANEL);
		setContentView(R.layout.taskpanel);
		_taskPanel = (TaskPanel) findViewById(R.id.taskFrame);
		_taskPanel.setMenuView(getLayoutInflater().inflate(R.layout.menu_frame,
				null));
		_taskPanel.setTaskView(getLayoutInflater().inflate(R.layout.task_frame,
				null));
		MenuFragment _menuFragment = new MenuFragment();
		// taskFragment = new SampleListFragment();
		TaskFragment _taskFragment = TaskViewManager.getTaskViewManager()
				.getDefaultTaskFragment();
		ft = this.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.menu_frame, _menuFragment);
		ft.replace(R.id.task_frame, _taskFragment);
		ft.commit();

	}

	@Override
	public void onBackPressed() {
		LOG.debug("onBackPressed ");
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void showMenuView() {
		_taskPanel.showMenuView();
	}

}
