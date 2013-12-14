
package com.lifemanager.data;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskList extends ArrayList<Task> {

    private static final long serialVersionUID = -7577737930358335279L;

    public TaskList() {

    }

    private void invalidate() {

    }

    static class PriorityTimeComparator implements Comparator<Task> {
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

    static class TimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task lhs, Task rhs) {
            long result = lhs.getStartTime().getTime() - rhs.getStartTime().getTime();
            return result > 0 ? 1 : -1;
        }
    }

}
