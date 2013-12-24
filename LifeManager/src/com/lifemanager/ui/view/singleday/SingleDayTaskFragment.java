
package com.lifemanager.ui.view.singleday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

import com.lifemanager.data.TaskDataHelper;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.ui.SingleDayAdapter;
import com.lifemanager.ui.TaskPanelActivity;
import com.lifemanager.ui.view.AbsTaskFragment;

public class SingleDayTaskFragment extends AbsTaskFragment {

    private SingleDayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // add click logic
        _button_menu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (adapter != null) {
                    LOG.debug("SingleDayAdapter.onMenuActionIconClick");
                    ((TaskPanelActivity) getActivity()).onMenuActionIconClick();
                }
            }
        });

        _button_switch_mode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LOG.debug("SingleDayAdapter.onMenuActionIconClick");
                ((TaskPanelActivity) getActivity()).onSwitchModeButtonClick();
                adapter.switchOrder();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _taskPanelTitle.setText("Singal Day Tasks");
        TaskList tList = new TaskList();
        tList.addArray(TaskDataHelper.getTestTaskArray());
        adapter = new SingleDayAdapter(getActivity(), tList);
        _taskListView.setAdapter(adapter);
    }

    @Override
    public int getTaskFragmentID() {
        return AbsTaskFragment.SINGLE_DAY_TASK_VIEW;
    }

}
