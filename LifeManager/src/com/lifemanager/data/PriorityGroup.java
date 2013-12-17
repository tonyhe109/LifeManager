
package com.lifemanager.data;

public class PriorityGroup extends AbsTaskGroup {

    private int _Priority;

    public PriorityGroup(int id, int priority) {
        super(id);
        if (priority != PRIORITY_LOW && priority != PRIORITY_MIDDLE && priority != PRIORITY_HIGH) {
            throw new IllegalStateException("Task illegal priority value");
        }
        _Priority = priority;
    }

    public int getPriority() {

        return _Priority;
    }

}
