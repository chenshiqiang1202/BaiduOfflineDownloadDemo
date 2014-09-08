/**
 * @description: 普通离线城市列表适配器
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午5:46:14   
 * @version 1.0   
 */
package com.csq.baiduoffline.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.csq.baiduoffline.BaiduOfflineMapActivity;
import com.csq.baiduoffline.R;
import com.csq.baiduoffline.interfaces.OnOfflineItemStatusChangeListener;
import com.csq.baiduoffline.models.OfflineMapItem;
import com.csq.baiduoffline.utils.FileUtil;

public class OfflineMapAdapter extends ArrayListAdapter<OfflineMapItem> {

	// ------------------------ Constants ------------------------

	// ------------------------- Fields --------------------------
	private Context context;
	private MKOfflineMap mOffline;
	private OnOfflineItemStatusChangeListener listener;

	// ----------------------- Constructors ----------------------
	
	public OfflineMapAdapter(Context context, MKOfflineMap mOffline, OnOfflineItemStatusChangeListener listener) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mOffline = mOffline;
		this.listener = listener;
	}

	// -------- Methods for/from SuperClass/Interfaces -----------
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem_offline_province_child, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		OfflineMapItem data = (OfflineMapItem) getItem(position);
		holder.setData(data);
		
		return convertView;
	}

	// --------------------- Methods public ----------------------

	// --------------------- Methods private ---------------------

	// --------------------- Getter & Setter ---------------------

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
				//暂停、未错误，都当作暂停，可以继续下载
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
