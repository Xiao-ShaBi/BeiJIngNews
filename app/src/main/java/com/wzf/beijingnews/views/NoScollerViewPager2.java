package com.wzf.beijingnews.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/12/13.
 */

public class NoScollerViewPager2 extends ViewPager {

    public NoScollerViewPager2(Context context) {
        this(context, null);
    }

    public NoScollerViewPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
