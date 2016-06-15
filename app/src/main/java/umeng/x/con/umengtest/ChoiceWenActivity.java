package umeng.x.con.umengtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umeng.x.con.adapter.ChoiceWenAdapter;
import umeng.x.con.model.FileInfo;
import umeng.x.con.util.IOUtil;


public class ChoiceWenActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener {

    public static Intent createIntent(Context ctx){
        return new Intent(ctx,ChoiceWenActivity.class);
    }

    private ChoiceWenAdapter adapter;
    private ListView mListView;
    private TextView tvCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choice_wen);
        tvCancel = (TextView) findViewById(R.id.tv_wen_list_cancel);
        tvCancel.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.act_wen_list);
        mListView.setOnItemClickListener(this);
        String scanPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        Map<String,String> scanType = new HashMap<>();
        scanType.put(".pdf","");
        scanType.put(".word","");
        scanType.put(".txt","");
        scanType.put(".excel","");
        new LoadLocalFileTask(this,scanType,scanPath){}.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(dataList == null || dataList.size() == 0){
            return;
        }
        FileInfo info = dataList.get(position);
        info.setCheck(!info.isCheck());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if(v == tvCancel){
            finish();
        }
    }

    private List<FileInfo> dataList;
    /**
     * 扫描策略说明 scanPath 一旦指定 则递归查找指定目录所有文件以及文件夹
     * 问题：目录数量  与扫描时间成正比
     * @author xdf
     */
    private class LoadLocalFileTask extends AsyncTask<String,String,List<FileInfo>>{
        Map<String,String> scanType;
        String scanPath;
        Context ctx;

        public LoadLocalFileTask(Context ctx,Map<String, String> scanType, String scanPath) {
            this.ctx = ctx;
            this.scanType = scanType;
            this.scanPath = scanPath;
        }

        @Override
        protected List<FileInfo> doInBackground(String... params) {
            return scanSDCard(new File(scanPath),scanType);
        }

        @Override
        protected void onPostExecute(List<FileInfo> fileInfos) {
            super.onPostExecute(fileInfos);
            if(fileInfos == null || fileInfos.size() == 0){
                return;
            }
            dataList = fileInfos;
            adapter = new ChoiceWenAdapter(ctx,fileInfos);
            mListView.setAdapter(adapter);
        }
    }

    private List<FileInfo> scanSDCard(File file, Map<String,String> scanType) {
        List<FileInfo> mInfo = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File mFile : files){
                if(mFile.isFile()){
                    String tempFileName = mFile.getName();
                    String tempFilePath = mFile.getAbsolutePath();
                    if(tempFilePath.contains(".")){
                        String suffix = IOUtil.getFileSuffix(tempFilePath);
                        if(scanType.containsKey(suffix)){
                            FileInfo info = new FileInfo(tempFileName,tempFilePath,false);
                            mInfo.add(info);
                        }
                    }
                }else{
                    scanSDCard(mFile,scanType);
                }
            }
        } else if (file.isFile()) {
            String tempFileName = file.getName();
            String tempFilePath = file.getAbsolutePath();
            if(tempFilePath.contains(".")){
                String suffix = IOUtil.getFileSuffix(tempFilePath);
                if(scanType.containsKey(suffix)){
                    FileInfo info = new FileInfo(tempFileName,tempFilePath,false);
                    mInfo.add(info);
                }
            }

        }
        return mInfo;
    }
}
