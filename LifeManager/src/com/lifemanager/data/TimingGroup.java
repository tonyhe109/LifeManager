
package com.lifemanager.data;

public class TimingGroup extends AbsTaskGroup {

    private int _Timing;

    public TimingGroup(int id, int timing) {
        super(id);
        if (timing != TIMING_NIGHT && timing != TIMING_AFTERNOON && timing != TIMING_MORNING) {
            throw new IllegalStateException("Task illegal timing value");
        }

        _Timing = timing;
        switch (_Timing) {
            case PRIORITY_HIGH:
                _Text = "上午";
                break;
            case TIMING_AFTERNOON:
                _Text = "下午";
                break;
            case TIMING_NIGHT:
                _Text = "晚上";
                break;
        }
    }

    public int getTiming() {

        return _Timing;
    }

}
