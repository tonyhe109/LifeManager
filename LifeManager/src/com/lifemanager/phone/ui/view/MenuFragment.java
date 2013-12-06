package com.lifemanager.phone.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.lifemanager.R;

public class MenuFragment extends Fragment {

	private static ButtonOnClickListener buttonClickListener = new ButtonOnClickListener();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View menuView = inflater.inflate(R.layout.menu_frame, null);
		View button_today = menuView.findViewById(R.id.button_today);
		View button_weekly = menuView.findViewById(R.id.button_weekly);
		View button_setting = menuView.findViewById(R.id.button_setting);
		menuView.setClickable(true);
		menuView.setFocusable(true);
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

		@Override
		public void onClick(View v) {
			System.out.println("=================================================");
			Log.e("MenuFragment", "ButtonOnCleckListener on view:" + v.getId());
		}

	}
}
