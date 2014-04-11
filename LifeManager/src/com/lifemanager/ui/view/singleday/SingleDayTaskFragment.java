
package com.lifemanager.ui.view.singleday;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lifemanager.R;
import com.lifemanager.data.Task;
import com.lifemanager.data.TaskList;
import com.lifemanager.data.database.DataBaseManager;
import com.lifemanager.ui.TaskPanelActivity;
import com.lifemanager.ui.view.AbsTaskFragment;

public class SingleDayTaskFragment extends AbsTaskFragment {

    private SingleDayAdapter adapter;
    private ListView _taskListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //
        _taskListView = (ListView) _MainView.findViewById(R.id.list);
        // add click logic
        _button_menu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (adapter != null) {
                    LOG.debug("SingleDayAdapter.onMenuActionIconClick");
                    ((TaskPanelActivity) getActivity()).onMenuActionIconClick();
                }
            }
        });
        // 
        _button_switch_mode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LOG.debug("SingleDayAdapter.onMenuActionIconClick");
                ((TaskPanelActivity) getActivity()).onSwitchModeButtonClick();
                adapter.switchOrder();
            }
        });
        //_taskListView.onTouchEvent(ev)
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TaskList tList = new TaskList();
        Date now = new Date();
        _taskPanelTitle.setText("Singal Day Tasks");
        //, Now :" + now
        LOG.debug("Singal Day Tasks, Now :" + now);
        Calendar calendar = new GregorianCalendar();
        calendar.set(now.getYear(), now.getMonth(), now.getDay() - 1, 0, 0, 0);
        Long start = calendar.getTime().getTime();
        LOG.debug("start:" + calendar.getTime());
        Task[] today = DataBaseManager.getDataBase().queryTasksAsArray(start, start + 24 * 60 * 60 * 1000);
        //Task[] today = DataBaseManager.getDataBase().queryAllTasks();
        tList.addArray(today);
        adapter = new SingleDayAdapter(getActivity(), tList);
        _taskListView.setAdapter(adapter);
    }

    @Override
    public int getTaskFragmentID() {
        return AbsTaskFragment.SINGLE_DAY_TASK_VIEW;
    }

}
