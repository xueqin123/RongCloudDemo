package com.example.qinxue.lean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import io.rong.imkit.RongIM;

/**
 * Created by qinxue on 2017/8/30.
 */

public class FriendFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static FriendFragment instance = null;//单例模式
    private ListView mList = null;
    private ListView mGroupList = null;
    private LayoutInflater mInflater;

    public static FriendFragment getInstance() {
        if (instance == null) {
            synchronized (FriendFragment.class) {
                if (instance == null) {
                    instance = new FriendFragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.friend_fragment_layout, null);
        mInflater = getActivity().getLayoutInflater();
        mList = (ListView) mView.findViewById(R.id.contacts);
        mList.setAdapter(new ContactsAdapter(getContext(), ContactsAdapter.NORMAL));
        mList.setOnItemClickListener(this);
        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(getActivity(), MainActivity.userIdList.get(position).getUserId(), MainActivity.userIdList.get(position).getName());
        }
    }


}
