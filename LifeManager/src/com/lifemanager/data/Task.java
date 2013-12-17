
package com.lifemanager.data;

import java.util.Date;

public interface Task extends TaskPriority, TaskTiming {

    /**
     * it's task's unique id
     * 
     * @return long value of the task create time
     */
    public long getUnique();

    /**
     * Task tile , should not be null
     * 
     * @return
     */
    public String getTitle();

    /**
     * task priority
     * 
     * @see TaskPriority
     * @return
     */
    public int getPriority();

    /**
     * task Content
     * 
     * @see TaskPriority
     * @return
     */
    public String getContent();

    /**
     * @return
     */
    public Date getStartTime();

    /**
     * @return
     */
    public Date getEndTime();

    /**
     * @return
     */
    public long getDuration();

    /**
     * @return
     */
    public Object getComments();

    /**
     * @return
     */
    public Date getAlarmTime();

    /**
     * @return
     */
    public int getTaskState();

}
