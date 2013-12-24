
package com.lifemanager.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.ui.TaskPanelActivity;
import com.lifemanager.ui.view.singleday.SingleDayTaskFragment;

public abstract class AbsTaskFragment extends Fragment {

    public static final String TAG = "Task Fragment";
    public static final int DEFAULT_TASK_VIEW = 0;
    public static final int SINGLE_DAY_TASK_VIEW = 1;
    public static final int WEEKLY_TASK_VIEW = 2;

    protected static final Logger LOG = Logger.getLogger(TAG);

    protected ImageView _button_switch_mode;
    protected ImageView _button_menu;
    protected TextView _taskPanelTitle;
    protected ListView _taskListView;
    protected int _TaskFragmentID = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        LOG.debug("TaskFragment.onCreateView");
        View mView = inflater.inflate(R.layout.task_frame, null);
        _button_switch_mode = (ImageView) mView
                .findViewById(R.id.ic_switch_mode);
        _button_menu = (ImageView) mView.findViewById(R.id.ic_menu);
        _taskPanelTitle = (TextView) mView.findViewById(R.id.iv_title);
        _taskListView = (ListView) mView.findViewById(R.id.list);
        return mView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        _TaskFragmentID = getTaskFragmentID();
        super.onActivityCreated(savedInstanceState);
    }

    public abstract int getTaskFragmentID();
    
    public static TaskFragmentManager getManager() {
        return _TaskFragmentManager;
    }

    private static final TaskFragmentManager _TaskFragmentManager = new TaskFragmentManager();
    private static AbsTaskFragment _SingalDayTaskFragment;
    private static AbsTaskFragment _WeeklyTaskFragment;
    private static int _CurrentTaskFragmentViewID = -1;

    public static class TaskFragmentManager {

        private TaskFragmentManager() {
            _SingalDayTaskFragment = new SingleDayTaskFragment();
            _WeeklyTaskFragment = new WeeklyTaskFragment();
        }

        private AbsTaskFragment getDefaultTaskFragment() {

            return getSingleDayTaskFragment();
        }

        private AbsTaskFragment getSingleDayTaskFragment() {
            _CurrentTaskFragmentViewID = SINGLE_DAY_TASK_VIEW;
            return _SingalDayTaskFragment;
        }

        private AbsTaskFragment getWeeklyTaskFragment() {
            _CurrentTaskFragmentViewID = WEEKLY_TASK_VIEW;
            return _WeeklyTaskFragment;
        }

        public AbsTaskFragment getTaskFragmentByID(int id) {

            switch (id) {
                case DEFAULT_TASK_VIEW:
                    return getDefaultTaskFragment();

                case SINGLE_DAY_TASK_VIEW:
                    return getSingleDayTaskFragment();

                case WEEKLY_TASK_VIEW:
                    return getWeeklyTaskFragment();
            }
            return null;
        }

        public int getCurrentTaskFragmentID() {
            return _CurrentTaskFragmentViewID;
        }

        public boolean isCurrentShowTaskView(int current) {

            return _CurrentTaskFragmentViewID == current;
        }

    }

}
