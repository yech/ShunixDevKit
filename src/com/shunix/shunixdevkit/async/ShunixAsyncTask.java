package com.shunix.shunixdevkit.async;

import com.shunix.shunixdevkit.utils.DevKitException;

import android.os.AsyncTask;

/**
 * 
 * @author Ray WANG
 * @since Nov 22nd, 2013
 * @version 1.0.0
 */
public class ShunixAsyncTask extends AsyncTask<Void, Integer, Void> {

	private AsyncTaskEntity asyncTaskEntity = null;
	//private static final String TAG = "ShunixAsyncTask"; 
	
	public ShunixAsyncTask(AsyncTaskEntity asyncTaskEntity) throws DevKitException {
		this.asyncTaskEntity = asyncTaskEntity;
		if(asyncTaskEntity == null) {
			throw new DevKitException("Async Task Entity cannot be empty");
		}
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.asyncTaskEntity.OnPostExecute();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.asyncTaskEntity.OnPreExecute();
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		this.asyncTaskEntity.OnExecute();
		return null;
	}

}
