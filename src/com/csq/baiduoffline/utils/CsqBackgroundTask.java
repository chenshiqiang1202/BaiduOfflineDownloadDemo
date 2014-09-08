/**
 * @description: 
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午10:04:38   
 * @version 1.0   
 */
package com.csq.baiduoffline.utils;

import java.lang.ref.WeakReference;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

public abstract class CsqBackgroundTask<T> extends AsyncTask<Void, Integer, T> {

	

	// ------------------------ Constants ------------------------

	// ------------------------- Fields --------------------------
	
	public WeakReference<Object> context;

	// ----------------------- Constructors ----------------------
	
	public CsqBackgroundTask(Context ctx){
		context = new WeakReference<Object>(ctx);
	}

	// -------- Methods for/from SuperClass/Interfaces -----------
	
	@Override
	protected T doInBackground(Void... params) {
		// TODO Auto-generated method stub
		T result = null;
		try {
			result = onRun();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(T result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(isCanContine()){
			onResult(result);
		}
	}

	// --------------------- Methods public ----------------------

	// --------------------- Methods private ---------------------
	
	protected abstract T onRun();
	protected abstract void onResult(T result);
	
	private boolean isCanContine(){
		if(context == null || context.get() == null){
			return false;
		}
		
		Object h = context.get();
		
		if(h instanceof Activity){
			return h != null && ((Activity)h).isFinishing() == false;
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(isInstanceofOsFragment(h)){
				return isFragmentRemoving((Fragment)h);
			}
		}
		
		if(h instanceof android.support.v4.app.Fragment){
			return h != null && ((android.support.v4.app.Fragment)h).isRemoving() == false;
		}
		
		return true;
	}
	
	@SuppressLint("NewApi")
	private boolean isInstanceofOsFragment(Object h){
		if(h instanceof Fragment){
			return true;
		}
		return false;
	}
	@SuppressLint("NewApi")
	private boolean isFragmentRemoving(Object h){
		return h != null && ((Fragment)h).isRemoving() == false;
	}

	// --------------------- Getter & Setter ---------------------

	// --------------- Inner and Anonymous Classes ---------------
}
