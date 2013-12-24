
package com.lifemanager.data.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifemanager.R;
import com.lifemanager.data.AbsTaskGroup;
import com.lifemanager.data.PriorityGroup;
import com.lifemanager.data.Task;
import com.lifemanager.data.TaskDataHelper;
import com.lifemanager.data.TaskGroup;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.TaskPriority;
import com.lifemanager.data.TaskTiming;
import com.lifemanager.data.TimingGroup;
import com.lifemanager.exception.NotImplementException;

public class SingleDayAdapter extends TaskArrayAdapter {

    private AbsTaskGroup[] pGroup;
    private AbsTaskGroup[] tGroup;
    protected View _NoItemView;

    public SingleDayAdapter(Context context, TaskList tList) {
        super(context, tList);
    }

    @Override
    protected void init() {
        pGroup = new PriorityGroup[3];
        pGroup[0] = new PriorityGroup(1, TaskPriority.PRIORITY_HIGH);
        pGroup[1] = new PriorityGroup(2, TaskPriority.PRIORITY_MIDDLE);
        pGroup[2] = new PriorityGroup(3, TaskPriority.PRIORITY_LOW);
        tGroup = new TimingGroup[3];
        tGroup[0] = new TimingGroup(1, TaskTiming.TIMING_MORNING);
        tGroup[1] = new TimingGroup(2, TaskTiming.TIMING_AFTERNOON);
        tGroup[2] = new TimingGroup(3, TaskTiming.TIMING_NIGHT);
    }

    @Override
    public void add(Task task) {
        //TODO
        new NotImplementException().printStackTrace();
    }

    @Override
    public void remove(Task task) {
        //TODO
        new NotImplementException().printStackTrace();
    }

    @Override
    protected void invalidate() {
        _AllTaskItems.clear();
        switch (_Order) {
            case ORDER_PRIORITY:
                synchronized (_Lock) {
                    int size = _TaskList.size();
                    if (size > 0) {
                        _TaskList.sort(ORDER_PRIORITY);
                        int lastPriority = -1;
                        int lastIndex = 0;
                        for (int i = 0; i < size; i++) {
                            Task task = _TaskList.get(i);
                            int tempPriority = task.getPriority();
                            if (tempPriority != lastPriority) {
                                lastPriority = tempPriority;
                                switch (lastPriority) {
                                    case TaskPriority.PRIORITY_HIGH:
                                        _AllTaskItems.add(pGroup[0]);
                                        break;
                                    case TaskPriority.PRIORITY_MIDDLE:
                                        pGroup[0].buildGroup(lastIndex, i - 1);
                                        lastIndex = i;
                                        _AllTaskItems.add(pGroup[1]);
                                        break;
                                    case TaskPriority.PRIORITY_LOW:
                                        pGroup[1].buildGroup(lastIndex, i - 1);
                                        lastIndex = i;
                                        _AllTaskItems.add(pGroup[2]);
                                        break;
                                }
                            }
                            _AllTaskItems.add(task);
                        }
                        if (lastIndex != size - 1) {
                            pGroup[2].buildGroup(lastIndex, size - 1);
                        }
                    }
                }
                break;
            case ORDER_TIME:
                synchronized (_Lock) {
                    int size = _TaskList.size();
                    if (size > 0) {
                        System.out.println("################# ORDER_TIME ###################");
                        _TaskList.sort(ORDER_TIME);
                        int lastTiming = -1;
                        int lastIndex = 0;
                        for (int i = 0; i < size; i++) {
                            Task task = _TaskList.get(i);
                            int tempTiming = task.getTiming();
                            if (tempTiming != lastTiming) {
                                lastTiming = tempTiming;
                                switch (lastTiming) {
                                    case TaskTiming.TIMING_MORNING:
                                        _AllTaskItems.add(tGroup[0]);
                                        break;
                                    case TaskTiming.TIMING_AFTERNOON:
                                        pGroup[0].buildGroup(lastIndex, i - 1);
                                        lastIndex = i;
                                        _AllTaskItems.add(tGroup[1]);
                                        break;
                                    case TaskTiming.TIMING_NIGHT:
                                        pGroup[1].buildGroup(lastIndex, i - 1);
                                        lastIndex = i;
                                        _AllTaskItems.add(tGroup[2]);
                                        break;
                                }
                            }
                            _AllTaskItems.add(task);
                        }
                        if (lastIndex != size - 1) {
                            tGroup[2].buildGroup(lastIndex, size - 1);
                        }
                    }
                }
                break;

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View line = null;
        Object viewItem = getItem(position);
        //
        if (viewItem instanceof Task) {
            line = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.line_simple_sd_task, null);
            Task task = (Task) viewItem;
            TextView time = (TextView) line.findViewById(R.id.task_time);
            ImageView dot = (ImageView) line.findViewById(R.id.time_dot);
            TextView title = (TextView) line.findViewById(R.id.task_title);
            //set time
            String tt = TaskDataHelper.getTimeString(task.getStartTime(), "HH:mm");
            time.setText(tt);
            //set task
            // set icon 
            int priority = task.getPriority();
            switch (priority) {
                case TaskPriority.PRIORITY_HIGH:
                    dot.setBackgroundResource(R.drawable.timedot_r1);
                    title.setBackgroundResource(R.drawable.record_item_bg_r1);
                    break;
                case TaskPriority.PRIORITY_MIDDLE:
                    dot.setBackgroundResource(R.drawable.timedot_r2);
                    title.setBackgroundResource(R.drawable.record_item_bg_r2);
                    break;
                case TaskPriority.PRIORITY_LOW:
                    dot.setBackgroundResource(R.drawable.timedot_r4);
                    title.setBackgroundResource(R.drawable.record_item_bg_r4);
                    break;

            }
            title.setText(task.getTitle());
            convertView = line;
        } else if (viewItem instanceof TaskGroup) {
            line = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.line_simple_sd_group, null);
            switch (_Order) {
                case ORDER_PRIORITY:
                case ORDER_TIME:
                    AbsTaskGroup group = (AbsTaskGroup) viewItem;
                    TextView dotPriority = (TextView) line.findViewById(R.id.time_dot_priority);
                    TextView title = (TextView) line.findViewById(R.id.task_priority);
                    dotPriority.setText("" + group.getCount());
                    title.setText(group.getText());
                    break;

            }
            convertView = line;
        }
        return convertView;

    }
}
