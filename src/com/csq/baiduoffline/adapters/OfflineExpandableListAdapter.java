/**
 * @description: 
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午9:02:54   
 * @version 1.0   
 */
package com.csq.baiduoffline.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.csq.baiduoffline.BaiduOfflineMapActivity;
import com.csq.baiduoffline.R;
import com.csq.baiduoffline.interfaces.OnOfflineItemStatusChangeListener;
import com.csq.baiduoffline.models.OfflineMapItem;
import com.csq.baiduoffline.utils.FileUtil;

public class OfflineExpandableListAdapter extends BaseExpandableListAdapter{

	// ------------------------ Constants ------------------------

	// ------------------------- Fields --------------------------
	
	private Context context;
	private MKOfflineMap mOffline;
	protected LayoutInflater inflater;
	private OnOfflineItemStatusChangeListener listener;
	
	private List<OfflineMapItem> itemsProvince;
    private List<List<OfflineMapItem>> itemsProvinceCity;

	// ----------------------- Constructors ----------------------
    
    public OfflineExpandableListAdapter(Context context, MKOfflineMap mOffline, OnOfflineItemStatusChangeListener listener){
    	this.context = context;
    	this.mOffline = mOffline;
    	this.listener = listener;
    	inflater = LayoutInflater.from(context);
    }

	// -------- Methods for/from SuperClass/Interfaces -----------
    @Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
    	final OfflineMapItem item = (OfflineMapItem) getGroup(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem_offline_province, null);
		}
		
		TextView provinceText = (TextView) convertView.findViewById(R.id.tvProvince);
		provinceText.setText(item.getCityName().toString() 
				+ "(" + FileUtil.getSizeStr(item.getSize()) + ")");
		
		if (isExpanded) {
			provinceText.setCompoundDrawablesWithIntrinsicBounds(null, 
					null,
					context.getResources().getDrawable(R.drawable.ic_offline_u),
					null);
		} else {
			provinceText.setCompoundDrawablesWithIntrinsicBounds(null, 
					null,
					context.getResources().getDrawable(R.drawable.ic_offline_d),
					null);
		}
		return convertView;
	}
    
    @Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
    	ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem_offline_province_child, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		OfflineMapItem data = (OfflineMapItem) getChild(groupPosition, childPosition);
		holder.setData(data);
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		if(itemsProvince != null){
			return itemsProvince.size();
		}
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if(itemsProvinceCity != null && groupPosition >= 0 && groupPosition < itemsProvinceCity.size()){
			List<OfflineMapItem> c = itemsProvinceCity.get(groupPosition);
			if(c != null){
				return c.size();
			}
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if(itemsProvince != null && groupPosition >= 0 && groupPosition < itemsProvince.size()){
			return itemsProvince.get(groupPosition);
		}
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		List<OfflineMapItem> pList = itemsProvinceCity.get(groupPosition);
		if(pList != null && childPosition >= 0 && childPosition < pList.size()){
			return pList.get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	// --------------------- Methods public ----------------------
	
	public List<OfflineMapItem> search(String key) {
		List<OfflineMapItem> list = new ArrayList<OfflineMapItem>();
		for (int i = 0; i < itemsProvinceCity.size(); i++) {
			for (OfflineMapItem item : itemsProvinceCity.get(i)) {
				if (item.getCityName().indexOf(key) >= 0) {
					list.add(item);
				}
			}
		}
		return list;
	}

	// --------------------- Methods private ---------------------

	// --------------------- Getter & Setter ---------------------
	
	public void setDatas(List<OfflineMapItem> itemsProvince, List<List<OfflineMapItem>> itemsProvinceCity) {
		this.itemsProvince = itemsProvince;
		this.itemsProvinceCity = itemsProvinceCity;
		notifyDataSetChanged();
	}

	// --------------- Inner and Anonymous Classes ---------------
	
	class ViewHolder implements OnClickListener{
		View lyRoot;
        TextView tvCityname;
        TextView tvSize;
        TextView tvStatus;
        ImageView ivDownload;
        
        private OfflineMapItem data;

        public ViewHolder(View convertView){
        	lyRoot = convertView.findViewById(R.id.lyRoot);
        	tvCityname = (TextView) convertView.findViewById(R.id.tvCityname);
        	tvSize = (TextView) convertView.findViewById(R.id.tvSize);
        	tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        	ivDownload = (ImageView) convertView.findViewById(R.id.ivDownload);
        	
        	lyRoot.setOnClickListener(this);
        }
        
        public void setData(OfflineMapItem data) {
			this.data = data;
			
			tvCityname.setText(data.getCityName());
			tvSize.setText(FileUtil.getSizeStr(data.getSize()));
			
			if(data.getStatus() == MKOLUpdateElement.UNDEFINED){
				ivDownload.setVisibility(View.VISIBLE);
				
			}else{
				ivDownload.setVisibility(View.INVISIBLE);
			}
			
			if(data.getStatus() == MKOLUpdateElement.UNDEFINED){
				tvStatus.setText("");
				
			}else if(data.getStatus() == MKOLUpdateElement.DOWNLOADING){
				tvStatus.setText("(" + data.getProgress() + "%)");
				
			}else if(data.getStatus() == MKOLUpdateElement.FINISHED){
				tvStatus.setText("(已下载)");
				
			}else if(data.getStatus() == MKOLUpdateElement.SUSPENDED
					|| data.getStatus() >= MKOLUpdateElement.eOLDSMd5Error){
				//暂停、错误，都当作暂停，都是可以继续下载
				tvStatus.setText("(暂停)");				
				
			}else if(data.getStatus() == MKOLUpdateElement.WAITING){
				tvStatus.setText("(等待)");				
				
			}else{
				tvStatus.setText("");
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.lyRoot:
				if(data.getStatus() == MKOLUpdateElement.UNDEFINED){
					int id = data.getCityId();
					if(id > 0){
						mOffline.start(id);
						data.setStatus(MKOLUpdateElement.WAITING);
						if(listener != null){
							listener.statusChanged(data, false);
						}
					}
					
				}else{
					//跳转下载界面
					if(context instanceof BaiduOfflineMapActivity){
						((BaiduOfflineMapActivity)context).toDownloadPage();
					}
				}
				break;
			default:
				break;
			}
		}
    }
}
