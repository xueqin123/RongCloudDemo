package com.example.qinxue.lean;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationNotificationStatus;
import io.rong.imlib.model.Discussion;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.FileMessage;
import io.rong.message.TextMessage;

public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ConversationActivity";
    private TextView mName;
    private String mCurrentId;
    private String nCurrentName;
    private EditText mEditText;
    private Button mButton;
    private Conversation.ConversationType mConversationType;
    private ArrayList<String> mMemberList = new ArrayList<>();
    private static final int REMOVE_REQUEST = 0;
    private static final int ADD_REQUEST = 1;
    private android.app.Fragment mFragment;
    private View mFragmentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mName = getmTitle();
        getmOk().setText("more");
        String typeStr = getIntent().getData().getLastPathSegment().toUpperCase(Locale.US);
        mConversationType = Conversation.ConversationType.valueOf(typeStr);
        Log.i(TAG, "mConversationType.getValue() = " + mConversationType.getValue());
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
        mFragment = getFragmentManager().findFragmentById(R.id.conversation);
    }

    private ListView listView;


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
        menu.add(0, 4, 0, "清空当前会话");
        menu.add(0, 5, 0, "插入消息");
        menu.add(0, 6, 0, "发送自定义消息");
        menu.add(0, 7, 0, "获取历史记录");
        menu.add(0, 8, 0, "发送外信息");
        menu.add(0, 9, 0, "发送媒体消息");
        menu.add(0, 10, 0, "获取最近消息");
        menu.add(0, 11, 0, "改变群组消息免打扰");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "item.getItemId() = " + item.getItemId() + "mMemberList.size() = " + mMemberList.size());
//                if (mMemberList.size() > 0) {
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
                    case 4:
                        Log.i(TAG, "清空当前会话");
                        RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, mCurrentId, new RongIMClient.ResultCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                Log.i(TAG, "onSuccess()");
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Log.i(TAG, "onError");

                            }
                        });
                        break;
                    case 5:
                        Log.i(TAG, "插入消息");
                        Message.ReceivedStatus status = new Message.ReceivedStatus(1);
                        status.setRead();
                        final TextMessage message = TextMessage.obtain("oooooooo");
                        RongIM.getInstance().insertIncomingMessage(mConversationType, mCurrentId, mCurrentId, status, message, System.currentTimeMillis(), null);

                        break;
                    case 6:
                        Log.i(TAG, "发送自定义消息");
                        User user = new User("icon", "id", "name");
                        CustomMessage customMessage = new CustomMessage("自定义的消息", user);
                        io.rong.imlib.model.Message message1 = io.rong.imlib.model.Message.obtain(mCurrentId, mConversationType, customMessage);
                        RongIM.getInstance().sendMessage(message1, "自定义PushContent", "自定义pushdata", new IRongCallback.ISendMessageCallback() {

                            @Override
                            public void onAttached(Message message) {

                            }

                            @Override
                            public void onSuccess(Message message) {
                                Log.i(TAG, "onSuccess()");
                            }

                            @Override
                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                            }
                        });
                        break;
                    case 7:
                        RongIM.getInstance().getRemoteHistoryMessages(mConversationType, mCurrentId, 0, 20, new RongIMClient.ResultCallback<List<Message>>() {

                            @Override
                            public void onSuccess(List<Message> messages) {
                                Log.i(TAG, "onSuccess() messages = " + messages);
                                if (messages != null) {
                                    Log.i(TAG, "messages.size() = " + messages.size());

                                }
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Log.i(TAG, "onError() errorCode = " + errorCode);
                            }
                        });

                        break;
                    case 8:
                        TextMessage extraMessage = TextMessage.obtain("extraMessage");
                        extraMessage.setExtra("this is extra");
                        RongIM.getInstance().sendMessage(mConversationType, mCurrentId, extraMessage, null, null, new RongIMClient.SendMessageCallback() {
                            @Override
                            public void onSuccess(Integer integer) {
                                Log.i(TAG, "onSuccess()");
                            }

                            @Override
                            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                                Log.i(TAG, "onError()");
                            }
                        });
                        break;

                    case 9:

                        File file = new File("/storage/emulated/0/1/sina/VID_20171025_102846.mp4");
                        if (file.exists()) {
                            Log.i(TAG, "Uri fromFile = " + Uri.fromFile(file));

                        } else {
                            Toast.makeText(ConversationActivity.this, R.string.file_not_exist, Toast.LENGTH_SHORT).show();
                        }

                        VideoMessage videoMessage = VideoMessage.obtain(Uri.fromFile(file), 5000);
//                        FileMessage fileMessage = FileMessage.obtain(Uri.fromFile(file));
                        Message message3 = Message.obtain(mCurrentId, mConversationType, videoMessage);
                        RongIMClient.getInstance().sendMediaMessage(message3, null, null, new IRongCallback.ISendMediaMessageCallback() {
                                    @Override
                                    public void onProgress(Message message, int i) {
                                        Log.i(TAG, "onProgress() i = " + i);
                                    }

                                    @Override
                                    public void onCanceled(Message message) {
                                        Log.i(TAG, "onCanceled()");
                                    }

                                    @Override
                                    public void onAttached(Message message) {
                                        Log.i(TAG, "onAttached()");
                                    }

                                    @Override
                                    public void onSuccess(Message message) {
                                        Log.i(TAG, "onSuccess()");
                                    }

                                    @Override
                                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                        Log.i(TAG, "onError() errorCode = " + errorCode);
                                    }
                                }
                        );
//                        Log.i(TAG, "file.exists() = " + file.exists());
//                        VideoMessage message2 = VideoMessage.obtain()
                        break;
                    case 10:
                        RongIM.getInstance().getLatestMessages(mConversationType, mCurrentId, 10, new RongIMClient.ResultCallback<List<Message>>() {
                            @Override
                            public void onSuccess(List<Message> messages) {
                                Log.i(TAG, "messages.size() = " + messages.size());
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {

                            }
                        });
                        break;
                    case 11:
                        RongIM.getInstance().getConversationNotificationStatus(mConversationType, mCurrentId,
                                new RongIMClient.ResultCallback<ConversationNotificationStatus>() {
                                    @Override
                                    public void onSuccess(ConversationNotificationStatus conversationNotificationStatus) {
                                        if (conversationNotificationStatus == ConversationNotificationStatus.DO_NOT_DISTURB) {
                                            setNotifyDistube(ConversationNotificationStatus.NOTIFY);
                                        } else if (conversationNotificationStatus == ConversationNotificationStatus.NOTIFY) {
                                            setNotifyDistube(ConversationNotificationStatus.DO_NOT_DISTURB);
                                        }

                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {

                                    }
                                });
                        break;
                    default:
                        break;
                }
//                }

                return false;
            }
        });
    }

    private void setNotifyDistube(ConversationNotificationStatus status) {
        RongIM.getInstance().setConversationNotificationStatus(mConversationType, mCurrentId, status, new RongIMClient.ResultCallback<ConversationNotificationStatus>() {
            @Override
            public void onSuccess(ConversationNotificationStatus conversationNotificationStatus) {
                Toast.makeText(ConversationActivity.this, "current status:" + conversationNotificationStatus, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

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
        if (list != null && list.size() != 0) {
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
        switch (v.getId()) {
            case R.id.rename:
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
                break;
        }

    }
}