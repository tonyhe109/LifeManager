package com.lifemanager.phone.ui.view;

import android.view.View;

public class TaskViewManager {
	
	private static final TaskViewManager _viewManager = new TaskViewManager();

	private TaskViewManager() {
	
	}
	
	public static TaskViewManager getTaskViewManager(){
		return _viewManager;
	}
	
	public View createDefaultTaskView(){
		
		return null;
	}
	
	public View createSingleDayTaskView (){
		
		return null;
	}

}
