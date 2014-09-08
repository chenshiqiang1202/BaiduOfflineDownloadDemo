/**
 * @description: 
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午6:36:03   
 * @version 1.0   
 */
package com.csq.baiduoffline.utils;
public class FileUtil {

	public static String getSizeStr(long fileLength) {
    	String strSize = "";
		try {
			if(fileLength >= 1024*1024*1024){
				strSize = (float)Math.round(10*fileLength/(1024*1024*1024))/10 + " GB";
			}else if(fileLength >= 1024*1024){
				strSize = (float)Math.round(10*fileLength/(1024*1024*1.0))/10 + " MB";
			}else if(fileLength >= 1024){
				strSize = (float)Math.round(10*fileLength/(1024))/10 + " KB";
			}else if(fileLength >= 0){
				strSize = fileLength + " B";
			}else {
				strSize = "0 B";
			}
		} catch (Exception e) {
			e.printStackTrace();
			strSize = "0 B";
		}
		return strSize;
	}
	
}
