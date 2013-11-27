package com.shunix.shunixdevkit.utils;

import java.io.File;
import java.io.PrintStream;

import android.content.Context;

/**
 * 
 * @author Ray WANG
 * @since Nov 27th, 2013
 * @version 1.0.0
 */
public class LogUtils {
	private File logFileDir;
	private File logFile;
	private PrintStream printStream;
	private static final String ERROR_TAG = "error_log.log";
	private static final String DEBUG_TAG = "debug_log.log";
	private static final String INFO_TAG = "debug_log.log";
	private static final String WARN_TAG = "warn_log.log";

	public LogUtils(Context context) {
		if (FileUtils.isExternalStorageAvailable()) {
			logFileDir = context.getExternalFilesDir(null);
		} else {
			logFileDir = context.getFilesDir();
		}
		logFile = null;
		printStream = null;
	}

	public boolean logError(Class<?> className, String message) {
		try {
			final String TAG = className.getName();
			logFile = new File(logFileDir, ERROR_TAG);
			printStream = new PrintStream(logFile);
			printStream.println(TAG + " " + message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean logInfo(Class<?> className, String message) {
		try {
			final String TAG = className.getName();
			logFile = new File(logFileDir, INFO_TAG);
			printStream = new PrintStream(logFile);
			printStream.println(TAG + " " + message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean logDebug(Class<?> className, String message) {
		try {
			final String TAG = className.getName();
			logFile = new File(logFileDir, DEBUG_TAG);
			printStream = new PrintStream(logFile);
			printStream.println(TAG + " " + message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean logWarn(Class<?> className, String message) {
		try {
			final String TAG = className.getName();
			logFile = new File(logFileDir, WARN_TAG);
			printStream = new PrintStream(logFile);
			printStream.println(TAG + " " + message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
