package com.example.qinxue.lean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by qinxue on 2017/9/8.
 */
public class ContactsAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    public static final int NORMAL = 0;
    public static final int SELECT = 1;
    private int mCurrentModel;
    private List<Friend> mList;

    public ContactsAdapter(Context context, int model) {
        init(context, model);
        mList = MainActivity.userIdList;
    }

    public ContactsAdapter(Context context, List<Friend> list, int model) {
        init(context, model);
        mList = list;
    }

    private void init(Context context, int model) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCurrentModel = model;
    }

    public int getcheckedCount() {
        int count = 0;
        for (Friend friend : mList) {
            if (friend.isCheck()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Friend getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_layout, null);
            holder = new ViewHolder();
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.portrait = (ImageView) convertView.findViewById(R.id.portrait);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.id = (TextView) convertView.findViewById(R.id.user_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mCurrentModel == 0) {
            holder.checkbox.setVisibility(View.GONE);
        } else {
            holder.checkbox.setChecked(getItem(position).isCheck());
            holder.checkbox.setVisibility(View.VISIBLE);
        }
        Picasso.with(mContext).load(mList.get(position).getPortraitUri()).into(holder.portrait);
        holder.name.setText(mList.get(position).getName());
        holder.id.setText(mList.get(position).getUserId());
        return convertView;
    }

    private static class ViewHolder {
        CheckBox checkbox;
        ImageView portrait;
        TextView name;
        TextView id;
    }
}