package com.x.oss.upload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.aliyun.oss.ossdemo.PauseableUploadTask;
import com.aliyun.oss.ossdemo.STSGetter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUploadService extends Service {
    private static final String TAG = FileUploadService.class.getSimpleName();

    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String imgEndpoint = "http://img-cn-hangzhou.aliyuncs.com";
    private static final String callbackAddress = "http://oss-demo.aliyuncs.com:23450";
    private static final String bucket = "";
    private String region = "杭州";

    private MyOssService ossService;
    private MyImageService imageService;
    private WeakReference<PauseableUploadTask> task;

    public MyOssService initOSS(String endpoint, String bucket) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        OSSCredentialProvider credentialProvider;
        //TODO 使用自己的获取STSToken的类 credentialProvider = new STSGetter(stsServer);
        credentialProvider = new STSGetter();

        //TODO 注意替换 bucket
        bucket = "sdk-demo";
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        return new MyOssService(oss, bucket);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //oncreate 执行初始化准备工作

    private ExecutorService limitedTaskExecutor = null;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"service is start");
        EventBus.getDefault().register(this);
        ossService = initOSS(endpoint,bucket);
        ossService.setCallbackAddress(callbackAddress);
        imageService = new MyImageService(initOSS(imgEndpoint,bucket));
        limitedTaskExecutor = Executors.newFixedThreadPool(3);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void doUploadEventBus(UploadEventType evt){
        Log.e(TAG,"doUploadEventBus>>>>>>");
        List<String> filePath = evt.getFilePathList();
        for (int i = 0;i < filePath.size(); i ++){
            ossService.asyncMultiPartUpload("fileName"+(i + 1) , filePath.get(i),limitedTaskExecutor);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
