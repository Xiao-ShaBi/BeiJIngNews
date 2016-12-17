package com.wzf.beijingnews.pager.newspagers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;
import com.wzf.beijingnews.R;
import com.wzf.beijingnews.activities.MainActivity;
import com.wzf.beijingnews.base.NewsPager;
import com.wzf.beijingnews.bean.FirstLinkNet;

/**
 * Created by Administrator on 2016/12/13.
 */

public class DetailNewsPager extends NewsPager {

    private final FirstLinkNet.DataBean dataBean;
    private ViewPager viewPager;
    private TabPageIndicator tabPageIndicator;
    private ImageButton ib_next;

    public DetailNewsPager(Context mContext, FirstLinkNet.DataBean dataBean) {
        super(mContext);
        Log.e("TAG", "dataBean" + dataBean.getChildren().size());
        this.dataBean = dataBean;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.news_menu_detail_pager, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tabPageIndicator);
        ib_next = (ImageButton) view.findViewById(R.id.ib_next);
        return view;
    }

    @Override
    public void initData() {
        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        tabPageIndicator.setViewPager(viewPager);
        //监听页面的改变
        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity mainActivity = (MainActivity) mContext;
                if (position == 0) {
                    //SlidingMenu可以侧滑
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    //不可以侧滑
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        ib_next.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TabPager tabPager = new TabPager(mContext, dataBean.getChildren().get(position));
            tabPager.initData();
            View view = tabPager.rootView;
            container.addView(view);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dataBean.getChildren().get(position).getTitle();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return dataBean.getChildren().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
