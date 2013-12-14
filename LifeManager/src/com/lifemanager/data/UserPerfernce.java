
package com.lifemanager.data;

public class UserPerfernce implements TaskPriority {

    private static final UserPerfernce _perfernce = new UserPerfernce();
    /**
     * default task duration half hour
     */
    private static int _DefaultTaskDuration = 1000 * 60 * 30;

    /**
     * default task start time delay time via the now<br>
     * say 5 minutes
     */
    private static int _DefaultTaskStartTimeDelay = 1000 * 60 * 5;

    /**
     * min task duration ,unit: ms<br>
     * say 15 minutes
     */
    private static int _MinTaskDuration = 1000 * 60 * 15;

    /**
     * max task duration<br>
     * say 15 minutes
     */
    private static int _MaxTaskDuration = 1000 * 60 * 60 * 6;

    /**
     * default task priority <br>
     * say PRIORITY_MIDDLE
     * 
     * @see TaskPriority
     */
    private static int _DefaultTaskPriority = TaskPriority.PRIORITY_MIDDLE;

    private UserPerfernce() {

    }

    public static UserPerfernce getUserPrefence() {
        return _perfernce;
    }

    public static int getDefaultTaskStartTimeDelay() {
        return _DefaultTaskStartTimeDelay;
    }

    public static String getTips() {
        return "";
    }
    
    public static int getDefaultTaskDuration() {
        return _DefaultTaskDuration;
    }

    public static int getMinTaskDuration() {
        return _MinTaskDuration;
    }

    public static int getMaxTaskDuration() {
        return _MaxTaskDuration;
    }

    public static int getDefaultTaskPriority() {
        return _DefaultTaskPriority;
    }

}
