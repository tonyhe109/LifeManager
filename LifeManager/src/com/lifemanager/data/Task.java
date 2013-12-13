package com.lifemanager.data;

import java.util.Date;

public interface Task extends TaskPriority {

	public String getTitle();

	public int getPriority();

	public String getContent();

	public Date getStartTime();

	public Date getEndTime();

	public int getDerution();

	public Object getComments();

	public Date getAlarmTime();

}
