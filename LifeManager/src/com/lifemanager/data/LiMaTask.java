
package com.lifemanager.data;

import java.util.Date;

public class LiMaTask implements Task {

    private final long _UniqueID;
    protected String _Title = "";
    protected int _Priority = PRIORITY_LOW;
    protected String _Content = "";
    protected Date _StartTime;
    protected Date _EndTime;
    protected int _Duration;

    private int _State = 0;

    /**
     * LmTask priority base constructor, Task will construct by priority ,<br>
     * <b>Start Time</b> set by perfernce default delay time .<br>
     * <b>End Time</b> set by default duration & start time .
     * 
     * @param priority
     */
    public LiMaTask(int priority) {
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
    public LiMaTask(Date start) {
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
    public LiMaTask(int priority, Date start) {
        _UniqueID = System.currentTimeMillis();
        _Priority = priority;
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
    public LiMaTask(int priority, Date start, String title) {
        this(priority, start);
        _Title = title;
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
    public final int getTiming() {
        int hours = _StartTime.getHours();
        //TODO need to improve 
        switch (hours) {
       
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return TaskTiming.TIMING_MORNING;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                return TaskTiming.TIMING_AFTERNOON;
            case 21:
            case 22:
            case 23:
            case 24:
                return TaskTiming.TIMING_AFTERNOON;

        }
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

    @Override
    public int getTaskState() {
        return _State;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Task) && ((Task) o).getUnique() == getUnique();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Task[" + _UniqueID + "] Priority:" + _Priority + " start:" + _StartTime + " duration:" + getDuration());
        if (_Title != null && !_Title.equals("")) {
            sb.append(" Title:" + _Title);
        }
        return sb.toString();

    }
}
