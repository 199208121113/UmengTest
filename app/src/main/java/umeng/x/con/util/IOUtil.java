package umeng.x.con.util;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by root on 16-6-14.
 */
public class IOUtil {

    public static boolean fileExist(String path) {
        if (path == null || path.trim().length() == 0)
            return false;
        File file = new File(path);
        return file.exists();
    }

    public static boolean saveBitmap(String fileFullPath, Bitmap bmp, final int quality) throws Exception{
        boolean saved = false;
        if(bmp == null || bmp.isRecycled())
            return saved;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(fileFullPath));
            bmp.compress(Bitmap.CompressFormat.JPEG, quality,bos);
            bos.flush();
            saved = true;
        } finally{
            if(bos != null){
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bos = null;
        }
        return saved;
    }

    public static String getFileSuffix(String fn) {
        if (fn == null || fn.trim().length() == 0)
            return null;
        int index = fn.lastIndexOf(".");
        if (index < 0)
            return null;
        return fn.substring(index);
    }
}
