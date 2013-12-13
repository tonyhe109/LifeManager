package com.lifemanager.ui.view;

import com.lifemanager.data.Group;
import com.lifemanager.data.Task;

public abstract class TaskViewItem<T> {

	protected T data;

	public final int ITEM_TASK = 0;
	public final int ITEM_GROUP = 1;

	public abstract int getItemType();

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

class TaskTypeItem extends TaskViewItem<Task> {

	@Override
	public int getItemType() {
		return ITEM_TASK;
	}

	// @Override
	// public Task getData() {
	//
	// return null;
	// }

}

class GroupTypeItem extends TaskViewItem<Group> {

	@Override
	public int getItemType() {
		return ITEM_GROUP;
	}

	// @Override
	// public Group getData() {
	//
	// return null;
	// }

}
