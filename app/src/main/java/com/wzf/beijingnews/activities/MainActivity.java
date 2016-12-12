package com.wzf.beijingnews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wzf.beijingnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.test)
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


//        SharedPreferences sp = getSharedPreferences("test", Context.MODE_PRIVATE);
//        sp.edit().putString("123", "123").commit();

//        SharedPreferences sp1 = getSharedPreferences("test", Context.MODE_PRIVATE);
//        String string = sp1.getString("123", "321");
//        test.setText(string);
    }
}
