package com.lifemanager.phone.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lifemanager.R;
import com.lifemanager.core.ui.SlidingMenu;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.LifeManagerActivity;
import com.lifemanager.phone.ui.view.MainPanel;
import com.lifemanager.phone.ui.view.MenuFragment;
import com.lifemanager.phone.ui.view.TaskFragment;
import com.lifemanager.phone.ui.view.TaskViewManager;

public class TaskPanelActivity extends LifeManagerActivity {
	private static final Logger LOG = Logger
			.getLogger(LifeManagerActivity.MAIN_TASK_PANEL.toString());

	private MainPanel _taskPanel;
	private SlidingMenu slidingMenu;
	private FragmentTransaction ft;

	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState, LifeManagerActivity.MAIN_TASK_PANEL);
	 setContentView(R.layout.main_panel);
	 _taskPanel = (MainPanel) findViewById(R.id.main_panel);
	 View _menuView = getLayoutInflater().inflate(R.layout.menu_panel, null);
	 View _taskView = getLayoutInflater().inflate(R.layout.task_panel, null);
	 _taskPanel.setMenuView(_menuView);
	 _taskPanel.setTaskView(_taskView);
	 MenuFragment _menuFragment = new MenuFragment();
	 //MenuListFragment _menuFragment = new MenuListFragment();
	 TaskFragment _taskFragment = TaskViewManager.getTaskViewManager()
	 .getDefaultTaskFragment();
	 ft = this.getSupportFragmentManager().beginTransaction();
	 ft.replace(R.id.menu_panel, _menuFragment);
	 ft.replace(R.id.task_panel, _taskFragment);
	 ft.commit();
	
	 }

//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState, LifeManagerActivity.MAIN_TASK_PANEL);
//		setContentView(R.layout.sliding_menu);
//		slidingMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
//		View _menuView = getLayoutInflater().inflate(R.layout.menu_panel, null);
//		View _taskView = getLayoutInflater().inflate(R.layout.task_panel, null);
//		slidingMenu.setMenu(_menuView);
//		slidingMenu.setContent(_taskView);
//		//slidingMenu.setMode(SlidingMenu.RIGHT);
//		// _taskPanel.setMenuView(_menuView);
//		// _taskPanel.setTaskView(_taskView);
//		MenuFragment _menuFragment = new MenuFragment();
//		// MenuListFragment _menuFragment = new MenuListFragment();
//		TaskFragment _taskFragment = TaskViewManager.getTaskViewManager()
//				.getDefaultTaskFragment();
//		ft = this.getSupportFragmentManager().beginTransaction();
//		ft.replace(R.id.menu_panel, _menuFragment);
//		ft.replace(R.id.task_panel, _taskFragment);
//		ft.commit();
//
//	}

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

	public void onMenuActionIconClick() {
		_taskPanel.onMenuActionIconClick();
	}

	public void onSwitchModeButtonClick() {
		// TODO
	}

}
