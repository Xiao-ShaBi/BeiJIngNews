package com.wzf.beijingnews.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wzf.beijingnews.R;
import com.wzf.beijingnews.activities.MainActivity;
import com.wzf.beijingnews.base.BaseFragment;
import com.wzf.beijingnews.bean.FirstLinkNet;
import com.wzf.beijingnews.pager.homepagers.NewsFragment;
import com.wzf.beijingnews.utils.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class LeftFragment extends BaseFragment {

    private ListView listView;
    private List<FirstLinkNet.DataBean> data;
    private LeftMenuAdapter adapter;
    private int prePosition = 0;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View initView(View view) {

        listView = new ListView(mContext);
        listView.setPadding(0, DensityUtil.dip2px(mContext, 40), 0, 0);
        listView.setDividerHeight(0);

        return listView;
    }

    public void setData(List<FirstLinkNet.DataBean> data) {
        this.data = data;

        adapter = new LeftMenuAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new MyOnItemClickListener());

        switchPager();
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            prePosition = position;

            adapter.notifyDataSetChanged();

            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.getSlidingMenu().toggle();

            switchPager();
        }
    }

    private void switchPager() {
        MainActivity mainActivity = (MainActivity) mContext;
        NewsFragment newsFragment = mainActivity.getContentFragment().getNewsFragment();
        newsFragment.switchPager(prePosition);
    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
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
            TextView textView = (TextView) View.inflate(mContext, R.layout.item_left_menu, null);
            textView.setText(data.get(position).getTitle());

            if (position == prePosition) {
                //变红色
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
            }

            return textView;
        }
    }

}
