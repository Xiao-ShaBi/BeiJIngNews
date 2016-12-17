package com.wzf.beijingnews.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/12/13.
 */

public class NoScollerViewPager extends ViewPager {

    public NoScollerViewPager(Context context) {
        this(context, null);
    }

    public NoScollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        Log.e("TAG", "这里开始移动");
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
