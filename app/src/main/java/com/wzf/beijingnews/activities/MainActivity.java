package com.wzf.beijingnews.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.wzf.beijingnews.R;
import com.wzf.beijingnews.fragments.ContentFragment;
import com.wzf.beijingnews.fragments.LeftFragment;
import com.wzf.beijingnews.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SlidingFragmentActivity {

    public static final String LEFT_MENU = "left_menu";
    public static final String CONTENT_MAIN = "content_main";
    @BindView(R.id.activity_main)
    FrameLayout activityMain;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();

        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu, new LeftFragment(), LEFT_MENU)
                .replace(R.id.activity_main, new ContentFragment(), CONTENT_MAIN).commit();
    }

    private void initSlidingMenu() {
        //1.设置主页面-中间布局
        setContentView(R.layout.activity_main);
        //2.设置左侧菜单
        setBehindContentView(R.layout.left_menu);

        ButterKnife.bind(this);
        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
//        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        //4.设置视图模式：左侧菜单 + 主页；左侧菜单+ 主页+ 右侧菜单；主页+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置触摸模式：不可以触摸滑动；边缘，全屏
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //6.设置主页面占200dp
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this, 200));
    }
}
