package com.wzf.beijingnews.pager.homepagers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.wzf.beijingnews.activities.MainActivity;
import com.wzf.beijingnews.base.BasePager;
import com.wzf.beijingnews.base.NewsPager;
import com.wzf.beijingnews.bean.FirstLinkNet;
import com.wzf.beijingnews.pager.newspagers.DetailNewsPager;
import com.wzf.beijingnews.pager.newspagers.InteractPager;
import com.wzf.beijingnews.pager.newspagers.PhotosPager;
import com.wzf.beijingnews.pager.newspagers.TopicPager;
import com.wzf.beijingnews.utils.ConstantsUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class NewsFragment extends BasePager {

    private List<NewsPager> pagers;
    private FirstLinkNet firstLinkNet;

    public NewsFragment(Context context) {
        super(context);
    }

    public void switchPager(int prePosition) {
        //1.修改标题
        tvTitle.setText(firstLinkNet.getData().get(prePosition).getTitle());

        //2.切换页面-去除重复
        NewsPager pager = pagers.get(prePosition);//有可能是NewsMenuDetailPager，TopicMenuDetailPager，PhotosMenuDetailPager，InteractMenuDetailPager
        pager.initData();//初始化数据

        flContent.removeAllViews();
        //添加到帧布局
        flContent.addView(pager.rootView);

        if (prePosition == 2) {
            ibSwich.setVisibility(View.VISIBLE);
            ibSwich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotosPager photosPager = (PhotosPager) pagers.get(2);
                    photosPager.switchListGrid(ibSwich);

                }
            });
        } else {
            ibSwich.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {

        pagers = new ArrayList<>();

        /**
         * 联网请求
         */
        RequestParams param = new RequestParams(ConstantsUtils.NEWSCENTER_PAGER_URL);
        x.http().get(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "我获得了数据源" + result);
                Gson gson = new Gson();
                firstLinkNet = gson.fromJson(result, FirstLinkNet.class);

                Log.e("TAG", "fir" + firstLinkNet.toString());
                pagers.add(new DetailNewsPager(mContext, firstLinkNet.getData().get(0)));
                pagers.add(new TopicPager(mContext));
                pagers.add(new PhotosPager(mContext, firstLinkNet.getData().get(2)));
                pagers.add(new InteractPager(mContext));

                ((MainActivity) mContext).getLeftFragment().setData(firstLinkNet.getData());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "NewsFragment - xutils联网出线了错误" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "xutils联网出线了错误" + cex.getMessage());
            }

            @Override
            public void onFinished() {
            }
        });
    }

}
