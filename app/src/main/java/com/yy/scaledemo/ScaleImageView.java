package com.yy.scaledemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


public class ScaleImageView extends AppCompatImageView implements View.OnTouchListener {
    public static final String TAG = "ScaleX5Webview";
    private boolean isCanTouch = false;
    public static final float SCALE_MAX = 5.0f; //最大的缩放比例
    private static final float SCALE_MIN = 1.0f;

    private double oldDist = 0;
    private double moveDist = 0;

    private float downX1 = 0;
    private float downX2 = 0;
    private float downY1 = 0;
    private float downY2 = 0;

    private float centerX = 0;
    private float centerY = 0;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public void setIsCanTouch(boolean canTouch) {
        isCanTouch = canTouch;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch isCanTouch= " + isCanTouch);
        if (!isCanTouch) {
            return false;
        }
        int pointerCount = event.getPointerCount();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
//                downX1 = event.getX();
//                downY1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (pointerCount == 2) {
                    downX1 = 0;
                    downY1 = 0;
                    downX2 = 0;
                    downY2 = 0;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointerCount == 2) {
                    float x1 = event.getX(0);
                    float x2 = event.getX(1);
                    float y1 = event.getY(0);
                    float y2 = event.getY(1);

                    double changeX1 = x1 - downX1;
                    double changeX2 = x2 - downX2;
                    double changeY1 = y1 - downY1;
                    double changeY2 = y2 - downY2;


                    moveDist = spacing(event);

//                    boolean changeMoveDist = Math.abs(moveDist - oldDist) > 15;
//                    boolean isSameDirectionX = (changeX1 >= 0 && changeX2 >= 0) || (changeX1 <= 0 && changeX2 <= 0);
//                    boolean isSameDirectionY = (changeY1 >= 0 && changeY2 >= 0) || (changeY1 <= 0 && changeY2 <= 0);
                    //if (getScaleX() > 1 && (isSameDirectionX && isSameDirectionY) && !changeMoveDist) { //滑动
                    if (getScaleX() > 1) { //滑动
                        float lessX = (float) ((changeX1) / 2 + (changeX2) / 2);
                        float lessY = (float) ((changeY1) / 2 + (changeY2) / 2);
                        setSelfPivot(lessX, lessY);
                        Log.d(TAG, "此时为滑动");
                    }
                    //if (changeMoveDist) { //缩放
                    moveDist = spacing(event);
                    double space = moveDist - oldDist;
                    float scale = (float) (getScaleX() + space / v.getWidth());
                    if (scale < SCALE_MIN) {
                        setScale(SCALE_MIN);
                    } else if (scale > SCALE_MAX) {
                        setScale(SCALE_MAX);
                    } else {
                        setScale(scale);
                    }
                    //}


//                    if (getScaleX() > 1 && isSameDirectionX && isSameDirectionY) { //滑动
//                        float lessX = (float) ((downX1 - x1) / 2 + (downX2 - x2) / 2);
//                        float lessY = (float) ((downY1 - y1) / 2 + (downY2 - y2) / 2);
//                        setSelfPivot(lessX, lessY);
//                        Log.d(TAG, "此时为滑动");
//                    } else if (!isSameDirectionX || !isSameDirectionY) { //缩放
//                        moveDist = spacing(event);
//                        double space = moveDist - oldDist;
//                        double sqrt = Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
//                        float scale = (float) (getScaleX() + space / sqrt);
//                        if (scale > SCALE_MIN && scale < SCALE_MAX) {
//                            setScale(scale);
//                        } else if (scale < SCALE_MIN) {
//                            setScale(SCALE_MIN);
//                        }
//                        Log.d(TAG, "此时为缩放");
//                    }
//                    if ((Math.abs(absX1 - absX2) < 20 && Math.abs(absy1 - absy2) < 20) && getScaleX() > 1) { // 滑动
//                        float lessX = (float) (downX1 - x1);
//                        float lessY = (float) (downY1 - y2);
//                        setSelfPivot(lessX, lessY);
//                    } else {
//                        moveDist = spacing(event);
//                        double space = moveDist - oldDist;
//                        float scale = (float) (getScaleX() + space / getWidth());
//                        if (scale > SCALE_MIN && scale < SCALE_MAX) {
//                            setScale(scale);
//                        } else if (scale < SCALE_MIN) {
//                            setScale(SCALE_MIN);
//                        }
//                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (pointerCount == 2) {
                    downX1 = event.getX(0);
                    downX2 = event.getX(1);
                    downY1 = event.getY(0);
                    downY2 = event.getY(1);
                    centerX = (downX1 + downX2) / 2;
                    centerY = (downY1 + downY2) / 2;
                    Log.d(TAG, "ACTION_POINTER_DOWN 双指按下 downX1=" + downX1 + " downX2="
                            + downX2 + "  downY1=" + downY1 + " downY2=" + downY2);
                    oldDist = spacing(event); //两点按下时的距离
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "ACTION_POINTER_UP");
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 触摸使用的移动事件
     *
     * @param lessX
     * @param lessY
     */
    private void setSelfPivot(float lessX, float lessY) {
        float setPivotX = 0;
        float setPivotY = 0;
        setPivotX = getPivotX() + lessX;
        setPivotY = getPivotY() + lessY;
        if (setPivotX < 0 && setPivotY < 0) {
            setPivotX = 0;
            setPivotY = 0;
        } else if (setPivotX > 0 && setPivotY < 0) {
            setPivotY = 0;
            if (setPivotX > getWidth()) {
                setPivotX = getWidth();
            }
        } else if (setPivotX < 0 && setPivotY > 0) {
            setPivotX = 0;
            if (setPivotY > getHeight()) {
                setPivotY = getHeight();
            }
        } else {
            if (setPivotX > getWidth()) {
                setPivotX = getWidth();
            }
            if (setPivotY > getHeight()) {
                setPivotY = getHeight();
            }
        }
        setPivot(setPivotX, setPivotY);
    }

    /**
     * 计算两个点的距离
     *
     * @param event
     * @return
     */
    private double spacing(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }

    /**
     * 平移画面，当画面的宽或高大于屏幕宽高时，调用此方法进行平移
     *
     * @param x
     * @param y
     */
    public void setPivot(float x, float y) {
        setPivotX(x);
        setPivotY(y);
    }


    /**
     * 设置放大缩小
     *
     * @param scale
     */
    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
    }

    /**
     * 初始化比例，也就是原始比例
     */
    public void setInitScale() {
        setScaleX(1.0f);
        setScaleY(1.0f);
        setPivot(getWidth() / 2, getHeight() / 2);
    }
}
