package com.shunix.shunixdevkit.annotation;

import java.lang.reflect.Field;

import android.app.Activity;
import android.view.View;

/**
 * To use annotation to init all view components. You should init a annotation
 * parser first.
 * 
 * @author Ray WANG
 * @since Dec 13th
 * @version 1.1.0
 */
public class AnnotationParser {
	private static AnnotationParser parser;

	private AnnotationParser() {

	}

	public static AnnotationParser getInstance() {
		if (parser == null) {
			parser = new AnnotationParser();
		}

		return parser;
	}

	/** Use Reflection to init all component in an activity */
	public void initAllComponent(Activity activity) {
		Field[] fields = activity.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ViewComponent.class)) {
				initComponent(activity, field);
			}
		}
	}

	private void initComponent(Activity activity, Field field) {
		ViewComponent viewComponent = field.getAnnotation(ViewComponent.class);
		int viewId = viewComponent.id();
		try {
			field.setAccessible(true);
			field.set(activity, activity.findViewById(viewId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Use Reflection to init all component in an activity */
	public void initAllComponent(android.support.v4.app.Fragment fragment, View view) {
		Field[] fields = fragment.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ViewComponent.class)) {
				initComponent(fragment, view, field);
			}
		}
	}

	private void initComponent(android.support.v4.app.Fragment fragment, View view, Field field) {
		ViewComponent viewComponent = field.getAnnotation(ViewComponent.class);
		int viewId = viewComponent.id();
		try {
			field.setAccessible(true);
			field.set(fragment, view.findViewById(viewId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
