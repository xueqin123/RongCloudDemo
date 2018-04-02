package com.example.qinxue.lean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Created by qinxue on 2017/8/30.
 */

public class MainActivity extends Activity implements View.OnClickListener, RongIM.UserInfoProvider, RongIM.GroupInfoProvider {
    public static List<Friend> userIdList;
    public static List<GroupUserInfo> nickNameList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private Button mLogin;
    private EditText mEditText;
    private TextView textView;
    //1 user1
    public static final String USER_TOKEN_1 = "xKVD3KOPl2fABJ7TmXzJJK5VbzBm9J5AteeZN+MbeIsewrb07hVFMnBkcc5GvzDgTFPVWauINNqV8GB7+3x38w==";
    //2 user2
    public static final String USER_TOKEN_2 = "VODXKtEAceT2Zn6lilLuo65VbzBm9J5AteeZN+MbeIsewrb07hVFMkjVBuFqG/ND42qoZ2NywOaV8GB7+3x38w==";
    //3 user3
    public static final String USER_TOKEN_3 = "REPjQ0ylm2vqW0BVWjYwYa5VbzBm9J5AteeZN+MbeIsewrb07hVFMkwmYn0C7cPfNYCWYNhBJJiV8GB7+3x38w==";
    //4 user4
    public static final String USER_TOKEN_4 = "dA8lwpMBVqkZm6JWYILXhq5VbzBm9J5AteeZN+MbeIsewrb07hVFMilkD8X2JnFfz0dfzWdylYaV8GB7+3x38w==";
    //5 user5
    public static final String USER_TOKEN_5 = "LoXZ8ByFhuIpZA1Ue18Guq5VbzBm9J5AteeZN+MbeIsewrb07hVFMjeIKW6WxRpHBzTx66zSZ+2V8GB7+3x38w==";

    public static final String TEST_200017 = "P/cMWUESE3m1ITlUTjTAJOvQf5Tgu6Xhw1Wyn2au7557UsdnmitctfaAehdvkc6xg5Jx441Pd+hjTLIOPrt/Uw==";
    public static final String TEST_200016 = "IlA8vjagiQ6I2MbkTVVtsOvQf5Tgu6Xhw1Wyn2au7557UsdnmitctZaMubntVMiOAJUiJP0VDpbWvbKnAihq0A==";
    //请不不要使用以下账号，
    //100 user100

    //101 user101
    public static final String[] TOKENS = {
            USER_TOKEN_1, USER_TOKEN_2, USER_TOKEN_3, USER_TOKEN_4, USER_TOKEN_5
    };
    public static String CURRENT_USER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.rc_edit_text);
        textView = (TextView) findViewById(R.id.test);
        initUserInfo();
    }

    @Override
    public void onClick(View v) {

        Integer token = Integer.valueOf(mEditText.getText().toString());
        switch (LeanApplication.APP_KEY) {
            case "x18ywvqfxn3nc":

                if (token == 200016) {
                    connectRongServer(TEST_200016);
                } else if (token == 200017) {
                    connectRongServer(TEST_200017);
                }
                break;
            case "sfci50a7s4q5i":

                connectRongServer(TOKENS[token - 1]);
                break;
        }


    }


    private void connectRongServer(String token) {
        Log.i(TAG, "appKey = " + LeanApplication.APP_KEY);
        Log.i(TAG, "connectRongServer() = " + token);
        RongIM.getInstance().setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
            @Override
            public GroupUserInfo getGroupUserInfo(String groupId, String userId) {

                if ("group1".equals(groupId)) {
                    for (GroupUserInfo info : nickNameList) {
                        if (info.getUserId().equals(userId)) {
                            return info;
                        }
                    }
                }
                return null;
            }
        }, true);


//        RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
//            @Override
//            public void getGroupMembers(String groupId, RongIM.IGroupMemberCallback callback) {
//                List<UserInfo> list = new ArrayList<UserInfo>();
//                if (groupId.equals("group1")) {
//                    for (int i = 0; i < 3; i++) {
//                        Friend friend = MainActivity.userIdList.get(i);
//                        list.add(new UserInfo(friend.getUserId(), friend.getName(), Uri.parse(friend.getPortraitUri())));
//                    }
//                }
//                Log.i(TAG,"list.size() = "+list.size());
//                for(UserInfo info:list){
//                    Log.i(TAG,"info.getName() = "+info.getName());
//                }
//                callback.onGetGroupMembersResult(list);
//            }
//        });
        RongIM.connect(token, new RongIMClient.ConnectCallback() {


            @Override
            public void onSuccess(String userId) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                Toast.makeText(MainActivity.this, "connect server success", Toast.LENGTH_SHORT).show();
                CURRENT_USER = userId;
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "connect failure errorCode is : " + errorCode.getValue());
            }


            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "token is error ,please check token and appkey");
            }
        });


        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
            @Override
            public Message onSend(Message message) {
                Log.i(TAG,"onSend type = "+message.getObjectName());
                return message;
            }

            @Override
            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
                return false;
            }
        });


        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                long receiveTime = message.getReceivedTime();
                long sendTime = message.getSentTime();
                long intravl = receiveTime - sendTime;
                Log.i(TAG, "receiveTime = " + receiveTime + " sendTime = " + sendTime + " intravl = " + intravl);
                Log.i(TAG, "onReceived()");
                Log.i(TAG, "onReceived() message.getContent().getClass().getSimpleName() =" + message.getContent().getClass().getSimpleName());
                MessageTag tag = CustomMessage.class.getAnnotation(MessageTag.class);
                MessageTag tag1 = TextMessage.class.getAnnotation(MessageTag.class);
                Log.i(TAG, "tag.value() = " + tag.value());
                Log.i(TAG, "message.getObjectName() = " + message.getObjectName());
                if (message.getObjectName().equals(tag.value())) {
                    Log.i(TAG, "CustomMessage receive");
                    final CustomMessage msg = (CustomMessage) message.getContent();
                    Log.i(TAG, "msg = " + msg.getString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this.getApplicationContext(), msg.getString(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else if (message.getObjectName().equals(tag1.value())) {
                    Log.i(TAG, "Textmessaget receive");

                    TextMessage message1 = (TextMessage) message.getContent();
                    Log.i(TAG, "message1.getExtra()) = " + message1.getExtra());
                }


                //刷新缓存
//                RongIM.getInstance().refreshUserInfoCache(getUserInfo(message.getUId()));

                //刷新讨论组缓存
//                RongIM.getInstance().refreshDiscussionCache();
//                刷新群组
//                RongIM.getInstance().refreshGroupInfoCache();
//                刷新群组成员
//                     RongIM.getInstance().refreshGroupUserInfoCache();
                return false;
            }
        });

        //设置消息点击监听
        RongIM.getInstance().setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {


            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Log.i(TAG, "onUserPortraitClick()");
                return false;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Log.i(TAG, "onUserPortraitLongClick()");
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                Log.i(TAG, "onMessageClick()");
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                Log.i(TAG, "onMessageLinkClick()");
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                Log.i(TAG, "onMessageLongClick()");
                return false;
            }
        });

        //发送消息监听
        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
            @Override
            public Message onSend(Message message) {
                Log.i(TAG, "onSend()");
//                RongIM.getInstance().refreshUserInfoCache(getUserInfo(message.getSenderUserId()));
                return message;
            }

            @Override
            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
                Log.i(TAG, " onSent() = " + message.getSentStatus());
//                message.getSentStatus()
                return false;
            }
        });
//
//        RongIM.getInstance().enableUnreadMessageIcon();
//        RongIM.getInstance().addToBlacklist();
        //接收消息监听
//        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//            @Override
//            public boolean onReceived(Message message, int i) {
//                Log.i(TAG, "onReceived()");
//                //刷新缓存
//                RongIM.getInstance().refreshUserInfoCache(getUserInfo(message.getUId()));
//
//                //刷新讨论组缓存
////                RongIM.getInstance().refreshDiscussionCache();
////                刷新群组
////                RongIM.getInstance().refreshGroupInfoCache();
////                刷新群组成员
////                     RongIM.getInstance().refreshGroupUserInfoCache();
//                return false;
//            }
//        });
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                Log.i(TAG, "onChanged () " + connectionStatus);
            }
        });

        //公众号
        RongIM.setPublicServiceBehaviorListener(new RongIM.PublicServiceBehaviorListener() {

            @Override
            public boolean onFollowClick(Context context, PublicServiceProfile publicServiceProfile) {
                return false;
            }

            @Override
            public boolean onUnFollowClick(Context context, PublicServiceProfile publicServiceProfile) {
                return false;
            }

            @Override
            public boolean onEnterConversationClick(Context context, PublicServiceProfile publicServiceProfile) {
                return false;
            }
        });
        RongIM.getInstance().setGroupInfoProvider(this, true);


    }

    private void initUserInfo() {
        userIdList = new ArrayList<Friend>();
        userIdList.add(new Friend("1", "user1", "http://img4.duitang.com/uploads/item/201208/25/20120825125320_eBMxB.jpeg"));
        userIdList.add(new Friend("2", "user2", "http://pic41.nipic.com/20140515/13163490_114514725169_2.jpg"));
        userIdList.add(new Friend("3", "user3", "http://dynamic-image.yesky.com/600x-/uploadImages/upload/20141120/ai4gjet4l4tjpg.jpg"));
        userIdList.add(new Friend("4", "user4", "http://img1.imgtn.bdimg.com/it/u=1891993688,2072816047&fm=214&gp=0.jpg"));
        userIdList.add(new Friend("5", "user5", "http://img.duoziwang.com/2016/11/27/133942164161.jpg"));
        userIdList.add(new Friend("200016", "user200016", "http://img.duoziwang.com/2016/11/27/133942164161.jpg"));
        userIdList.add(new Friend("200017", "user200017", "http://img.duoziwang.com/2016/11/27/133942164161.jpg"));
        RongIM.setUserInfoProvider(this, true);
        for (Friend i : userIdList) {
            nickNameList.add(new GroupUserInfo("group1", i.getUserId(), i.getName()));
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userIdList) {
            if (i.getUserId().equals(s)) {
                Log.e(TAG, i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getName(), Uri.parse(i.getPortraitUri()));
            }
        }
        Log.e(TAG, "UserId is : " + s);
        return null;
    }


    @Override
    public Group getGroupInfo(String s) {
        Log.i(TAG, "s = " + s);
        Group group = null;
        switch (s) {
            case "group1":
                group = new Group("group1", "群聊1", Uri.parse("http://img1.imgtn.bdimg.com/it/u=1891993688,2072816047&fm=214&gp=0.jpg"));
                break;
        }

        return group;
    }
}
