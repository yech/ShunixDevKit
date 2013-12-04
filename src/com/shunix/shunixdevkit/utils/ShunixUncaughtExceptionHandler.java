package com.shunix.shunixdevkit.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Ray WANG
 * @since Nov 27th, 2013
 * @version 1.0.0
 */
public class ShunixUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = ShunixUncaughtExceptionHandler.class
			.getName();
	private static final int ERROR_CODE = 1;
	private Thread.UncaughtExceptionHandler defaultExceptionHandler;
	private Context context;
	private static ShunixUncaughtExceptionHandler singleInstance = null;
	private Map<String, String> exceptionInfo = new HashMap<String, String>();
	private File logFileDir;
	private File logFile;

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
		if (!handleException(arg1) && defaultExceptionHandler != null) {
			defaultExceptionHandler.uncaughtException(arg0, arg1);
		} else {
			try {
				Thread.sleep(600);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}
		}
		// Kill the process.
		Process.killProcess(Process.myPid());
		System.exit(ERROR_CODE);
	}

	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "App crashed.", Toast.LENGTH_SHORT)
						.show();
			}
		};
		((Activity) context).runOnUiThread(runnable);
		getExceptionInfo();
		storeException(ex);
		return true;
	}

	private void storeException(Throwable ex) {
		StringBuffer exceptionBuffer = new StringBuffer();
		for (Map.Entry<String, String> entry : exceptionInfo.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			exceptionBuffer.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		exceptionBuffer.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = DateFormat.format("yyyy-MM-dd-HH-mm-ss",
					new Date(timestamp)).toString();
			String fileName = "exception-" + time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				logFile = new File(logFileDir, fileName);
				FileOutputStream fos = new FileOutputStream(logFile, true);
				fos.write(exceptionBuffer.toString().getBytes());
				fos.close();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
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
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}
}
