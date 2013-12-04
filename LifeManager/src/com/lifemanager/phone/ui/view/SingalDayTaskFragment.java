package com.lifemanager.phone.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SingalDayTaskFragment extends TaskFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LOG.debug("SingalDayTaskFragment.onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LOG.debug("SingalDayTaskFragment.onActivityCreated");
		_taskPanelTitle.setText("Singal Day Tasks");
		LOG.debug("TaskFragment.onActivityCreated");
	}
}
