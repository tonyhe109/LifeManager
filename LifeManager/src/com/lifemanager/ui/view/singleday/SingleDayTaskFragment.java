
package com.lifemanager.ui.view.singleday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifemanager.R;
import com.lifemanager.data.Task;
import com.lifemanager.data.TaskDataHelper;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.TaskPriority;
import com.lifemanager.data.ui.SingleDayAdapter;
import com.lifemanager.ui.view.AbsTaskFragment;

public class SingleDayTaskFragment extends AbsTaskFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        LOG.debug("SingalDayTaskFragment.onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LOG.debug("SingalDayTaskFragment.onActivityCreated");
        _taskPanelTitle.setText("Singal Day Tasks");
        LOG.debug("TaskFragment.onActivityCreated");
        TaskList tList = new TaskList();
        tList.addArray(TaskDataHelper.getTestTaskArray());
        BaseAdapter adapter = new SingleDayAdapter(getActivity(), tList);
        _taskListView.setAdapter(adapter);
    }

    @Override
    public int getTaskFragmentID() {
        return AbsTaskFragment.SINGLE_DAY_TASK_VIEW;
    }

}
