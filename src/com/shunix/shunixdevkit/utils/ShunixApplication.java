package com.shunix.shunixdevkit.utils;

import android.app.Application;

/**
 * use this class to replace the default application.
 * @author Ray WANG
 * @since Dec 4th, 2013
 * @version 1.0.0
 * 
 */
public class ShunixApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		@SuppressWarnings("unused")
		ShunixUncaughtExceptionHandler shunixUncaughtExceptionHandler = ShunixUncaughtExceptionHandler
				.getInstance(getApplicationContext());
	}

}
