package com.wzf.beijingnews.fragments;

import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wzf.beijingnews.R;
import com.wzf.beijingnews.activities.MainActivity;
import com.wzf.beijingnews.base.BaseFragment;
import com.wzf.beijingnews.base.BasePager;
import com.wzf.beijingnews.pager.homepagers.HomeFragment;
import com.wzf.beijingnews.pager.homepagers.NewsFragment;
import com.wzf.beijingnews.pager.homepagers.SettingFragment;

import java.util.ArrayList;

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

    private ArrayList<BasePager> pagers;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public View initView(View view) {
        ButterKnife.bind(this, view);
        return super.initView(view);
    }

    public NewsFragment getNewsFragment() {
        return (NewsFragment) pagers.get(1);
    }

    @Override
    protected void initData() {
        //ViewPager绑定页面
        pagers = new ArrayList<>();
        pagers.add(new HomeFragment(mContext));//添加主页面
        pagers.add(new NewsFragment(mContext));//添加新闻中心页面
        pagers.add(new SettingFragment(mContext));//添加设置中心页面

        //设置适配器
        viewpager.setAdapter(new ContentFragmentAdapter());

        //设置RadioGroup状态变化的监听
        rgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //默认选中主页面
        rgMain.check(R.id.rb_home);

        //监听ViewPager状态的变化
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                BasePager basePager =  pagers.get(position);//HomePager,NewsCenterPager,SettingPager
                pagers.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagers.get(0).initData();//设置默认页面数据
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //不可以拖拽
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            switch (checkedId) {
                case R.id.rb_home://首页
                    viewpager.setCurrentItem(0, false);

                    break;
                case R.id.rb_news://新闻中心
                    viewpager.setCurrentItem(1, false);
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    //可以拖拽
                    break;
                case R.id.rb_setting://设置
                    viewpager.setCurrentItem(2, false);
                    //不可以拖拽
                    break;
            }

        }
    }

    class ContentFragmentAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = pagers.get(position);//HomePager，NewsCenterPager，SettingPager
            //代表某个页面
            View rootView = basePager.rootView;
            //添加到容器中
            container.addView(rootView);

            //调用initData
//            basePager.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
