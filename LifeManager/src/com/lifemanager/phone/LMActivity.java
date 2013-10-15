package com.lifemanager.phone;

import com.lifemanager.improve.UserActionCollector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LMActivity extends Activity{

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
