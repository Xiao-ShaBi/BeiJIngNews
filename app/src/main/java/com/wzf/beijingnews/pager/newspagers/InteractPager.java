package com.wzf.beijingnews.pager.newspagers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wzf.beijingnews.base.NewsPager;

/**
 * Created by Administrator on 2016/12/13.
 */

public class InteractPager extends NewsPager {

    public InteractPager(Context mContext) {
        super(mContext);
    }

    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setText("互动页面");
        return textView;
    }
}
