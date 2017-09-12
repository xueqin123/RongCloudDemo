package com.example.qinxue.lean;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.model.UserInfo;

/**
 * Created by qinxue on 2017/9/8.
 */

public class MyInfoFragment extends Fragment implements View.OnClickListener {

    ImageView imageView;
    TextView textView;
    EditText mEditText;
    Button mButton;
    private Friend mFriend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup mView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.my_information_layout, null);
        imageView = (ImageView) mView.findViewById(R.id.image);
        textView = (TextView) mView.findViewById(R.id.name);
        mEditText = (EditText) mView.findViewById(R.id.rename);
        mButton = (Button) mView.findViewById(R.id.ok);
        mButton.setOnClickListener(this);
        for (Friend friend : MainActivity.userIdList) {
            if (friend.getUserId().equals(MainActivity.CURRENT_USER)) {
                mFriend = friend;
                Picasso.with(getContext()).load(friend.getPortraitUri()).into(imageView);
                textView.setText(friend.getName());
            }
        }
        return mView;
    }

    @Override
    public void onClick(View v) {



    }
}
