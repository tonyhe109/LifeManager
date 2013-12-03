package com.lifemanager.phone.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeeklyTaskFragment extends TaskFragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LOG.debug("WeeklyTaskFragment.onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		LOG.debug("WeeklyTaskFragment.onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		//
		_taskPanelTitle.setText("Weekly Tasks ");
	}
}
