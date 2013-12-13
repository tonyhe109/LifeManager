package com.lifemanager.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.lifemanager.R;
import com.lifemanager.ui.TaskPanelActivity;

public class MenuFragment extends Fragment {

	private static ButtonOnClickListener buttonClickListener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View menuView = inflater.inflate(R.layout.menu_frame, null);
		View button_today = menuView.findViewById(R.id.button_today);
		View button_weekly = menuView.findViewById(R.id.button_weekly);
		View button_setting = menuView.findViewById(R.id.button_setting);
		if (buttonClickListener == null) {
			buttonClickListener = new ButtonOnClickListener(
					(TaskPanelActivity) getActivity());
		}
		menuView.setOnClickListener(buttonClickListener);
		button_today.setClickable(true);
		button_weekly.setClickable(true);
		button_setting.setClickable(true);
		button_today.setOnClickListener(buttonClickListener);
		button_weekly.setOnClickListener(buttonClickListener);
		button_setting.setOnClickListener(buttonClickListener);
		return menuView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private static class ButtonOnClickListener implements OnClickListener {
		private TaskPanelActivity contorler = null;

		public ButtonOnClickListener(TaskPanelActivity activity) {
			contorler = activity;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_today:
				contorler.switchTaskView(TaskFragment.SINGLE_DAY_TASK_VIEW);
				break;
			case R.id.button_weekly:
				contorler.switchTaskView(TaskFragment.WEEKLY_TASK_VIEW);
				break;
			case R.id.button_setting:
				Log.e("MenuFragment", "not implemention exception");
				break;
			}
		}
	}
}
