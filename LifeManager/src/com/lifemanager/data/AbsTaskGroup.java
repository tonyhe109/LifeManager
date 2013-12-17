
package com.lifemanager.data;

import com.lifemanager.exception.NotImplementException;

public abstract class AbsTaskGroup implements TaskGroup {

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
        new NotImplementException().printStackTrace();
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

}
