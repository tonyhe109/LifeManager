
package com.lifemanager.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lifemanager.data.TaskList;
import com.lifemanager.ui.data.SDayTaskArrayAdapter;

public class SingalDayTaskFragment extends TaskFragment {

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
        BaseAdapter adapter = new SDayTaskArrayAdapter(getActivity(), new TaskList(0));
        _taskListView.setAdapter(adapter);
    }

    @Override
    public int getTaskFragmentID() {
        return TaskFragment.SINGLE_DAY_TASK_VIEW;
    }
}
