
package com.lifemanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskList extends ArrayList<Task> implements TaskOrderMode {

    private static final long serialVersionUID = -7577737930358335279L;

    public static final Comparator<Task> COMP_PRIORITY = new PriorityTimeComparator();
    public static final Comparator<Task> COMP_TIME = new TimeComparator();

    private int _Order = -1;

    public TaskList() {
        this(-1);
    }

    public TaskList(int order) {
        if (order != ORDER_TIME)
            _Order = ORDER_TIME;
        else
            _Order = ORDER_PRIORITY;
    }

    public void sort(Comparator<Task> comp) {
        Collections.sort(this, comp);
    }

    public void addArray(Task[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            add(array[i]);
        }
        invalidate();
    }

    protected void invalidate() {
        System.out.println("Task List invalidate ... ");
        switch (_Order) {
            case ORDER_PRIORITY:
                Collections.sort(this, COMP_PRIORITY);
                break;
            case ORDER_TIME:
                Collections.sort(this, COMP_TIME);
                break;
        }
    }

    public void sort(int sort) {
        if (sort == _Order || (sort != ORDER_PRIORITY && sort != ORDER_TIME)) {
            return;
        }
        switch (sort) {
            case ORDER_PRIORITY:
                Collections.sort(this, COMP_PRIORITY);
                break;
            case ORDER_TIME:
                Collections.sort(this, COMP_TIME);
                break;
        }

    }

    private static class PriorityTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task lhs, Task rhs) {
            long priority = lhs.getPriority() - rhs.getPriority();
            if (priority == 0) {
                priority = lhs.getStartTime().getTime() - rhs.getStartTime().getTime();
                return priority > 0 ? 1 : -1;
            }
            return (int) priority;
        }
    }

    private static class TimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task lhs, Task rhs) {
            long result = lhs.getStartTime().getTime() - rhs.getStartTime().getTime();
            return result > 0 ? 1 : -1;
        }
    }

}
