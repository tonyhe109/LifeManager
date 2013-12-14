
package com.lifemanager.data;

import java.util.Date;

public class LmTask implements Task {

    private final long _UniqueID;
    protected String _Title = "";
    protected int _Priority = PRIORITY_LOW;
    protected String _Content = "";
    protected Date _StartTime;
    protected Date _EndTime;
    protected int _Duration;

    /**
     * LmTask priority base constructor, Task will construct by priority ,<br>
     * <b>Start Time</b> set by perfernce default delay time .<br>
     * <b>End Time</b> set by default duration & start time .
     * 
     * @param priority
     */
    public LmTask(int priority) {
        _UniqueID = System.currentTimeMillis();
        _Priority = priority;
        // set start time
        int delay = UserPerfernce.getDefaultTaskStartTimeDelay();
        _StartTime = new Date(_UniqueID + delay);
        // set end time
        int duration = UserPerfernce.getDefaultTaskDuration();
        _EndTime = new Date(_StartTime.getTime() + duration);
    }

    /**
     * LmTask time base constructor<br>
     * <b>Priority</b> set by perfernce default delay time .<br>
     * <b>End Time</b> set by default duration & start time .
     * 
     * @param start
     */
    public LmTask(Date start) {
        _UniqueID = System.currentTimeMillis();
        _Priority = UserPerfernce.getDefaultTaskPriority();
        // set start time
        _StartTime = start;
        // set end time
        int duration = UserPerfernce.getDefaultTaskDuration();
        _EndTime = new Date(_StartTime.getTime() + duration);
    }

    /**
     * LmTask constructor<br>
     * <b>End Time</b> set by default duration & start time .
     * 
     * @param priority
     * @param start
     */
    public LmTask(int priority, Date start) {
        _UniqueID = System.currentTimeMillis();
        _Priority = priority;
        // set start time
        _StartTime = start;
        // set end time
        int duration = UserPerfernce.getDefaultTaskDuration();
        _EndTime = new Date(_StartTime.getTime() + duration);
    }

    @Override
    public final long getUnique() {
        return _UniqueID;
    }

    @Override
    public final String getTitle() {
        return _Title;
    }

    @Override
    public final int getPriority() {
        return _Priority;
    }

    @Override
    public final String getContent() {
        return _Content;
    }

    @Override
    public final Date getStartTime() {
        return _StartTime;
    }

    @Override
    public final Date getEndTime() {
        return _EndTime;
    }

    @Override
    public long getDuration() {
        return _Duration;
    }

    @Override
    public final Object getComments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Date getAlarmTime() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTitle(String title) {
        if (title == null) {
            _Title = "";
            return;
        }
        this._Title = title;
    }

    public void setPriority(int priority) {
        if (_Priority != TaskPriority.PRIORITY_HIGH && _Priority != TaskPriority.PRIORITY_MIDDLE
                && _Priority != TaskPriority.PRIORITY_MIDDLE) {
            throw new IllegalStateException("Task illegal priority value");
        }
        this._Priority = priority;
    }

    public void setContent(String content) {
        if (content == null) {
            _Content = "";
        }
        this._Content = content;
    }

    public void set_StartTime(Date _StartTime) {
        this._StartTime = _StartTime;
    }

    public void set_EndTime(Date _EndTime) {
        this._EndTime = _EndTime;
    }

    private static final int BASE_START_END = 0;
    private static final int BASE_START_DURATION = 1;
    private static final int BASE_DURATION_END = 2;

    protected final void invalidate() {
        invalidate(BASE_START_END);
    }

    protected void invalidate(int base) {
        // TODO
        switch (base) {
            case BASE_START_DURATION:
                break;
            case BASE_DURATION_END:
                break;
            case BASE_START_END:
            default:
                break;
        }
    }

}
