package umeng.x.con.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by root on 16-6-14.
 */
public class IntentUtil {
    /**
     * 创建照相Intent 返回的Intent为null
     * */
    public static Intent createPhotoTakeIntent(String outPutUri) {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(outPutUri);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return it;
    }

    /**
     * 创建裁剪Intent Activity需要设置为：
     * android:configChanges="orientation|keyboardHidden"
     * */
    public static Intent createPhotoCropIntent(String sourceImageUri, String outPutUri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP", null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        int n = getA(width, height);
        int ax = width / n;
        int ay = height / n;
        intent.putExtra("aspectX", ax);
        intent.putExtra("aspectY", ay);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(outPutUri)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.setDataAndType(Uri.fromFile(new File(sourceImageUri)), "image/jpeg");
        return intent;
    }

    // 求最大公约数
    private static int getA(int m, int n) {
        int min = Math.min(m, n);
        int a = 1;
        for (int i = min; i > 0; i--) {
            if (m % i == 0 && n % i == 0) {
                a = i;
                break;
            }
        }
        return a;
    }
}
