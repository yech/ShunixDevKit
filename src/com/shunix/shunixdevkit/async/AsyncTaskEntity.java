package com.shunix.shunixdevkit.async;

/**
 * 
 * @author Ray WANG
 * @since Nov 22nd, 2013
 * @version 1.0.0
 */
public abstract class AsyncTaskEntity {
	public abstract void OnPreExecute();
	public abstract void OnExecute();
	public abstract void OnPostExecute();
}
