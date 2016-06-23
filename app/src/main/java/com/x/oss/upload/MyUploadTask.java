package com.x.oss.upload;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.utils.BinaryUtil;
import com.aliyun.oss.ossdemo.PauseableUploadTask;

import java.io.IOException;
import java.util.Map;

/**
 * Created by root on 16-6-23.
 */
public class MyUploadTask implements Runnable{

    private String localFile;
    private String bucket;
    private String object;
    private Map<String, String> multiPartStatus;
    private PauseableUploadTask task;
    private int partSize;

    public MyUploadTask(String localFile, String bucket, String object, Map<String, String> multiPartStatus,
                        PauseableUploadTask task, int partSize) {
        this.localFile = localFile;
        this.bucket = bucket;
        this.object = object;
        this.multiPartStatus = multiPartStatus;
        this.task = task;
        this.partSize = partSize;
    }

    @Override
    public void run() {
        try {
            //计算上传任务的唯一校验码，将文件的MD5、bucket、object和分片大小编码成一个校验码来校验本次上传任务和上次上传任务是否一致
            String fileMd5 = BinaryUtil.calculateMd5Str(localFile);
            String totalMd5 = BinaryUtil.calculateMd5Str((fileMd5 + bucket + object + String.valueOf(partSize)).getBytes());

            Log.d("MultipartUploadMd5", totalMd5);

            String uploadId = multiPartStatus.get(totalMd5);

            //如果没有找到对应的上传记录，表示是新的上传任务，因此使用initupload来初始化一个断点上传（分片上传）任务
            if (uploadId == null) {
                uploadId = task.initUpload();
                Log.d("InitUploadId", uploadId);
                multiPartStatus.put(totalMd5, uploadId);
            } else {
                //如果之前已经存在一样的上传任务，那么继续使用上次的uploadId
                Log.d("GetPausedUploadId", uploadId);
            }

            //使用uploadId来完成上传
            task.upload(uploadId);
            if (task.isComplete()) {
                multiPartStatus.remove(totalMd5);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
