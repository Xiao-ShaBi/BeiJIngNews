package com.wzf.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wzf.beijingnews.R;
import com.wzf.beijingnews.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/12.
 */

public class BasePager {

    protected Context mContext;

    public View rootView;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.ib_menu)
    public ImageButton ibMenu;
    @BindView(R.id.fl_content)
    public FrameLayout flContent;

    public BasePager(Context context) {
        mContext = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.basepager, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.ib_menu)
    public void onClick() {
        //关于侧滑的开关
        MainActivity mainActivity = (MainActivity) mContext;
        mainActivity.getSlidingMenu().toggle();//关<->开
    }

    /**
     * 1.当需要联网的时候
     * 2.当需要绑定数据到ui的时候
     */
    public void initData() {

    }

}
