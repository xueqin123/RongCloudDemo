package com.example.qinxue.lean;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by qinxue on 2017/9/8.
 */

public class BaseActivity extends FragmentActivity {
    FrameLayout container;
    ViewGroup content;
    Button mBack;
    Button mOk;
    TextView mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.base_activity_layout);
        container = (FrameLayout) findViewById(R.id.container);
        mBack = (Button) findViewById(R.id.back);
        mOk = (Button) findViewById(R.id.ok);
        mTitle = (TextView) findViewById(R.id.title);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick(v);
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOKClick(v);
            }
        });
        content = (ViewGroup) getLayoutInflater().inflate(layoutResID, null);
        container.addView(content);
    }

    void onBackClick(View view) {

    }

    void onOKClick(View view) {

    }

    void setTitle(String string) {
        if (mTitle != null) {
            mTitle.setText(string);
        }
    }

    public Button getmBack() {
        return mBack;
    }


    public Button getmOk() {
        return mOk;
    }


    public TextView getmTitle() {
        return mTitle;
    }
}
