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
import java.util.List;

import umeng.x.con.adapter.ChoiceAudioAdapter;
import umeng.x.con.model.ChoiceAudioInfo;


public class ChoiceAudioActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    public static Intent createIntent(Context context){
        return new Intent(context,ChoiceAudioActivity.class);
    }

    private TextView tvCancel;
    private ListView listView;
    private ContentResolver resolver;
    private ChoiceAudioAdapter adapter;
    List<ChoiceAudioInfo> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choice_audio);
        initView();
        resolver = getContentResolver();
        dataList = getLocalAudio();
        adapter = new ChoiceAudioAdapter(this,dataList);
        listView.setAdapter(adapter);
    }

    void initView(){
        listView = (ListView) findViewById(R.id.act_audio_list);
        listView.setOnItemClickListener(this);
        tvCancel = (TextView)findViewById(R.id.tv_audio_list_cancel);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(dataList == null || dataList.size() == 0){
            return;
        }
        ChoiceAudioInfo info = dataList.get(position);
        info.setCheck(!info.isCheck());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v == tvCancel){
            finish();
        }
    }

    //获取音频策略
    private List<ChoiceAudioInfo> getLocalAudio() {
        String[] projection = new String[]{MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns.SIZE,MediaStore.Audio.AudioColumns.DURATION,MediaStore.Audio.AudioColumns.TITLE};

        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection , null, null, MediaStore.Audio.AudioColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext())
            return null;
        List<ChoiceAudioInfo> audioList = new ArrayList<>();
        cursor.moveToLast();
        do {
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
            if(duration < 1000){
                continue;
            }
            try {
                String audioName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
                String audioUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                ChoiceAudioInfo info = new ChoiceAudioInfo(audioName,audioUrl,duration,false);
                audioList.add(info);
            } catch (Exception e) {
                //忽略异常
                e.printStackTrace();
            }
        } while (cursor.moveToPrevious());
        return audioList;
    }


}
