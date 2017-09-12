package com.example.qinxue.lean;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ConversationActivity";
    private TextView mName;
    private String mCurrentId;
    private String nCurrentName;
    private EditText mEditText;
    private Button mButton;
    private ArrayList<String> mMemberList = new ArrayList<>();
    private static final int REMOVE_REQUEST = 0;
    private static final int ADD_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mName = getmTitle();
        getmOk().setText("more");
        mCurrentId = getIntent().getData().getQueryParameter("targetId"); //targetId:单聊即对方ID，群聊即群组ID
        String sName = getIntent().getData().getQueryParameter("title"); //获取昵称
        nCurrentName = sName;
        if (!TextUtils.isEmpty(sName)) {
            mName.setText(sName);
        }
        mEditText = (EditText) findViewById(R.id.new_name);
        mButton = (Button) findViewById(R.id.rename);
        mButton.setOnClickListener(this);
        RongIMClient.getInstance().getDiscussion(mCurrentId, mCallBack);
        RongIMClient.getInstance().setTypingStatusListener(typingStatusListener);

    }

    RongIMClient.TypingStatusListener typingStatusListener = new RongIMClient.TypingStatusListener() {

        @Override
        public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
//            Log.i(TAG, "onTypingStatusChanged() type = " + type + " targetId = " + targetId + "typingStatusSet.size() = " + typingStatusSet.size());
            final int size = typingStatusSet.size();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "Thread.currentThread() = " + Thread.currentThread());
                    if (size != 0) {
                        mName.setText("(正在输入...）");
                    } else {
                        mName.setText(nCurrentName);
                    }
                }
            });
        }
    };

    RongIMClient.ResultCallback mCallBack = new RongIMClient.ResultCallback() {

        @Override
        public void onSuccess(Object o) {
            Log.i(TAG, "getDiscussion onSuccess()");
            Discussion discussion = (Discussion) o;
            mMemberList = (ArrayList<String>) discussion.getMemberIdList();
            final StringBuilder stringBuilder = new StringBuilder();
            for (String str : mMemberList) {
                stringBuilder.append(str);
                stringBuilder.append(", ");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mName.setText(stringBuilder.toString());
                }
            });
        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {
            Log.i(TAG, "getDiscussion onError()");

        }
    };

    RongIMClient.OperationCallback mAddremoveCallBack = new RongIMClient.OperationCallback() {

        @Override
        public void onSuccess() {
            Log.i(TAG, "onSuccess() Thread.currentThread().getName() = " + Thread.currentThread().getName());

        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {
            Log.i(TAG, "onError() Thread.currentThread().getName() = " + Thread.currentThread().getName());
        }
    };

    @Override
    void onBackClick(View view) {
        finish();
    }


    @Override
    void onOKClick(View view) {

        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu = popupMenu.getMenu();
        menu.add(0, 0, 0, "踢出成员");
        menu.add(0, 1, 0, "加入成员");
        menu.add(0, 2, 0, "退出该组");
        menu.add(0, 3, 0, "解散该组");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "item.getItemId() = " + item.getItemId() + "mMemberList.size() = " + mMemberList.size());
                if (mMemberList.size() > 0) {
                    Intent intent = new Intent(ConversationActivity.this, SelectActivity.class);
                    intent.putStringArrayListExtra("group_members", mMemberList);
                    switch (item.getItemId()) {
                        case 0:
                            intent.setAction("action_remove");
                            startActivityForResult(intent, REMOVE_REQUEST);
                            break;
                        case 1:
                            intent.setAction("action_add");
                            startActivityForResult(intent, ADD_REQUEST);
                            break;
                        case 2:

                            break;
                    }
                }

                return false;
            }
        });
    }
//
//    class operRunnable extends Runnable{
//        int module;
//        operRunnable(int module){
//
//        }
//
//        @Override
//        public void run() {
//
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> list = new ArrayList<>();
        if (data != null) {
            list = data.getStringArrayListExtra("selected_list");
        }
        if (list.size() != 0) {
            Log.i(TAG, "list.size() = " + list.size());
            switch (requestCode) {
                case REMOVE_REQUEST:
                    for (String id : list) {
                        RongIM.getInstance().removeMemberFromDiscussion(mCurrentId, id, mAddremoveCallBack);
                    }
                    break;
                case ADD_REQUEST:
                    RongIM.getInstance().addMemberToDiscussion(mCurrentId, list, mAddremoveCallBack);

                    break;
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        clear();
    }


    private void clear() {
        mAddremoveCallBack = null;
        mCallBack = null;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        String newName = mEditText.getText().toString();
        if (!TextUtils.isEmpty(newName)) {
            int index = 0;
            for (int i = 0; i < MainActivity.nickNameList.size(); i++) {
                if (MainActivity.nickNameList.get(i).getUserId().equals("2")) {
                    index = i;
                }
            }
            MainActivity.nickNameList.remove(index);
            GroupUserInfo newGroupInfo = new GroupUserInfo("group1", "2", newName);
            MainActivity.nickNameList.add(newGroupInfo);
            RongIM.getInstance().refreshGroupUserInfoCache(newGroupInfo);

        }
    }
}