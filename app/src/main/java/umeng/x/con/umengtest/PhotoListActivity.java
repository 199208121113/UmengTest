package umeng.x.con.umengtest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import umeng.x.con.adapter.PhotoListAdapter;
import umeng.x.con.model.AlbumModel;

/**
 * 选择相册
 * Created by root on 16-6-14.
 */
public class PhotoListActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    public static Intent createIntent(Context ctx){
        return new Intent(ctx,PhotoListActivity.class);
    }

    private ContentResolver resolver;
    private TextView tvCancel;
    private ListView mListView;
    private PhotoListAdapter adapter;
    private List<AlbumModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_list);
        initView();
        resolver = getContentResolver();
        dataList = getAlbums();
        adapter = new PhotoListAdapter(this,dataList);
        mListView.setAdapter(adapter);
    }

    void initView(){
        mListView = (ListView)findViewById(R.id.act_photo_list);
        mListView.setOnItemClickListener(this);
        tvCancel = (TextView)findViewById(R.id.tv_photo_list_cancel);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = dataList.get(position).getName();
        startActivity(ChoiceImgActivity.createIntent(this,name));
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == tvCancel){
            finish();
        }
    }

    /**
     * 获取所有相册列表
     */
    public List<AlbumModel> getAlbums() {
        List<AlbumModel> albums = new ArrayList<>();
        Map<String, AlbumModel> map = new HashMap<>();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.SIZE}, null, null, null);
        if (cursor == null || !cursor.moveToNext())
            return new ArrayList<>();
        cursor.moveToLast();
        AlbumModel current = new AlbumModel("最近照片", 0, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)), true); // "最近照片"相册
        albums.add(current);
        do {
            if (cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) < 1024 * 10)
                continue;

            current.increaseCount();
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            if (map.keySet().contains(name))
                map.get(name).increaseCount();
            else {
                AlbumModel album = new AlbumModel(name, 1, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
                map.put(name, album);
                albums.add(album);
            }
        } while (cursor.moveToPrevious());
        return albums;
    }
}
