
package com.lifemanager.data;

import java.util.Date;

public interface Task extends TaskPriority {

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

    public Date getStartTime();

    public Date getEndTime();

    public long getDuration();

    public Object getComments();

    public Date getAlarmTime();

}
