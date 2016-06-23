package com.x.oss.upload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.aliyun.oss.ossdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class UploadListActivity extends Activity implements View.OnClickListener{
    private static final String TAG = UploadListActivity.class.getSimpleName();

    private Button btnUpload;
    private ListView mListView;
    private UploadListAdapter adapter;
    String[] imgUrls = new String[]{"/storage/sdcard0/tencent/MicroMsg/1466314666001_42c66968.jpg",
    "/storage/sdcard0/tencent/MicroMsg/1464705612306_42d39ee8.jpg","/storage/sdcard0/tencent/MicroMsg/1459397573781_42a32ee0.jpg",
    "/storage/sdcard0/tencent/MicroMsg/1459397570779_42a60d70.jpg","/storage/sdcard0/tencent/MicroMsg/1459397565312_42abf500.jpg",
    "/storage/sdcard0/tencent/MicroMsg/1459397561490_42ac2370.jpg","/storage/sdcard0/tencent/MicroMsg/1459397558281_42a5ea80.jpg"};

    List<UploadInfo> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_list);
        EventBus.getDefault().register(this);
        btnUpload = (Button)findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.upload_list);
        dataList = new ArrayList<>();
        int i = 0;
        for (String url : imgUrls){
            i++;
            UploadInfo info = new UploadInfo("1466314666001_42c66968"+i,url,0);
            dataList.add(info);
        }
        adapter = new UploadListAdapter(this,dataList);
        mListView.setAdapter(adapter);

        startService(new Intent(this,FileUploadService.class));
    }

    @Override
    public void onClick(View v) {
        if(v == btnUpload){
            List<String> filePath = new ArrayList<>();
            for (UploadInfo info : dataList){
                filePath.add(info.getFilePath());
            }
            EventBus.getDefault().post(new UploadEventType(filePath));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressChangeEventBus(ProgressEventType evt){
        Log.e(TAG,"onProgressChange>>>>>>");
        String filePath = evt.getFilePath();
        for (UploadInfo info : dataList){
            if(info.getFilePath().equals(filePath)){
                info.setProgress(evt.getProgress());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        stopService(new Intent(this,FileUploadService.class));
        super.onDestroy();
    }
}
