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
import umeng.x.con.model.ChoiceAudioInfo;
import umeng.x.con.umengtest.R;

/**
 * Created by root on 16-6-14.
 */
public class ChoiceAudioAdapter extends BaseAdapter {
    private Context ctx;
    private List<ChoiceAudioInfo> dataList;

    public ChoiceAudioAdapter(Context ctx, List<ChoiceAudioInfo> dataList) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_audio_list,null);
            holder.ivImg = (ImageView)convertView.findViewById(R.id.item_audio_list_iv);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.item_audio_list_title);
            holder.ivCheck = (ImageView)convertView.findViewById(R.id.item_audio_list_check);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        ChoiceAudioInfo info = dataList.get(position);
        long duration = info.getDuration() / 1000;
        String title = info.getTitle()+".mp3("+duration+"秒)";
        holder.tvTitle.setText(title);

        boolean isCheck = info.isCheck();
        if(isCheck){
            holder.ivCheck.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            holder.ivCheck.setImageResource(android.R.drawable.checkbox_off_background);
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView ivImg;
        TextView tvTitle;
        ImageView ivCheck;
    }


}
