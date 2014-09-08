/**
 * @description: 
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午10:21:38   
 * @version 1.0   
 */
package com.csq.baiduoffline.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class ToastUtil {

	// ------------------------ Constants ------------------------

	// ------------------------- Fields --------------------------
	
	public static Context appContext;
	
	private static Handler handler = new Handler(appContext.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			ToastInfo ti = (ToastInfo) msg.obj;
			if(ti != null){
				if(ti.text != null){
					Toast.makeText(appContext, 
							ti.text, 
							ti.isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(appContext, 
							ti.resId, 
							ti.isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
				}
			}
		};
	};

	// ----------------------- Constructors ----------------------

	// -------- Methods for/from SuperClass/Interfaces -----------

	// --------------------- Methods public ----------------------
	
	public static void showToastInfo(Context context, String text, boolean isLong) 
	{
		appContext = context.getApplicationContext();
		
		ToastInfo ti = new ToastInfo(text, isLong);
		Message msg = new Message();
		msg.obj = ti;
		handler.sendMessage(msg);
	}
	
	public static void showToastInfo(Context context, int resId, boolean isLong) 
	{
		appContext = context.getApplicationContext();
		
		ToastInfo ti = new ToastInfo(resId, isLong);
		Message msg = new Message();
		msg.obj = ti;
		handler.sendMessage(msg);
	}

	// --------------------- Methods private ---------------------

	// --------------------- Getter & Setter ---------------------

	// --------------- Inner and Anonymous Classes ---------------
	
	static class ToastInfo{
		String text = null;
		int resId = 0;
		boolean isLong = false;
		
		public ToastInfo(String text, boolean isLong) {
			super();
			this.text = text;
			this.isLong = isLong;
		}
		
		public ToastInfo(int resId, boolean isLong) {
			super();
			this.resId = resId;
			this.isLong = isLong;
		}
	}
	
}
