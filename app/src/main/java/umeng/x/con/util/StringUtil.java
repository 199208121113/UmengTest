package umeng.x.con.util;

import java.util.Locale;

/**
 * Created by root on 16-6-14.
 */
public class StringUtil {

    public static String toUpperCase(String str){
        if(isEmpty(str))
            return "";
        return str.toUpperCase(Locale.getDefault());
    }

    public static boolean isEmpty(String s){
        return s == null || s.trim().length() == 0;
    }
}
