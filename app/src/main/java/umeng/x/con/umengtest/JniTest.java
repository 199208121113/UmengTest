package umeng.x.con.umengtest;

import java.io.Serializable;

/**
 * Created by root on 16-8-2.
 */
public class JniTest implements Serializable{
    static {
        System.loadLibrary("jni-test");
    }

    public static native String get();
    public static native void set(String str);
}
