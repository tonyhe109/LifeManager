package com.lifemanager.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

public class Logger {

	public static final boolean ENABLED = true;
	public static final String GLOBAL_LOGGER_NAME = "global";
	private static final String TAG = "LOGGING";
	private static final String CONFIG_TAGS = "TAGS";
	private static final String CONFIG_ROOT = "ROOT";
	private static final int VERBOSE = 2;
	private static final int DEBUG = 3;
	private static final int INFO = 4;
	private static final int WARN = 5;
	private static final int ERROR = 6;
	private static final int ASSERT = 7;
	private static final int UNKNOWN = 0;
	private static final int ALL = 1;
	private static final int OFF = 8;
	private static final HashMap LOGGERS = new HashMap();
	private static final String LEVEL_STRINGS[] = { "ALL", "VERBOSE", "DEBUG",
			"INFO", "WARN", "ERROR", "ASSERT", "OFF" };
	private static final int LEVEL_INTS[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
	private final String mName;
	private final int mLevel;
	private static String mConfigPath;

	public static Logger getLogger(Class cl) {
		return getLogger(cl.getName());
	}

	public static synchronized Logger getLogger(String name) {
		if (mConfigPath == null) {
			mConfigPath = (new StringBuilder(String.valueOf(Environment
					.getExternalStorageDirectory().getAbsolutePath())))
					.append(File.separator).append("logger.json").toString();
			init(mConfigPath);
		}
		Logger logger = (Logger) LOGGERS.get(name);
		if (logger == null) {
			int level = 8;
			level = getLoggerLevel(name);
			logger = new Logger(name, level);
			LOGGERS.put(name, logger);
		}
		return logger;
	}

	public static void init(String configPath) {
		mConfigPath = configPath;
		if (configPath != null) {
			File configFile = new File(configPath);
			if (configFile.exists())
				try {
					String json = (new Scanner(configFile)).useDelimiter("\\A")
							.next();
					JSONObject config = new JSONObject(json);
					if (config.has("ROOT"))
						setLoggerRootLevel(parseLevel(config.getString("ROOT")));
					if (config.has("TAGS")) {
						JSONObject tags = config.getJSONObject("TAGS");
						String name;
						for (Iterator keys = tags.keys(); keys.hasNext(); setLoggerLevel(
								name, parseLevel(tags.getString(name))))
							name = (String) keys.next();

					}
				} catch (FileNotFoundException e) {
					Log.w("LOGGING", e.getLocalizedMessage());
				} catch (JSONException e) {
					Log.w("LOGGING", e.getLocalizedMessage());
				}
		}
	}

	private Logger(String name, int level) {
		mName = name;
		mLevel = level;
	}

	public boolean isVerboseEnabled() {
		return mLevel <= 2;
	}

	public boolean isDebugEnabled() {
		return mLevel <= 3;
	}

	public boolean isInfoEnabled() {
		return mLevel <= 4;
	}

	public boolean isWarnEnabled() {
		return mLevel <= 5;
	}

	public boolean isErrorEnabled() {
		return mLevel <= 6;
	}

	public void verbose(String msg) {
		log(2, msg);
	}

	public void verbose(Throwable tr) {
		log(2, tr);
	}

	public void verbose(String msg, Throwable tr) {
		log(2, msg, tr);
	}

	public void verbose(String format, Object arg) {
		log(2, format, arg);
	}

	public void verbose(String format, Object arg1, Object arg2) {
		log(2, format, arg1, arg2);
	}

	public void verbose(String format, Object arguments[]) {
		log(2, format, arguments);
	}

	public void debug(String msg) {
		log(3, msg);
	}

	public void debug(Throwable tr) {
		log(3, tr);
	}

	public void debug(String msg, Throwable tr) {
		log(3, msg, tr);
	}

	public void debug(String format, Object arg) {
		log(3, format, arg);
	}

	public void debug(String format, Object arg1, Object arg2) {
		log(3, format, arg1, arg2);
	}

	public void debug(String format, Object arguments[]) {
		log(3, format, arguments);
	}

	public void info(String msg) {
		log(4, msg);
	}

	public void info(Throwable tr) {
		log(4, tr);
	}

	public void info(String msg, Throwable tr) {
		log(4, msg, tr);
	}

	public void info(String format, Object arg) {
		log(4, format, arg);
	}

	public void info(String format, Object arg1, Object arg2) {
		log(4, format, arg1, arg2);
	}

	public void info(String format, Object arguments[]) {
		log(4, format, arguments);
	}

	public void warn(String msg) {
		log(5, msg);
	}

	public void warn(Throwable tr) {
		log(5, tr);
	}

	public void warn(String msg, Throwable tr) {
		log(5, msg, tr);
	}

	public void warn(String format, Object arg) {
		log(5, format, arg);
	}

	public void warn(String format, Object arg1, Object arg2) {
		log(5, format, arg1, arg2);
	}

	public void warn(String format, Object arguments[]) {
		log(5, format, arguments);
	}

	public void error(String msg) {
		log(6, msg);
	}

	public void error(Throwable tr) {
		log(6, tr);
	}

	public void error(String msg, Throwable tr) {
		log(6, msg, tr);
	}

	public void error(String format, Object arg) {
		log(6, format, arg);
	}

	public void error(String format, Object arg1, Object arg2) {
		log(6, format, arg1, arg2);
	}

	public void error(String format, Object arguments[]) {
		log(6, format, arguments);
	}

	private void log(int priority, String msg) {
		if (priority >= mLevel)
			Log.println(priority, mName, msg);
	}

	private void log(int priority, Throwable tr) {
		if (priority >= mLevel)
			logThrowable(priority, tr);
	}

	private void log(int priority, String msg, Throwable tr) {
		if (priority >= mLevel) {
			Log.println(priority, mName, msg);
			logThrowable(priority, tr);
		}
	}

	private void log(int priority, String format, Object arg) {
		if (priority >= mLevel)
			Log.println(priority, mName, format(format, arg, null));
	}

	private void log(int priority, String format, Object arg1, Object arg2) {
		if (priority >= mLevel)
			Log.println(priority, mName, format(format, arg1, arg2));
	}

	private void log(int priority, String format, Object arguments[]) {
		if (priority >= mLevel)
			Log.println(priority, mName, format(format, arguments));
	}

	private void logThrowable(int priority, Throwable tr) {
		Log.println(priority, mName, Log.getStackTraceString(tr));
	}

	private String format(String format, Object arg1, Object arg2) {
		return MessageFormatter.format(format, arg1, arg2).getMessage();
	}

	private String format(String format, Object args[]) {
		return MessageFormatter.arrayFormat(format, args).getMessage();
	}

	private static int parseLevel(String str) {
		int level = 0;
		for (int i = 0; i < LEVEL_STRINGS.length; i++) {
			if (!str.equals(LEVEL_STRINGS[i]))
				continue;
			level = LEVEL_INTS[i];
			break;
		}

		return level;
	}

	private static int getLoggerLevel(String s) {
		return ROOT_LEVEL;
	}

	private static int ROOT_LEVEL = 0;

	private static void setLoggerRootLevel(int i) {
		ROOT_LEVEL = i;
	}

	private static void setLoggerLevel(String s, int i) {

	}

	// private static native int getLoggerLevel(String s);
	//
	// private static native void setLoggerRootLevel(int i);
	//
	// private static native void setLoggerLevel(String s, int i);
	//
	// static {
	// try {
	// System.loadLibrary("logging");
	// } catch (Exception e) {
	// Log.e("LOGGING", e.getMessage());
	// }
	// }
}