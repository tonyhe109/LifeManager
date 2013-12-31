
package com.lifemanager.data.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifemanager.R;
import com.lifemanager.data.AbsTaskGroup;
import com.lifemanager.data.PriorityGroup;
import com.lifemanager.data.Task;
import com.lifemanager.data.TaskDataHelper;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.TaskPriority;
import com.lifemanager.data.TaskTiming;
import com.lifemanager.data.TimingGroup;
import com.lifemanager.exception.NotImplementException;

public class SingleDayAdapter<ViewItem> extends TaskArrayAdapter {

    private AbsTaskGroup[] pGroup;
    private AbsTaskGroup[] tGroup;
    protected View _NoItemView;
    
    private static TaskTouchAction _TaskActionListener = new TaskTouchAction();

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
                priorityInvalidate();
                break;
            case ORDER_TIME:
                timingInvalidate();
                break;
        }
    }

    private void priorityInvalidate() {
        synchronized (_Lock) {
            int size = _TaskList.size();
            if (size > 0) {
                _TaskList.sort(ORDER_PRIORITY);
                int lastPriority = -1;
                int lastIndex = 0;
                int layer = 0;
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
                                layer = 1;
                                pGroup[0].buildGroup(lastIndex, i - 1);
                                lastIndex = i;
                                _AllTaskItems.add(pGroup[1]);
                                break;
                            case TaskPriority.PRIORITY_LOW:
                                layer = 2;
                                pGroup[1].buildGroup(lastIndex, i - 1);
                                lastIndex = i;
                                _AllTaskItems.add(pGroup[2]);
                                break;
                        }
                    }
                    _AllTaskItems.add(task);
                }
                if (lastIndex != size - 1) {
                    pGroup[layer].buildGroup(lastIndex, size - 1);
                }
            }
        }
    }

    private void timingInvalidate() {
        synchronized (_Lock) {
            int size = _TaskList.size();
            if (size > 0) {
                _TaskList.sort(ORDER_TIME);
                int lastTiming = -1;
                int lastIndex = 0;
                int layer = 0;
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
                                layer = 1;
                                tGroup[0].buildGroup(lastIndex, i - 1);
                                lastIndex = i;
                                _AllTaskItems.add(tGroup[1]);
                                break;
                            case TaskTiming.TIMING_NIGHT:
                                layer = 2;
                                tGroup[1].buildGroup(lastIndex, i - 1);
                                lastIndex = i;
                                _AllTaskItems.add(tGroup[2]);
                                break;
                        }
                    }
                    _AllTaskItems.add(task);
                }
                if (lastIndex != size - 1 && layer != 0) {
                    tGroup[layer].buildGroup(lastIndex, size - 1);
                }
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        Object viewItem = getItem(position);
        //
        if (viewItem instanceof Task) {
            convertView = buildTaskLine(parent.getContext(), (Task) viewItem);
        } else if (viewItem instanceof AbsTaskGroup) {
            convertView = buildGroupLine(parent.getContext(), (AbsTaskGroup) viewItem);
        }
        return convertView;

    }

    private View buildTaskLine(Context context, Task task) {
        View line = null;
        line = LayoutInflater.from(context).inflate(
                R.layout.line_simple_sd_task, null);
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
        // add 
        line.setOnTouchListener((OnTouchListener) _TaskActionListener);
        return line;
    }

    private View buildGroupLine(Context context, AbsTaskGroup group) {
        View line = null;
        line = LayoutInflater.from(context).inflate(
                R.layout.line_simple_sd_group, null);
        switch (_Order) {
            case ORDER_PRIORITY:
            case ORDER_TIME:
                TextView dotPriority = (TextView) line.findViewById(R.id.time_dot_priority);
                TextView title = (TextView) line.findViewById(R.id.task_priority);
                dotPriority.setText("" + group.getCount());
                title.setText(group.getText());
                break;
        }
        return line;
    }
    
    private static class TaskTouchAction implements OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            
            System.out.println("==onTouch==v:"+v+"==event:"+event);
            
            return true;
        }
        
    }
}
