
package com.lifemanager.data;

public interface ViewItem<T> {

    public static final int TASK_ITEM = 0;
    public static final int GROUP_ITEM = 0;

    public int getViewItemType();

    public T getViewItem();

}
