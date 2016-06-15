package umeng.x.con.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import umeng.x.con.loader.ImageLoader;
import umeng.x.con.model.AlbumModel;
import umeng.x.con.umengtest.R;

/**
 * Created by root on 16-6-14.
 */
public class PhotoListAdapter extends BaseAdapter {
    private Context ctx;
    private List<AlbumModel> dataList;

    public PhotoListAdapter(Context ctx, List<AlbumModel> dataList) {
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
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_photo_list,null);
            holder.ivImg = (ImageView)convertView.findViewById(R.id.item_photo_list_iv);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.item_photo_list_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        AlbumModel info = dataList.get(position);
        String title = info.getName()+"("+info.getCount()+")";
        holder.tvTitle.setText(title);
        ImageLoader.getInstance().loadImage(info.getRecent(),holder.ivImg,false);
        return convertView;
    }

    private class ViewHolder{
        ImageView ivImg;
        TextView tvTitle;
    }


}
