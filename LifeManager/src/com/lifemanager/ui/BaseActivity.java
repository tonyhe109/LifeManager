
package com.lifemanager.ui;

import tony.support.improve.UserActionCollector;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.lifemanager.LifeManagerModule;
import com.lifemanager.R;

@SuppressLint("ShowToast")
public abstract class BaseActivity extends FragmentActivity {

    public static final LifeManagerModule LOADING = new LifeManagerModule(0, "LOADING");
    public static final LifeManagerModule MAIN_TASK_PANEL = new LifeManagerModule(1, "MAIN_TASK_PANEL");
    public static final LifeManagerModule DEVELOPER_HELPER = new LifeManagerModule(1984, "DEVELOPER_HELPER");

    private static MessageHandler msgHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToast();
        loadGlobleSettings();
    }

    protected void onCreate(Bundle savedInstanceState, LifeManagerModule module) {
        super.onCreate(savedInstanceState);
        initToast();
        loadGlobleSettings();
        UserActionCollector.activityOnCreate(module);
    }

    private void loadGlobleSettings() {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void initToast() {
       // msgHandler = new MessageHandler(BaseActivity.this);
    }

    public void showSimpleMessage(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String msg) {
        //        msgHandler.setText(msg);
        //        msgHandler.show();
    }

    class MessageHandler extends Toast {

        private TextView msgView;

        public MessageHandler(Context context) {
            super(context);
            View toastView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_default, null);
            msgView = (TextView) toastView.findViewById(R.id.toast_msg);
            setView(toastView);
            setDuration(Toast.LENGTH_SHORT);
            setGravity(Gravity.TOP, 0, 0);
        }

        public void setText(String msg) {
            msgView.setText(msg);
        }

    }

}
