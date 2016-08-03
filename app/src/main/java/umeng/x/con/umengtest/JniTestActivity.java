package umeng.x.con.umengtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by root on 16-8-2.
 */
public class JniTestActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_jni_test);
        TextView tvDisPlay = (TextView)findViewById(R.id.display_tv);
        JniTest.set("Hello world from JniTestApp");
        tvDisPlay.setText(JniTest.get());
    }
}
