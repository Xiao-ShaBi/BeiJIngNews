package com.wzf.beijingnews.pager.newspagers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wzf.beijingnews.R;
import com.wzf.beijingnews.activities.ActivityTransitionToActivity;
import com.wzf.beijingnews.base.NewsPager;
import com.wzf.beijingnews.bean.FirstLinkNet;
import com.wzf.beijingnews.bean.PhotosBean;
import com.wzf.beijingnews.utils.ConstantsUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/12/13.
 */

public class PhotosPager extends NewsPager {

    FirstLinkNet.DataBean data;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private List<PhotosBean.DataBean.NewsBean> list;

    private PhotosAdapter photosAdapter;

    public PhotosPager(Context mContext, FirstLinkNet.DataBean dataBean) {
        super(mContext);
        data = dataBean;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.photos_menu_detail_pager, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        getDataFromNet(ConstantsUtils.BASE_URL + data.getUrl());
    }

    private void getDataFromNet(String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "PhotosPager - getDataFromNet" + result);

                Gson gson = new Gson();
                PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);

                PhotosBean.DataBean data = photosBean.getData();
                list = data.getNews();

                PhotoAdapter adapter = new PhotoAdapter();
//                listview.setAdapter(adapter);
//                gridview.setAdapter(adapter);
                recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
//                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                photosAdapter = new PhotosAdapter();
                recyclerview.setAdapter(photosAdapter);
                try {
                    photosAdapter.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
//                            Toast.makeText(mContext, "-> ->", Toast.LENGTH_SHORT).show();
                            transition(view, position);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "--" + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "PhotosPager - onError" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "PhotosPager - onError" + cex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void transition(View view, int position) {
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText(mContext, "21+ only, keep out", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(mContext, ActivityTransitionToActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) mContext, view, "test");
            Bundle bundle = options.toBundle();
            intent.putExtra("url", ConstantsUtils.BASE_URL + list.get(position).getLargeimage());
            mContext.startActivity(intent, bundle);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
            holder.tvTitle.setText(list.get(position).getTitle());
            Glide.with(mContext).load(ConstantsUtils.BASE_URL + list.get(position).getListimage()).into(holder.ivIcon);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.iv_icon)
            ImageView ivIcon;
            @BindView(R.id.tv_title)
            TextView tvTitle;
            public View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                ButterKnife.bind(this, itemView);
            }
        }
    }


    class PhotoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_photos_menu_detail_pager, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvTitle.setText(list.get(position).getTitle());
            Glide.with(mContext).load(ConstantsUtils.BASE_URL + list.get(position).getListimage()).into(viewHolder.ivIcon);

            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * true:显示ListView
     * false:显示GridView
     */
    private boolean isShowListView = true;

    public void switchListGrid(ImageButton ibSwich) {
        if (isShowListView) {

            //Gridandroid.support.v7.widget.GridLayoutManager
            recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false));

            //按钮- List
            ibSwich.setImageResource(R.drawable.icon_pic_list_type);
            isShowListView = false;
        } else {
            //ListLinearLayoutManager
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            //按钮-Grid
            ibSwich.setImageResource(R.drawable.icon_pic_grid_type);
            isShowListView = true;

        }

    }

}
