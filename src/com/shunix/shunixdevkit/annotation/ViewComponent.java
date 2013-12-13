package com.shunix.shunixdevkit.annotation;

import java.lang.annotation.*;

/**
 * 
 * @author Ray WANG <admin@shunix.com>
 * @since Dec 13th
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewComponent {
	/** View's id */
	public int id() default -1;
}
