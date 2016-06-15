package umeng.x.con.umengtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import umeng.x.con.util.IntentUtil;
import umeng.x.con.util.ScreenUtil;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btnPhoto,btnImg,btnMedia,btnAudio,btnWen;
    private ImageView ivPhoto;
    //拍照相关参数
    private static final int PHOTO_RESULT = 1111;//拍照返回
    private static final int PHOTO_CROP_RESULT = 1113;//剪裁返回
    private String outPutUri = Environment.getExternalStorageDirectory()+ "/outputUri.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
    }

    @Override
    public void onClick(View v) {
        if(v == btnImg){
            startActivity(ChoiceImgActivity.createIntent(this,"最近照片"));
        }else if(v == btnPhoto){
            startActivityForResult(IntentUtil.createPhotoTakeIntent(outPutUri),PHOTO_RESULT);
        }else if(v == btnMedia){
            startActivity(ChoiceMediaActivity.createIntent(this));
        }else if(v == btnAudio){
            startActivity(ChoiceAudioActivity.createIntent(this));
        }else if(v == btnWen){
            startActivity(ChoiceWenActivity.createIntent(this));
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
