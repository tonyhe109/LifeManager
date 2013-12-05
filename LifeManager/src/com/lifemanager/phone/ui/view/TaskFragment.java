package com.lifemanager.phone.ui.view;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.ui.TaskPanelActivity;

public class TaskFragment extends ListFragment {
	protected static final Logger LOG = Logger.getLogger("TaskFragment");
	protected ImageView _button_switch_mode;
	protected ImageView _button_menu;
	protected TextView _taskPanelTitle;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LOG.debug("TaskFragment.onCreateView");
		View mView = inflater.inflate(R.layout.task_frame, null);
		_button_switch_mode = (ImageView) mView
				.findViewById(R.id.ic_switch_mode);
		_button_menu = (ImageView) mView.findViewById(R.id.ic_menu);
		_taskPanelTitle = (TextView) mView.findViewById(R.id.iv_title);
		// add click logic
		_button_menu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((TaskPanelActivity) getActivity()).onMenuActionIconClick();
			}
		});

		_button_switch_mode.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((TaskPanelActivity) getActivity()).onSwitchModeButtonClick();
			}
		});

		return mView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d("----->", position + "");
	}
}
