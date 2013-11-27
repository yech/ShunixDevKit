package com.shunix.shunixdevkit.utils;

import java.io.DataOutputStream;

import android.util.Log;

public class ShellHelper {
	public static boolean RootCommand(String command) {
		Process process = null;
		DataOutputStream os = null;
		final String TAG = "RootCommand";
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
}
