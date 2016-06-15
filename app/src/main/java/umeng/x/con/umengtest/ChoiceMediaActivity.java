package umeng.x.con.umengtest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import umeng.x.con.adapter.ChoiceMediaAdapter;
import umeng.x.con.loader.ImageLoader;
import umeng.x.con.model.ChoiceMediaInfo;
import umeng.x.con.util.IOUtil;
import umeng.x.con.util.ScreenUtil;


public class ChoiceMediaActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    public static Intent createIntent(Context context){
        return new Intent(context,ChoiceMediaActivity.class);
    }

    private GridView gridView;
    private TextView tvCancel;
    private ContentResolver resolver;
    private ChoiceMediaAdapter adapter;
    private List<ChoiceMediaInfo> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choice_media);
        initView();
        resolver = getContentResolver();
        dataList = getLocalMedia();
        adapter = new ChoiceMediaAdapter(this,dataList, ImageLoader.getInstance());
        gridView.setAdapter(adapter);
    }

    void initView(){
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        gridView = (GridView)findViewById(R.id.act_choice_media_grid);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == tvCancel){
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(dataList == null || dataList.size() == 0){
            return;
        }
        ChoiceMediaInfo info = dataList.get(position);
        info.setCheck(!info.isCheck());
        adapter.notifyDataSetChanged();
    }


    public static String MEDIA_THUMB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/x_media/";
    //获取视频策略 （获取视频路径，获取对应路径的bitmap到本地文件夹）
    private List<ChoiceMediaInfo> getLocalMedia() {
        Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.DATE_ADDED, MediaStore.Video.VideoColumns.SIZE,MediaStore.Video.VideoColumns.TITLE}, null, null, MediaStore.Video.VideoColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext())
            return null;
        List<ChoiceMediaInfo> localImgUrls = new ArrayList<>();
        cursor.moveToLast();
        do {
            try {
                String videoUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE));
                String tempPath = MEDIA_THUMB_PATH + title +".jpg";
                if(IOUtil.fileExist(tempPath)){
                    ChoiceMediaInfo info = new ChoiceMediaInfo(videoUrl,tempPath,title,false);
                    localImgUrls.add(info);
                    continue;
                }
                if(!IOUtil.fileExist(MEDIA_THUMB_PATH)){
                    new File(MEDIA_THUMB_PATH).mkdirs();
                }
                Bitmap tempBitmap = getVideoThumbnail(videoUrl,ScreenUtil.dip2px(this,60),ScreenUtil.dip2px(this,60), MediaStore.Images.Thumbnails.MICRO_KIND);
                boolean saveStatus = IOUtil.saveBitmap(tempPath,tempBitmap,90);
                if(saveStatus){
                    ChoiceMediaInfo info = new ChoiceMediaInfo(videoUrl,tempPath,title,false);
                    localImgUrls.add(info);
                }else{
                    Toast.makeText(this,"文件缩略图保存失败,请检查内存!",Toast.LENGTH_SHORT).show();
                }
                if(tempBitmap != null && !tempBitmap.isRecycled()){
                    tempBitmap.recycle();
                }
            } catch (Exception e) {
                //忽略异常
                e.printStackTrace();
            }
        } while (cursor.moveToPrevious());
        return localImgUrls;
    }

    public static Bitmap getVideoThumbnail(String videoPath,int width,int height,int kind) {
        Bitmap bitmap =null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    private void playVideo(String videoPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String strend="";
        if(videoPath.toLowerCase().endsWith(".mp4")){
            strend="mp4";
        }
        else if(videoPath.toLowerCase().endsWith(".3gp")){
            strend="3gp";
        }
        else if(videoPath.toLowerCase().endsWith(".mov")){
            strend="mov";
        }
        else if(videoPath.toLowerCase().endsWith(".wmv")){
            strend="wmv";
        }
        intent.setDataAndType(Uri.parse(videoPath), "video/"+strend);
        startActivity(intent);
    }
}
