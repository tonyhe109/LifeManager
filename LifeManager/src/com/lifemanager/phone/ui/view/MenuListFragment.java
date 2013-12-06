package com.lifemanager.phone.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifemanager.R;

public class MenuListFragment extends ListFragment {

	private MenuItemAdapter menuItemAdapter;
	private LayoutInflater _inflater;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_inflater = inflater;
		View menuView = inflater.inflate(R.layout.fragment_menu, null);
		return menuView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		menuItemAdapter = new MenuItemAdapter(getActivity());
		menuItemAdapter.add(new MenuItem(R.string.button_text_today,
				R.drawable.icon_today_normal));
		menuItemAdapter.add(new MenuItem(R.string.button_text_weekly,
				R.drawable.icon_seven_days_normal));
		menuItemAdapter.add(new MenuItem(R.string.button_text_setting,
				R.drawable.icon_filter_manage));
		setListAdapter(menuItemAdapter);

	}
	

	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.e("MenuFragment", "onListItemClick on view:" + v.getId());
	}

	private class MenuItem {
		public int NAME_ID;
		public int ICON_RES_ID;

		public MenuItem(int name, int icon) {
			NAME_ID = name;
			ICON_RES_ID = icon;
		}
	}

	private class MenuItemAdapter extends ArrayAdapter<MenuItem> {
		public MenuItemAdapter(Context context) {
			super(context, 0);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
	          MenuItem item = getItem(position);
	          convertView = _inflater.inflate(R.layout.menu_item, null);
	          ImageView imageView = (ImageView)convertView.findViewById(R.id.item_icon);
	          imageView.setImageResource(item.ICON_RES_ID);
	          TextView textView = (TextView)convertView.findViewById(R.id.item_text);
	          textView.setText(item.NAME_ID);
	          convertView.invalidate();
	          return convertView;
	      }
	}

	private class MenuItemView extends RelativeLayout {

		public MenuItemView(Context context) {
			super(context);
		}

		public void setMenuItem(MenuItem item) {
			menuItem = item;
		}

		private MenuItem menuItem;

	}

}
