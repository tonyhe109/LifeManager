package com.lifemanager.phone;

import java.io.File;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;

import com.lifemanager.R;
import com.lifemanager.logging.Logger;
import com.lifemanager.phone.ui.TaskPanelActivity;
import com.lifemanager.util.DeviceHelper;
import com.lifemanager.util.Util;

public class BootActivity extends LMActivity {

	private static final int MIN_SHOW_TIME = 2000;
	private static final int MAX_SHOW_TIME = 10000;

	private static final Logger LOG = Logger.getLogger(BootActivity.class);

	@Override
	public void onBackPressed() {
		LOG.debug("onBackPressed ");
		// TODO
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, LMActivity.LOADING);

		if (isNormalStartup()) {
			LOG.debug("onCreate.");
			setContentView(R.layout.loading);
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				mStartupTask.execute();
				mTimeoutTask.execute();
			} else {
				mStartupTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				mTimeoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		} else {
			LOG.info("application exit because of special startup.");
			exit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mStartupTask.cancel(true);
		mTimeoutTask.cancel(true);
	}

	private void exit() {
		finish();
		getApplication().onTerminate();
	}

	private boolean isNormalStartup() {
		boolean result = true;
		// String sdcard = Environment.getExternalStorageDirectory().getPath();
		// File file = new File(sdcard + File.separator + ".kankan_silent");
		// if (file.exists()) {
		// result = !(file.delete());
		// }

		return result;
	}

	private final AsyncTask<Void, Void, Boolean> mStartupTask = new AsyncTask<Void, Void, Boolean>() {

		@Override
		protected Boolean doInBackground(Void... params) {
			// Util.getPeerid(BootActivity.this);
			// Util.getSelfAppVersion(BootActivity.this);
			// Util.getIMEI(BootActivity.this);
			// DeviceHelper.loadScreenInfo(BootActivity.this);

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			LOG.info("startup task complete. ready={}", result);
		};
	};

	private final AsyncTask<Void, Void, Void> mTimeoutTask = new AsyncTask<Void, Void, Void>() {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(MIN_SHOW_TIME);
				mStartupTask.get(MAX_SHOW_TIME - MIN_SHOW_TIME,
						TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				LOG.warn("interrupted.");
			} catch (ExecutionException e) {
				LOG.warn(e);
			} catch (TimeoutException e) {
				LOG.warn("timeout.");
			} catch (CancellationException e) {
				LOG.warn("AsyncTask has cancled ");
			}
			return null;
		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		protected void onPostExecute(Void result) {
			finish();
			startActivity(new Intent(BootActivity.this, TaskPanelActivity.class));
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		};
	};

}
