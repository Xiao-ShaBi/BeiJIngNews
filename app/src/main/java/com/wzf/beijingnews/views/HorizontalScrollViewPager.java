package com.wzf.beijingnews.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float startX;
    private float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG", "这里是一次分发事件" + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                float endX = Math.abs(startX - ev.getX());
                float endY = Math.abs(startY - ev.getY());
                Log.e("TAG", "getX()" + ev.getX());
                Log.e("TAG", "getY()" + ev.getY());
                if (endX > endY) {
                    if (getCurrentItem() == 0 && startX - ev.getX() <= 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (getCurrentItem() == getAdapter().getCount() - 1 && startX - ev.getX() > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
