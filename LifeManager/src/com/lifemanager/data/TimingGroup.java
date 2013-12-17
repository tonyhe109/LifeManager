
package com.lifemanager.data;

public class TimingGroup extends AbsTaskGroup {

    private int _Timing;

    public TimingGroup(int id, int timing) {
        super(id);
        if (timing != TIMING_NIGHT && timing != TIMING_AFTERNOON && timing != TIMING_MORNING) {
            throw new IllegalStateException("Task illegal timing value");
        }
        _Timing = timing;
    }

    public int getTiming() {

        return _Timing;
    }

}
