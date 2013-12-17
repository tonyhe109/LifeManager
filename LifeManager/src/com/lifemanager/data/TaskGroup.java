
package com.lifemanager.data;

/**
 * @author tony.he
 */
public interface TaskGroup extends TaskPriority, TaskTiming {

    /**
     * the unique index of each Task array adapter
     * 
     * @return integer
     */
    public int getGruopID();

    /**
     * Task number of this group
     * 
     * @return integer
     */
    public int getCount();

    /**
     * first index of this Group via Task List
     * 
     * @return integer
     */
    public int getFirstTaskIndex();

    /**
     * this text will show on the Group item view.
     * 
     * @return String
     */
    public String getText();

    /**
     * this image will show on Group item view.
     * 
     * @return integer
     */
    public int getIconImageRes();
    

}
