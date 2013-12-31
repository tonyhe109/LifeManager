
package com.lifemanager.data;

public abstract class AbsTaskGroup implements TaskGroup, ViewItem<AbsTaskGroup> {

    private final int _GruopIndex;
    protected int _Count;
    protected int _FirstTaskIndex;
    protected String _Text;
    protected int _IconImageRes;

    public AbsTaskGroup(int id) {
        _GruopIndex = id;
    }

    public void buildGroup(int first, int end) {
        _FirstTaskIndex = first;
        _Count = (end - first) > 0 ? end - first + 1 : 0;
    }

    @Override
    public int getGruopID() {
        return _GruopIndex;
    }

    @Override
    public int getCount() {
        return _Count;
    }

    @Override
    public int getFirstTaskIndex() {
        return _FirstTaskIndex;
    }

    @Override
    public String getText() {
        return _Text;
    }

    @Override
    public int getIconImageRes() {
        return _IconImageRes;
    }

    @Override
    public int getViewItemType() {
        return GROUP_ITEM;
    }

    @Override
    public AbsTaskGroup getViewItem() {
        return this;
    }

}
