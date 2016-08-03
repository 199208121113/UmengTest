package umeng.x.con.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * View事件学习
 * Created by root on 16-7-27.
 */
public class LearnView extends ViewGroup {

    public LearnView(Context context) {
        this(context,null);
    }

    public LearnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**系统所能识别出的最小滑动距离（根据系统而言）*/
        int systemScrollMin = ViewConfiguration.get(context).getScaledTouchSlop();

    }

/**===============1.VelocityTracker用于滑动的速度追踪 ===========================*/
//    VelocityTracker velocityTracker = VelocityTracker.obtain();
//    velocityTracker.addMovement(event);
//
//    获取当前滑动速度  必须先调用computeCurrentVelocity方法
//    这里的速度 = 终点位置 - 起点位置 / times -->Mark:times = 1s则表示1s内滑过的屏幕像素点（值可能小于0：从右到左））
//    velocityTracker.computeCurrentVelocity(1000);
//    int xVelocityTracker = (int)velocityTracker.getXVelocity();
//    int yVelocityTracker = (int)velocityTracker.getYVelocity();
//
//    当不需要使用的时候 注意回收
//    velocityTracker.clear();
//    velocityTracker.recycle();


/**===============1.GestureDetector辅助检测用户的单击/长按/滑动/双击行为 ===========================*/
//    1.首先初始化
//    GestureDetector gestureDetector = new GestureDetector(this);
//    解决长按屏幕后无法拖动的现象
//    gestureDetector.setIsLongpressEnabled(false);

//    2.接管view的OnTouchEvent方法
//    boolean consume = gestureDetector.onTouchEvent(event);

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);

        if(childCount == 0){
            setMeasuredDimension(0,0);
        }else if(widthSpaceMode == MeasureSpec.AT_MOST && heightSpaceMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight() * childCount;
            setMeasuredDimension(measuredWidth,measuredHeight);
        }else if(heightSpaceMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize,childView.getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
