package com.example.qinxue.lean;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Conversation;

/**
 * Created by qinxue on 2017/8/30.
 */

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;//将tab页面持久在内存中
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    private List<Fragment> mFragments = new ArrayList<>();
    private static final int SELECTED_GROUP = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        mConversationList = initConversationList();//获取融云会话列表的对象
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFragments.add(mConversationList);//加入会话列表
        mFragments.add(FriendFragment.getInstance());//加入通讯录
        mFragments.add(new MyInfoFragment());
        //配置ViewPager的适配器
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
        setTitle("会话列表");
        getmBack().setVisibility(View.GONE);
        getmOk().setText("more");

    }

    private Fragment initConversationList() {

        /**
         * appendQueryParameter对具体的会话列表做展示
         */
        if (mConversationFragment == null) {
            ConversationListFragment listFragment = new MyConversationListFragment();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationList")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置私聊会是否聚合显示
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        } else {
            return mConversationFragment;
        }
    }

    @Override
    void onBackClick(View view) {
        Log.i(TAG, "onBackClick()");
        finish();
    }

    @Override
    void onOKClick(View view) {
        Log.i(TAG, "onOKClick()");
        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu = popupMenu.getMenu();
        menu.add(0, 0, 0, "讨论组");
        menu.add(0, 1, 0, "添加好友");
        menu.add(0, 2, 0, "扫一扫");
        menu.add(0, 3, 0, "含有id为1,2,3的群聊");
        menu.add(0, 4, 0, "test");
        menu.add(0, 5, 0, "客服");
        menu.add(0, 6, 0, "创建聊天室");
        menu.add(0, 7, 0, "加入聊天室");
        menu.add(0, 8, 0, "disconnected");
        menu.add(0, 9, 0, "切换账户到2");
        menu.add(0, 10, 0, "测试Java代理");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "item.getItemId() = " + item.getItemId());
                switch (item.getItemId()) {
                    case 0:
                        Intent intent = new Intent(HomeActivity.this, SelectActivity.class);
                        intent.setAction("action_create");
                        startActivityForResult(intent, SELECTED_GROUP);
                        break;
                    case 1:

                        break;
                    case 2:
                        break;

                    case 3:
                        RongIM.getInstance().startGroupChat(HomeActivity.this, "group1", "群聊1");
//                        Conversation.ConversationType.GROUP
                        break;
                    case 4:
                        Log.i(TAG, "test onClick() ");
                        break;
                    case 5:
                        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                        CSCustomServiceInfo csInfo = csBuilder.nickName("客服").build();
                        RongIM.getInstance().startCustomerServiceChat(HomeActivity.this, "KEFU150537350421542", "在线客服", csInfo);
                        break;
                    case 6:
                        RongIM.getInstance().startChatRoomChat(HomeActivity.this, "chatRoomId1", true);
                        break;
                    case 7:
                        RongIM.getInstance().joinChatRoom("chatRoomId1", 0, new RongIMClient.OperationCallback() {
                            @Override
                            public void onSuccess() {
                                Log.i(TAG, "onSuccess()");
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Log.i(TAG, "onError() errorCode = " + errorCode);
                            }
                        });
                        break;
                    case 8:
                        Log.i(TAG, "disconnect()");
                        RongIM.getInstance().disconnect();
                        break;
                    case 9:
                        RongIM.getInstance().logout();
                        RongIM.connect(MainActivity.TOKENS[1], new RongIMClient.ConnectCallback() {


                            @Override
                            public void onSuccess(String userId) {
                                recreate();
                                Toast.makeText(getApplicationContext(), "connect server success", Toast.LENGTH_SHORT).show();
                                MainActivity.CURRENT_USER = userId;
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
                        break;
                    case 10:
                        testProxy();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    private void testProxy() {
        ProxyTest.RealSubject real = new ProxyTest.RealSubject(); //
        //参数 （类加载器，type，invokhandler接口)
        ProxyTest.Subject proxySubject = (ProxyTest.Subject) Proxy.newProxyInstance(ProxyTest.Subject.class.getClassLoader(),
                new Class[]{ProxyTest.Subject.class},
                new ProxyTest.ProxyHandler(real));

        proxySubject.doSomething();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult() requestCode = " + requestCode + "resultCode = " + resultCode);
        switch (requestCode) {
            case SELECTED_GROUP:
                if (resultCode == RESULT_OK) {
                    List<String> list = data.getStringArrayListExtra("selected_list");
                    Log.i(TAG, "list.size() = " + list.size());
                    RongIM.getInstance().createDiscussionChat(this, list, "讨论组", new RongIMClient.CreateDiscussionCallback() {

                        @Override
                        public void onSuccess(String s) {
                            Log.i(TAG, "onActivityResult()");

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.i(TAG, "onError()");
                        }
                    });
                }
                break;
            case 1:

                break;
        }
    }
}
