package com.shunix.shunixdevkit.utils;

import android.os.Environment;

public class FileUtils {
	public static boolean isExternalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}
