package com.shunix.shunixdevkit.cache;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The database name will be the package name 
 * @author Ray WANG
 * @since Dec 22nd, 2013
 * @version 1.0.0
 */
public class CacheHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	public CacheHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public CacheHelper(Activity activity) {
		this(activity, activity.getClass().getName(), null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		Log.i(CacheHelper.class.getCanonicalName(), "Database Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i(CacheHelper.class.getCanonicalName(), "Database Updated");
	}

}
