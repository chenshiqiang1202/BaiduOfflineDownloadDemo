/**
 * @description: 
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午4:54:38   
 * @version 1.0   
 */
package com.csq.baiduoffline.models;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.csq.baiduoffline.utils.pinyin.PinyinUtil;

public class OfflineMapItem implements Comparable<OfflineMapItem>{

	// ------------------------ Constants ------------------------
	
	// ------------------------- Fields --------------------------
	/**
	 * 下载信息
	 */
	private volatile MKOLUpdateElement mDownInfo;
	/**
	 * 城市基本信息
	 */
	private volatile MKOLSearchRecord mCityInfo;
	
	/**
	 * 中文转拼音，用于排序
	 */
	private String pinyin;
	
	// ----------------------- Constructors ----------------------

	// -------- Methods for/from SuperClass/Interfaces -----------

	// --------------------- Methods public ----------------------

	// --------------------- Methods private ---------------------

	// --------------------- Getter & Setter ---------------------

	public int getProgress() {
		if(mDownInfo != null){
			return mDownInfo.ratio;
		}
		return 0;
	}

	public int getStatus() {
		if(mDownInfo != null){
			return mDownInfo.status;
		}
		return MKOLUpdateElement.UNDEFINED;
	}
	
	public void setStatus(int status) {
		if(mDownInfo != null){
			mDownInfo.status = status;
		}
	}
	
	public boolean isHavaUpdate(){
		if(mDownInfo != null){
			return mDownInfo.update;
		}
		return false;
	}

	public String getCityName() {
		if(mCityInfo != null){
			return mCityInfo.cityName;
		}
		return "";
	}
	
	public String getPinyin() {
		return pinyin;
	}

	public int getCityId() {
		if(mCityInfo != null){
			return mCityInfo.cityID;
		}
		return 0;
	}

	public MKOLSearchRecord getCityInfo() {
		return mCityInfo;
	}

	public void setCityInfo(MKOLSearchRecord mRecord) {
		this.mCityInfo = mRecord;
		pinyin = PinyinUtil.getPinYin(mRecord.cityName);
	}

	public MKOLUpdateElement getDownInfo() {
		return mDownInfo;
	}

	public void setDownInfo(MKOLUpdateElement mElement) {
		this.mDownInfo = mElement;
	}

	public int getSize() {
		if(mCityInfo != null){
			return mCityInfo.size;
		}
		return 0;
	}

	@Override
	public int compareTo(OfflineMapItem o2) {
		// TODO Auto-generated method stub
		int f1 = getStatus() == MKOLUpdateElement.FINISHED ? 1 : 0;
		int f2 = o2.getStatus() == MKOLUpdateElement.FINISHED ? 1 : 0;
		if(f1 != f2){
			return f1 - f2;
		}
		
		String s1 = getPinyin();
        String s2 = o2.getPinyin();
        int len1 = s1.length();
        int len2 = s2.length();
        int n = Math.min(len1, len2);
        char v1[] = s1.toCharArray();
        char v2[] = s2.toCharArray();
        int pos = 0;
        while (n-- != 0) {
            char c1 = v1[pos];
            char c2 = v2[pos];
            if (c1 != c2) { return c1 - c2; }
            pos++;
        }
        return len1 - len2;
	}

	// --------------- Inner and Anonymous Classes ---------------
}
