package com.wzf.beijingnews.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2016/12/13.
 */

public abstract class NewsPager {

    public View rootView;

    public Context mContext;

    public NewsPager(Context mContext) {
        this.mContext = mContext;
        rootView = initView();
    }

    protected abstract View initView();

    public void initData() {
    }

}
