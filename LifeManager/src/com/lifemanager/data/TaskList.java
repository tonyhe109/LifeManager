
package com.lifemanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskList extends ArrayList<Task> implements TaskOrderMode {

    private static final long serialVersionUID = -7577737930358335279L;

    public static final Comparator<Task> COMP_PRIORITY = new PriorityTimeComparator();
    public static final Comparator<Task> COMP_TIME = new TimeComparator();

    private int _Order = -1;

    public TaskList(int order) {
        _Order = order;
    }

    public void sort(Comparator<Task> comp) {
        Collections.sort(this, comp);
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
            long result = lhs.getPriority() - lhs.getPriority();
            if (result == 0) {
                result = lhs.getStartTime().getTime() - rhs.getStartTime().getTime();
                return result > 0 ? 1 : -1;
            } else {
                return (int) result;
            }
        }
    }

    private static class TimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task lhs, Task rhs) {
            long result = lhs.getStartTime().getTime() - rhs.getStartTime().getTime();
            return result > 0 ? 1 : -1;
        }
    }

    // abstract class TaskListDataChangeListener {
    //
    // public abstract void taskDataChange();
    //
    // }

}
