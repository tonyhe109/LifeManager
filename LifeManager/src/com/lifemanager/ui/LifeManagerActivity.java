package com.lifemanager.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.lifemanager.LifeManagerModule;
import com.lifemanager.improve.UserActionCollector;

public class LifeManagerActivity extends FragmentActivity{

	public static final LifeManagerModule LOADING = new LifeManagerModule(0,"LOADING");
	public static final LifeManagerModule MAIN_TASK_PANEL = new LifeManagerModule(1,"MAIN_TASK_PANEL");
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadGlobleSettings();
	}
	
	protected void onCreate(Bundle savedInstanceState,LifeManagerModule module) {
		super.onCreate(savedInstanceState);
		loadGlobleSettings();
		UserActionCollector.activityOnCreate(module);
	}
	
	private void loadGlobleSettings() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

}
