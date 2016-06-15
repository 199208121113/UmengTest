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
import umeng.x.con.model.ChoiceImgInfo;
import umeng.x.con.umengtest.R;

/**
 * Created by root on 16-6-13.
 */
public class ChoiceImgAdapter extends BaseAdapter{
    private Context context;
    private List<ChoiceImgInfo> dataList;
    private ImageLoader mImageLoader;

    public ChoiceImgAdapter(Context context, List<ChoiceImgInfo> dataList,ImageLoader mImageLoader) {
        this.context = context;
        this.dataList = dataList;
        this.mImageLoader = mImageLoader;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_fragment_list_imgs, parent, false);
        }
        ImageView imageview = (ImageView) convertView
                .findViewById(R.id.id_img);
        ImageView checkImg = (ImageView)convertView.findViewById(R.id.id_check);
        TextView tvMask = (TextView)convertView.findViewById(R.id.id_mask);
        ChoiceImgInfo info = dataList.get(position);

        //tvMask
        if(info.isCheck()){
            checkImg.setImageResource(android.R.drawable.checkbox_on_background);
            tvMask.setVisibility(View.VISIBLE);
        }else{
            checkImg.setImageResource(android.R.drawable.checkbox_off_background);
            tvMask.setVisibility(View.GONE);
        }

        //img
//        imageview.setImageResource(R.drawable.ic_launcher);
        mImageLoader.loadImage(info.getImgUrl(), imageview, false);
        return convertView;
    }
}
