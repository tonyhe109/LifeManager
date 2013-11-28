package com.lifemanager.phone.ui.view;

public class TaskViewManager {

	private static final TaskViewManager _viewManager = new TaskViewManager();

	private TaskViewManager() {

	}

	public static TaskViewManager getTaskViewManager() {
		return _viewManager;
	}

	public TaskFragment getDefaultTaskFragment() {

		return new SingalDayTaskFragment();
	}

	public TaskFragment getSingleDayTaskFragment() {

		return new SingalDayTaskFragment();
	}

	public TaskFragment getWeeklyTaskFragment() {

		return new SingalDayTaskFragment();
	}

}
