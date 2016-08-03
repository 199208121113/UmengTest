package umeng.x.con.umengtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import umeng.x.con.util.IntentUtil;
import umeng.x.con.util.ScreenUtil;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btnPhoto,btnImg,btnMedia,btnAudio,btnWen;
    private ImageView ivPhoto;
    //拍照相关参数
    private static final int PHOTO_RESULT = 1111;//拍照返回
    private static final int PHOTO_CROP_RESULT = 1113;//剪裁返回
    private String outPutUri = Environment.getExternalStorageDirectory()+ "/outputUri.jpg";

    private TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        new MyAsyncTask(){
        }.execute(new String[]{"1","2","3"});
    }

    void initView(){
        btnImg = (Button)findViewById(R.id.btn_img);
        btnImg.setOnClickListener(this);
        btnPhoto = (Button)findViewById(R.id.btn_photo);
        btnPhoto.setOnClickListener(this);
        btnMedia = (Button)findViewById(R.id.btn_media);
        btnMedia.setOnClickListener(this);
        ivPhoto = (ImageView)findViewById(R.id.photo_img);
        btnAudio = (Button)findViewById(R.id.btn_audio);
        btnAudio.setOnClickListener(this);
        btnWen = (Button)findViewById(R.id.btn_wen);
        btnWen.setOnClickListener(this);
        tvTest = (TextView)findViewById(R.id.tv_test_translation);
        TransitionDrawable transitionDrawable = (TransitionDrawable)tvTest.getBackground();
        transitionDrawable.startTransition(10000);
    }

    @Override
    public void onClick(View v) {
        if(v == btnImg){
            startActivity(ChoiceImgActivity.createIntent(this,"最近照片"));
        }else if(v == btnPhoto){
            startActivityForResult(IntentUtil.createPhotoTakeIntent(outPutUri),PHOTO_RESULT);
        }else if(v == btnMedia){
            sendNotify();
//            startActivity(ChoiceMediaActivity.createIntent(this));
        }else if(v == btnAudio){
            startActivity(ChoiceAudioActivity.createIntent(this));
        }else if(v == btnWen){
            startActivity(ChoiceWenActivity.createIntent(this));
        }
    }

    private void sendNotify(){
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText = "Hello Notification";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

//        Intent intent = ChoiceMediaActivity.createIntent(this);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);

    }

    private class MyAsyncTask  extends AsyncTask<String,Integer,List<String>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... params) {
            Log.e("MyAsyncTask","doInBackground:"+params[0]);
            Log.e("MyAsyncTask","doInBackground:"+params[1]);
            Log.e("MyAsyncTask","doInBackground:"+params[2]);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        if(requestCode == PHOTO_RESULT){
            startActivityForResult(IntentUtil.createPhotoCropIntent(
                    outPutUri, outPutUri, ScreenUtil.dip2px(this, 65),
                    ScreenUtil.dip2px(this, 65)), PHOTO_CROP_RESULT);
        }else if(requestCode == PHOTO_CROP_RESULT){
            Bitmap uploadBitmap= BitmapFactory.decodeFile(outPutUri);
            ivPhoto.setImageBitmap(uploadBitmap);
        }
    }
}
