package com.x.oss.upload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aliyun.oss.ossdemo.R;

import java.util.List;

/**
 * Created by root on 16-6-23.
 */
public class UploadListAdapter extends BaseAdapter {
    private Context ctx;
    private List<UploadInfo> dataList;

    public UploadListAdapter(Context ctx, List<UploadInfo> dataList) {
        this.ctx = ctx;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holer;
        if(convertView == null){
            holer = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_upload_list,null);
            holer.tvFileName = (TextView) convertView.findViewById(R.id.item_upload_fileName);
            holer.uploadPro = (ProgressBar)convertView.findViewById(R.id.item_upload_pro);
            convertView.setTag(holer);
        }else{
            holer = (ViewHolder) convertView.getTag();
        }
        UploadInfo info = dataList.get(position);

        holer.tvFileName.setText(info.getFileName());
        holer.uploadPro.setProgress(info.getProgress());
        return convertView;
    }

    class ViewHolder{
        TextView tvFileName;
        ProgressBar uploadPro;
    }
}
