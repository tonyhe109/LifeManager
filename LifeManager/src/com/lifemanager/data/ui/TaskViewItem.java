
package com.lifemanager.data.ui;

import com.lifemanager.data.Task;
import com.lifemanager.data.TaskGroup;

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

class TaskItem extends TaskViewItem<Task> {

    @Override
    public int getItemType() {
        return ITEM_TASK;
    }

}

class GroupItem extends TaskViewItem<TaskGroup> {

    @Override
    public int getItemType() {
        return ITEM_GROUP;
    }

}
