package com.wzf.beijingnews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wzf.beijingnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Activity that gets transitioned to
 */
public class ActivityTransitionToActivity extends AppCompatActivity {

    @BindView(R.id.iv_photo)
    PhotoView ivPhoto;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_to);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final PhotoViewAttacher attacher = new PhotoViewAttacher(ivPhoto);
        url = getIntent().getStringExtra("url");
        Picasso.with(this)
                .load(url)
                .into(ivPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
