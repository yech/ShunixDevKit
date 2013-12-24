package com.shunix.shunixdevkit.cache;

/**
 * All classes to be cached need to implement this interface. The getName()
 * method will return the table name of this table. Usually, returing the class
 * name will be OK.
 * 
 * @author Ray WANG <admin@shunix.com>
 * @since Dec 22nd, 2013
 * @version 1.0.0
 */
public interface Cachable {
	public String getName();
}
