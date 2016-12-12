package com.wzf.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.wzf.beijingnews.base.BasePager;

/**
 * Created by Administrator on 2016/12/12.
 */

public class SettingFragment extends BasePager {

    public SettingFragment(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG", "设置数据加载了...");
        //设置标题
        tvTitle.setText("设置");
        //绑定数据
        //联网请求得到数据

        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        textView.setText("设置内容");

        //添加到帧布局
        flContent.addView(textView);

    }
}
