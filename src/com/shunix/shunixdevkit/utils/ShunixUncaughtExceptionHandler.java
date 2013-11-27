package com.shunix.shunixdevkit.utils;

import java.io.File;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 
 * @author Ray WANG
 * @since Nov 27th, 2013
 * @version 1.0.0
 */
public class ShunixUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = ShunixUncaughtExceptionHandler.class
			.getName();
	private Thread.UncaughtExceptionHandler defaultExceptionHandler;
	private Context context;
	private static ShunixUncaughtExceptionHandler singleInstance = null;
	private Map<String, String> exceptionInfo = new HashMap<String, String>();
	private File logFileDir;
	private PrintStream printStream;

	public static ShunixUncaughtExceptionHandler getInstance(Context context) {
		if (singleInstance == null) {
			singleInstance = new ShunixUncaughtExceptionHandler(context);
		}
		return singleInstance;
	}

	public ShunixUncaughtExceptionHandler(Context context) {
		this.context = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {

	}

	private void storeException() {

	}

	private void getExceptionInfo() {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (packageInfo != null) {
				String versionName = packageInfo.versionName == null ? "null"
						: packageInfo.versionName;
				String versionCode = String.valueOf(packageInfo.versionCode);
				exceptionInfo.put("versionName", versionName);
				exceptionInfo.put("versionCode", versionCode);
			}
			// Reflection to get some private information
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				exceptionInfo.put(field.getName(), field.get(null).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
