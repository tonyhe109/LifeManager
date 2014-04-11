
package com.lifemanager.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.lifemanager.data.Task;
import com.lifemanager.data.TaskOrderMode;

public class TaskInfoView extends TextView implements TaskOrderMode {

    private Task _Task;
    private int _Mode = MODE_SIMPLE;

    public TaskInfoView(Context context) {
        super(context);
    }

    public void setTask(Task task) {
        _Task = task;
    }

    public void setMode(int mode) {
        _Mode = mode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
