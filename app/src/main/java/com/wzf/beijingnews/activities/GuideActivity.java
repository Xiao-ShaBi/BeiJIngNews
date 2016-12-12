package com.wzf.beijingnews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wzf.beijingnews.R;
import com.wzf.beijingnews.utils.CacheUtils;
import com.wzf.beijingnews.utils.DensityUtil;
import com.wzf.beijingnews.utils.VersionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.activity_guide)
    RelativeLayout activityGuide;
    @BindView(R.id.btn_guide_into)
    Button btnGuideInto;
    @BindView(R.id.ll_guide)
    LinearLayout llGuide;
    @BindView(R.id.rl_guide)
    RelativeLayout rlGuide;

    private int[] welcomePhotos = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private int width;
    private ImageView imageRedPoint;
    private int margin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initViewPager();

        initPoint();
    }

    private void initPoint() {
        width = DensityUtil.dip2px(this, 10);

        for (int i = 0; i < welcomePhotos.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            if (i != 0) {
                params.leftMargin = width;
            } else {
                params.leftMargin = 0;
            }
            ImageView imageView = new ImageView(this);

            imageView.setBackgroundResource(R.drawable.guide_gree_point);
            imageView.setLayoutParams(params);

            llGuide.addView(imageView);
        }
        //设置生成红色小圆点
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        imageRedPoint = new ImageView(this);
        params.leftMargin = 0;
        imageRedPoint.setBackgroundResource(R.drawable.guide_red_point);
        imageRedPoint.setLayoutParams(params);
        rlGuide.addView(imageRedPoint);

        imageRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            imageRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //计算两个点的间距
            margin = llGuide.getChildAt(1).getLeft() - llGuide.getChildAt(0).getLeft();
        }
    }

    private void initViewPager() {
        vpGuide.setAdapter(new MyPagerAdapter());
        vpGuide.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageRedPoint.getLayoutParams();
            params.leftMargin = position * margin + (int) (margin * positionOffset);
            imageRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            Log.e("TAG", "position" + position);
            btnGuideInto.setVisibility(View.INVISIBLE);
            if (position == welcomePhotos.length - 1) {
                btnGuideInto.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(welcomePhotos[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return welcomePhotos.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @OnClick(R.id.btn_guide_into)
    public void onClick() {
        CacheUtils.setVersion(this, VersionUtils.getVersion(this));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
