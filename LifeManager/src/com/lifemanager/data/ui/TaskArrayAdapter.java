
package com.lifemanager.data.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lifemanager.data.Task;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.TaskOrderMode;
import com.lifemanager.exception.NotImplementException;

/**
 * @author tony.he
 * @param <TaskViewItem>
 */
public abstract class TaskArrayAdapter extends BaseAdapter implements TaskOrderMode {

    protected TaskList _TaskList = null;
    protected int _Order = ORDER_PRIORITY;
    protected int _Mode = MODE_SIMPLE;
    protected List _AllTaskItems;
    protected Object _Lock = new Object();
    protected Context _Context;

    protected ArrayList _OriginalItems;

    // TODO add task filter , as the task have many status ...

    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called
     * whenever {@link #_AllTaskItems} is modified.
     */
    private boolean _NotifyOnChange = true;

    public TaskArrayAdapter(Context context, TaskList tList) {
        _Context = context;
        _TaskList = tList;
        _AllTaskItems = new ArrayList();
        init();
        invalidate();
    }

    public TaskArrayAdapter(Context context, TaskList tList, int order, int mode) {
        _Context = context;
        _Order = order;
        _TaskList = tList;
        _AllTaskItems = new ArrayList();
        init();
        invalidate();
    }

    protected abstract void init() ;

    public void setTaskList(TaskList tList) {
        _TaskList = tList;
        invalidate();
    }

    public void setOrder(int order) {
        if (order != ORDER_PRIORITY && order != ORDER_TIME) {
            throw new IllegalStateException("Task Item List have no such order mode");
        }
        if (_Order != order) {
            _Order = order;
        }
        invalidate();
    }

    public void setMode(int mode) {
        if (mode != MODE_SIMPLE && mode != MODE_DETAILS) {
            throw new IllegalStateException("Task Item List have no such order mode");
        }
        if (_Mode != mode) {
            _Mode = mode;
        }
        if (_NotifyOnChange)
            notifyDataSetChanged();
    }

    public int size() {
        return _AllTaskItems.size();
    }

    /**
     * Adds the specified object at the end of the array.
     * 
     * @param object The object to add at the end of the array.
     */
    public abstract void add(Task task);

    /**
     * Adds the specified items at the end of the array.
     * 
     * @param items The items to add at the end of the array.
     */
    public void addAll(Task... tasks) {
        synchronized (_Lock) {
            new NotImplementException().printStackTrace();
        }
        if (_NotifyOnChange)
            notifyDataSetChanged();
    }

    /**
     * Inserts the specified object at the specified index in the array.
     * 
     * @param object The object to insert into the array.
     */
    protected void insert(Task task, int index) {
        synchronized (_Lock) {
            new NotImplementException().printStackTrace();
        }
        if (_NotifyOnChange)
            notifyDataSetChanged();
    }

    /**
     * Removes the task from the array.
     * 
     * @param Task The task to remove.
     */
    public abstract void remove(Task task);

    protected int indexOf(Task task) {
        synchronized (_Lock) {
            int index = -1;
            int size = _AllTaskItems.size();
            for (int i = 0; i < size; i++) {
                Object item = _AllTaskItems.get(i);
                if (item instanceof Task && item.equals(task)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
    }

    protected int indexOf(Object item) {
        int index = -1;
        new NotImplementException().printStackTrace();
        return index;
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (_Lock) {
            _TaskList.clear();
            _AllTaskItems.clear();
        }
        invalidate();
        if (_NotifyOnChange)
            notifyDataSetChanged();
    }

    protected abstract void invalidate();

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        _NotifyOnChange = true;
    }

    @Override
    public int getCount() {
        if (size() == 0) {
            return 1;
        }
        return size();
    }

    @Override
    public Object getItem(int position) {
        return _AllTaskItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
