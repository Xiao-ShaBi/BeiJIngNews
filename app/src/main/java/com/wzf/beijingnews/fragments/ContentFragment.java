package com.wzf.beijingnews.fragments;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wzf.beijingnews.R;
import com.wzf.beijingnews.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ContentFragment extends BaseFragment {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_news)
    RadioButton rbNews;
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public View initView(View view) {
        ButterKnife.bind(this, view);
        return super.initView(view);
    }
}
