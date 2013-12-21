
package com.lifemanager.data;

public class PriorityGroup extends AbsTaskGroup {

    private int _Priority;

    public PriorityGroup(int id, int priority) {
        super(id);
        if (priority != PRIORITY_LOW && priority != PRIORITY_MIDDLE && priority != PRIORITY_HIGH) {
            throw new IllegalStateException("Task illegal priority value");
        }
        _Priority = priority;
        switch (priority) {
            case PRIORITY_HIGH:
                _Text = "优先级 高";
                break;
            case PRIORITY_MIDDLE:
                _Text = "优先级 中";
                break;
            case PRIORITY_LOW:
                _Text = "优先级 低";
                break;
        }
    }

    public int getPriority() {
        return _Priority;
    }

}
