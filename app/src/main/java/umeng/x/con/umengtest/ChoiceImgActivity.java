package umeng.x.con.umengtest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import umeng.x.con.adapter.ChoiceImgAdapter;
import umeng.x.con.loader.ImageLoader;
import umeng.x.con.model.ChoiceImgInfo;

public class ChoiceImgActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    public static Intent createIntent(Context context,String name){
        Intent intent = new Intent(context,ChoiceImgActivity.class);
        intent.putExtra("title",name);
        return intent;
    }

    private GridView gridView;
    private ImageLoader mImageLoader;
    private ChoiceImgAdapter adapter;
    private ContentResolver resolver;
    private TextView tvBack,tvTitle,tvCancel;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choice_img);
        name = getIntent().getStringExtra("title");
        initView();
        resolver = getContentResolver();
        mImageLoader = ImageLoader.getInstance();
        setFirstAdapter();
    }

    void initView(){
        gridView = (GridView) findViewById(R.id.act_choice_img_grid);
        gridView.setOnItemClickListener(this);
        tvBack = (TextView)findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText(name);
        tvCancel = (TextView)findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
    }

    List<ChoiceImgInfo> dataList;

    private void setFirstAdapter() {
        if(name.equals("最近照片")){
            dataList = getLocalImg();
        }else{
            dataList = getAlbum(name);
        }
        adapter = new ChoiceImgAdapter(this, dataList, mImageLoader);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (dataList == null || dataList.size() < 0) {
            return;
        }
        ChoiceImgInfo info = (ChoiceImgInfo) adapter.getItem(position);
        info.setCheck(!info.isCheck());
        adapter.notifyDataSetInvalidated();
    }

    @Override
    public void onClick(View v) {
        if(v == tvBack){
            startActivity(PhotoListActivity.createIntent(this));
            finish();
        }else if(v == tvCancel){
            finish();
        }
    }

    public List<ChoiceImgInfo> getAlbum(String name) {
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE}, "bucket_display_name = ?",
                new String[]{name}, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext())
            return new ArrayList<>();
        List<ChoiceImgInfo> photos = new ArrayList<>();
        cursor.moveToLast();
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                ChoiceImgInfo photoModel = new ChoiceImgInfo();
                photoModel.setImgUrl(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
                photoModel.setCheck(false);
                photos.add(photoModel);
            }
        } while (cursor.moveToPrevious());
        return photos;
    }

    private List<ChoiceImgInfo> getLocalImg() {
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE}, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext())
            return null;
        List<ChoiceImgInfo> localImgUrls = new ArrayList<>();
        cursor.moveToLast();
        do {
            //过滤掉< 10kb的图片
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024 * 10) {
                String url = "";
                try {
                    url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                } catch (Exception e) {
                    //忽略异常
                }
                if (!TextUtils.isEmpty(url)) {
                    ChoiceImgInfo info = new ChoiceImgInfo(url,false);
                    localImgUrls.add(info);
                }
            }
        } while (cursor.moveToPrevious());
        return localImgUrls;
    }

}
