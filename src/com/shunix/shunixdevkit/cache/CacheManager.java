package com.shunix.shunixdevkit.cache;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Currently only support classes with all String fields.
 * 
 * @author Ray WANG
 * @since Dec 22nd, 2013
 * @version 1.0.1
 */
public class CacheManager {
	private static CacheManager cacheManager = null;
	private CacheHelper cacheHelper = null;
	private SQLiteDatabase database = null;

	public static CacheManager getInstance(Activity activity) {
		if (cacheManager == null) {
			cacheManager = new CacheManager(activity);
		}
		return cacheManager;
	}

	private CacheManager(Activity activity) {
		cacheHelper = new CacheHelper(activity);
	}

	public boolean cacheObject(Cachable cachable) {
		try {
			Class<?> c = cachable.getClass();
			Field[] fields = c.getDeclaredFields();
			String sqlField = "";
			if (!isTableExist(cachable.getName())) {
				for (Field field : fields) {
					boolean originalState = field.isAccessible();
					field.setAccessible(true);
					sqlField += field.getName() + " TEXT,";
					field.setAccessible(originalState);
				}
				sqlField = sqlField.substring(0, sqlField.length() - 1);
				database = cacheHelper.getWritableDatabase();
				String sqlCmd = "CREATE TABLE " + cachable.getName() + " ("
						+ sqlField + ");";
				database.execSQL(sqlCmd);
			}
			for (Field field : fields) {
				boolean originalState = field.isAccessible();
				field.setAccessible(true);
				ContentValues cValues = new ContentValues();
				cValues.put(field.getName(), (String) field.get(cachable));
				database.insert(cachable.getName(), null, cValues);
				field.setAccessible(originalState);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (database.isOpen()) {
				database.close();
			}
			return false;
		}
		if (database.isOpen()) {
			database.close();
		}
		return true;
	}

	public String get(Cachable cachable, String name, String selectionName,
			String selectionValue) {
		database = cacheHelper.getReadableDatabase();
		Cursor cursor = database.query(cachable.getName(),
				new String[] { name }, "?=?", new String[] { selectionName,
						selectionValue }, null, null, null);
		return cursor.getString(0);
	}

	private boolean isTableExist(String name) {
		if (name == null) {
			return false;
		}
		Cursor cursor = null;
		database = cacheHelper.getReadableDatabase();
		try {
			String sqlCmd = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type='table' and name='"
					+ name.trim() + "';";
			cursor = database.rawQuery(sqlCmd, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (database.isOpen()) {
				database.close();
			}
			return false;
		}
		if (database.isOpen()) {
			database.close();
		}
		return false;
	}

}
