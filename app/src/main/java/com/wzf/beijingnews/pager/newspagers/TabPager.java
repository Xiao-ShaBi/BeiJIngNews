package com.wzf.beijingnews.pager.newspagers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.wzf.beijingnews.R;
import com.wzf.beijingnews.base.NewsPager;
import com.wzf.beijingnews.bean.FirstLinkNet;
import com.wzf.beijingnews.bean.TabData;
import com.wzf.beijingnews.utils.ConstantsUtils;
import com.wzf.beijingnews.utils.DensityUtil;
import com.wzf.beijingnews.views.HorizontalScrollViewPager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.State.REFRESHING;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.State.RESET;

/**
 * 用来创建12个标签页面
 */

public class TabPager extends NewsPager {

    private static final int RUN_PAGER = 0;
    private final FirstLinkNet.DataBean.ChildrenBean childrenBean;
    private ListView listView;
    private List<TabData.DataBean.NewsBean> news;
    private List<TabData.DataBean.TopnewsBean> topnews;
    private HorizontalScrollViewPager viewPager;
    private LinearLayout ll_point_group;
    private PullToRefreshListView pullToRefresh;
    private TextView tv_title;
    private int currViewPager;
    private boolean isReflash = true;
    private TabData tabData;
    private MyAdapter myAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case RUN_PAGER:
                    viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % topnews.size());
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(RUN_PAGER, 3000);
                    break;

            }

        }
    };

    public TabPager(Context mContext, FirstLinkNet.DataBean.ChildrenBean childrenBean) {
        super(mContext);
        this.childrenBean = childrenBean;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.tabdetail_pager, null);
        pullToRefresh = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(mContext, "onPullDownToRefresh", Toast.LENGTH_SHORT).show();
                isReflash = true;
                getJsonFromNet(ConstantsUtils.BASE_URL + childrenBean.getUrl());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(mContext, "onPullUpToRefresh", Toast.LENGTH_SHORT).show();
                isReflash = false;
                getJsonFromNet(ConstantsUtils.BASE_URL + tabData.getData().getMore());
            }
        });
        listView = pullToRefresh.getRefreshableView();
        listView.setDividerHeight(0);

        View header = View.inflate(mContext, R.layout.topnews_pager, null);
        viewPager = (HorizontalScrollViewPager) header.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        tv_title = (TextView) header.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) header.findViewById(R.id.ll_point_group);
        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(mContext);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(REFRESHING, R.raw.refreshing_sound);
        pullToRefresh.setOnPullEventListener(soundListener);

        this.listView.addHeaderView(header);
        return view;
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tv_title.setText(topnews.get(position).getTitle());

            //1.把之前的设置默认
            ll_point_group.getChildAt(currViewPager).setEnabled(false);
            //2.把当前的设置高亮
            ll_point_group.getChildAt(position).setEnabled(true);

            currViewPager = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                //空闲状态
                case ViewPager.SCROLL_STATE_IDLE:
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(RUN_PAGER, 3000);
                    break;
                //拖拽状态
                case ViewPager.SCROLL_STATE_DRAGGING:
                    handler.removeCallbacksAndMessages(null);
                    break;
                //移动状态
                case ViewPager.SCROLL_STATE_SETTLING:
                    handler.removeCallbacksAndMessages(null);
                    break;
            }
        }
    }

    @Override
    public void initData() {
        getJsonFromNet(ConstantsUtils.BASE_URL + childrenBean.getUrl());
    }

    private void getJsonFromNet(String url) {
        RequestParams param = new RequestParams(url);
        x.http().get(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getListFromJson(result);
                pullToRefresh.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("TAG", "联网得到一个错误" + ex.getMessage());
                Toast.makeText(mContext, "联网出线了错误", Toast.LENGTH_SHORT).show();
                pullToRefresh.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getListFromJson(String result) {
        Gson gson = new Gson();
        tabData = gson.fromJson(result, TabData.class);
        if (isReflash) {
            news = tabData.getData().getNews();
            topnews = tabData.getData().getTopnews();
            tv_title.setText(topnews.get(currViewPager).getTitle());

            if (news.size() > 0 && topnews.size() > 0) {
                myAdapter = new MyAdapter();
                listView.setAdapter(myAdapter);
                viewPager.setAdapter(new MyPagerAdapter());

                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(RUN_PAGER, 3000);
            }

            ll_point_group.removeAllViews();
            for (int i = 0; i < topnews.size(); i++) {
                ImageView imageView = new ImageView(mContext);

                imageView.setBackgroundResource(R.drawable.topnews_point_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    imageView.setEnabled(true);
                } else {
                    imageView.setEnabled(false);
                    params.leftMargin = DensityUtil.dip2px(mContext, 10);
                }

                imageView.setLayoutParams(params);

                //添加
                ll_point_group.addView(imageView);
            }
        } else {
            news.addAll(tabData.getData().getNews());
            myAdapter.notifyDataSetChanged();
        }


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


            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(RUN_PAGER, 3000);
                            break;
                    }
                    return false;
                }
            });

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
