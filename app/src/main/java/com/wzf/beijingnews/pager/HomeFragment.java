package com.wzf.beijingnews.pager;

import android.content.Context;
import android.widget.TextView;

import com.wzf.beijingnews.base.BasePager;

/**
 * Created by Administrator on 2016/12/12.
 */

public class HomeFragment extends BasePager {

    public HomeFragment(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        //设置标题
        tvTitle.setText("主页");

        TextView textView = new TextView(mContext);
        textView.setText("这里是主页");
        flContent.addView(textView);
    }
}
