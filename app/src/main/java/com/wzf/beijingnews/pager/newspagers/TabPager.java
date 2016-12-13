package com.wzf.beijingnews.pager.newspagers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wzf.beijingnews.R;
import com.wzf.beijingnews.base.NewsPager;
import com.wzf.beijingnews.bean.FirstLinkNet;
import com.wzf.beijingnews.bean.TabData;
import com.wzf.beijingnews.utils.ConstantsUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用来创建12个标签页面
 */

public class TabPager extends NewsPager {

    private final FirstLinkNet.DataBean.ChildrenBean childrenBean;
    private ListView listView;
    private List<TabData.DataBean.NewsBean> news;
    private List<TabData.DataBean.TopnewsBean> topnews;
    private ViewPager viewPager;
    private LinearLayout ll_point_group;


    public TabPager(Context mContext, FirstLinkNet.DataBean.ChildrenBean childrenBean) {
        super(mContext);
        this.childrenBean = childrenBean;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.tabdetail_pager, null);
        listView = (ListView) view.findViewById(R.id.listview);

        View header = View.inflate(mContext, R.layout.topnews_pager, null);
        viewPager = (ViewPager) header.findViewById(R.id.viewpager);

        listView.addHeaderView(header);
        return view;
    }

    @Override
    public void initData() {
        RequestParams param = new RequestParams(ConstantsUtils.BASE_URL + childrenBean.getUrl());
        x.http().get(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "TabPager - 联网成功" + result);
                Gson gson = new Gson();
                TabData tabData = gson.fromJson(result, TabData.class);
                news = tabData.getData().getNews();
                topnews = tabData.getData().getTopnews();
                if (news.size() > 0 && topnews.size() > 0) {
                    listView.setAdapter(new MyAdapter());
                    viewPager.setAdapter(new MyPagerAdapter());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "联网得到一个错误" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            ImageView imageView = new ImageView(mContext);

            imageView.setImageResource(R.drawable.home_scroll_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            Glide.with(mContext).load(ConstantsUtils.BASE_URL + topnews.get(position).getTopimage()).into(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_tab_detail_pager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Glide.with(mContext).load(ConstantsUtils.BASE_URL + news.get(position).getListimage()).into(viewHolder.ivIcon);

            viewHolder.tvTitle.setText(news.get(position).getTitle());
            viewHolder.tvTime.setText(news.get(position).getPubdate());
//            viewHolder.iconNewsCommentNum.setImageResource();


            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.icon_news_comment_num)
        ImageView iconNewsCommentNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
