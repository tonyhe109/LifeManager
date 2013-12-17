
package com.lifemanager.ui.data;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lifemanager.data.TaskList;

public abstract class TaskViewItemList<T> extends ArrayList {

    private static final long serialVersionUID = 2233096231572294529L;

    public static final int ORDER_PRIORITY = 0;
    public static final int ORDER_TIME = 1;
    /**
     * 
     */

    protected TaskList _TaskList = null;
    protected int _OrderMode = ORDER_PRIORITY;

    protected TaskDateAdapter _DateAdapter;

    public TaskViewItemList(TaskList tList) {
        _TaskList = tList;
        invalidate();
    }

    public TaskViewItemList(TaskList tList, int order) {
        _OrderMode = order;
        _TaskList = tList;
        invalidate();
    }

    public void setTaskList(TaskList tList) {
        _TaskList = tList;
        invalidate();
    }

    public void setTaskList(TaskList tList, int order) {
        _OrderMode = order;
        _TaskList = tList;
        invalidate();
    }

    public void setOrderMode(int order) {
        if (order != ORDER_PRIORITY && order != ORDER_TIME) {
            throw new IllegalStateException("Task Item List have no such order mode");
        }
        if (_OrderMode != order) {
            _OrderMode = order;
        }
        invalidate();
    }

    public BaseAdapter getAdapter() {
        return _DateAdapter;
    }

    protected abstract void invalidate();

    abstract class TaskDateAdapter<T> extends BaseAdapter {

        @Override
        public int getCount() {
            return TaskViewItemList.this.size();
        }

        @Override
        public T getItem(int position) {
            return (T) TaskViewItemList.this.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

}
