package com.example.qinxue.lean;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by qinxue on 2017/11/22.
 */

public class GridViewActivity extends Activity {
    private static GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGridView = new GridView(getApplicationContext());
        MAdapter adapter = new MAdapter(mGridView.getContext(), new String[]{"item1", "item2", "item3",
                "item4", "item5", "item6", "item7", "item8", "item9", "item10", "item11", "item12",
                "item13", "item14", "item15", "item16", "item17", "item18", "item19", "item20",
                "item21", "item22", "item23", "item24", "item25", "item26", "item27", "item28", "item29", "item30", "item31", "item32",
                "item33", "item34", "item35", "item36", "item37", "item38", "item39", "item40"});

        mGridView.setAdapter(adapter);
        mGridView.setNumColumns(2);
        setContentView(mGridView);
    }

    class MAdapter extends BaseAdapter {

        private String[] items;
        private Context context;

        public MAdapter(Context context, String[] items) {
            this.items = items;
            this.context = context;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (position % 4 == 0) {
                textView = new MyText(context);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, 100);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                textView.setText(items[position]);
                textView.setBackgroundColor(Color.GRAY);
                textView.setGravity(Gravity.CENTER);
            } else {
                textView = new TextView(context);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, 200);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                textView.setText(items[position]);
                textView.setVisibility(View.VISIBLE);
                if (position % 4 == 1) {
                    AbsListView.LayoutParams params2 = new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.MATCH_PARENT, 100);
                    textView.setLayoutParams(params2);
                    textView.setVisibility(View.INVISIBLE);
                }
                textView.setGravity(Gravity.CENTER);
            }
            return textView;
        }

        class MyText extends TextView {

            public MyText(Context context) {
                super(context);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                int targetWidth = mGridView.getMeasuredWidth() - mGridView.getPaddingLeft() - mGridView.getPaddingRight();
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(targetWidth, MeasureSpec.getMode(widthMeasureSpec));
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }

        }

    }

}
