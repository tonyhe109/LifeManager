package com.lifemanager.ui.view;

import com.lifemanager.data.Task;


public abstract class AbsTaskItem<T> {

	public final int ITEM_TASK = 0;
	public final int ITEM_GROUP = 1;

	public abstract int getItemType();

	public abstract T getData();
}

class TaskItem extends AbsTaskItem<Task> {

	@Override
	public int getItemType() {
		return ITEM_TASK;
	}

	@Override
	public Task getData() {
		return null;
	}

}

class GroupItem extends AbsTaskItem {

	@Override
	public int getItemType() {
		return ITEM_GROUP;
	}

	@Override
	public Object getData() {
		return null;
	}

}
