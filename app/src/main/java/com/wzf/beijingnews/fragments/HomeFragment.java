package com.wzf.beijingnews.fragments;

import android.view.View;
import android.widget.TextView;

import com.wzf.beijingnews.base.BaseFragment;

/**
 * Created by Administrator on 2016/12/12.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View initView(View view) {
        TextView textView = new TextView(mContext);
        textView.setText("我是主界面");
        return textView;
    }
}
