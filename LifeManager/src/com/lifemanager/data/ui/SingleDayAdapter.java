
package com.lifemanager.data.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifemanager.R;
import com.lifemanager.data.PriorityGroup;
import com.lifemanager.data.Task;
import com.lifemanager.data.TaskGroup;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.TaskPriority;
import com.lifemanager.data.TaskTiming;
import com.lifemanager.data.TimingGroup;

public class SingleDayAdapter extends TaskArrayAdapter {

    private TaskGroup[] pGroup;
    private TaskGroup[] tGroup;
    private View _NoItemView;

    public SingleDayAdapter(Context context, TaskList tList) {
        super(context, tList);
        initGroup();
    }

    private void initGroup() {
        pGroup = new PriorityGroup[3];
        pGroup[0] = new PriorityGroup(0, TaskPriority.PRIORITY_HIGH);
        pGroup[1] = new PriorityGroup(1, TaskPriority.PRIORITY_MIDDLE);
        pGroup[2] = new PriorityGroup(2, TaskPriority.PRIORITY_LOW);
        tGroup = new TimingGroup[3];
        tGroup[0] = new TimingGroup(0, TaskTiming.TIMING_MORNING);
        tGroup[1] = new TimingGroup(1, TaskTiming.TIMING_AFTERNOON);
        tGroup[2] = new TimingGroup(2, TaskTiming.TIMING_NIGHT);
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public void remove(Task task) {

    }

    @Override
    protected void invalidate() {

        switch (_Order) {
            case ORDER_PRIORITY:
                int size = _TaskList.size();
                if (size > 0) {

                }
                break;
            case ORDER_TIME:

                break;

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        
        _NoItemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.task_panel_no_item, null);
        convertView = _NoItemView;
        
        
        return convertView;

    }
}
