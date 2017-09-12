package com.example.qinxue.lean;

/**
 * Created by qinxue on 2017/8/30.
 */

public class Friend {
    private String userId;
    private String name;
    private String portraitUri;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public  Friend(){

    }
    public Friend(String userId, String name, String portraitUri) {
        this.userId = userId;
        this.name = name;
        this.portraitUri = portraitUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    @Override
    public String toString() {
        return "userId = "+userId+" name = "+name+" portraitUri = "+portraitUri;
    }
}
