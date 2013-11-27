package com.lifemanager.phone.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.LifeManagerActivity;
import com.lifemanager.phone.ui.view.RightFragment;
import com.lifemanager.phone.ui.view.SampleListFragment;
import com.lifemanager.phone.ui.view.TaskFrame;

public class TaskPanelActivity extends LifeManagerActivity {
	private static final Logger LOG = Logger
			.getLogger(LifeManagerActivity.MAIN_TASK_PANEL.toString());
	
	private TaskFrame taskFrame;
	private RightFragment rightFragment;
	private SampleListFragment taskFragment;
	private FragmentTransaction ft; 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, LifeManagerActivity.MAIN_TASK_PANEL);
		setContentView(R.layout.taskpanel);
		LOG.debug("onCreate.............");
		taskFrame = (TaskFrame) findViewById(R.id.taskFrame);
		taskFrame.setMenuView(getLayoutInflater().inflate(
				R.layout.menu_frame, null));
		taskFrame.setTaskView(getLayoutInflater().inflate(
				R.layout.task_frame, null));
		LOG.debug("111.............");
		rightFragment = new RightFragment();
		taskFragment = new SampleListFragment();
		LOG.debug("444.............");
		ft = this.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.right_frame, rightFragment);
		ft.replace(R.id.task_frame, taskFragment);
		ft.commit();
		LOG.debug("5555.............");
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
	
	public void showMenuView(){
		taskFrame.showMenuView();
	}

}
