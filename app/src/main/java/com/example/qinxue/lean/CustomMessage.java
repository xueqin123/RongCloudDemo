package com.example.qinxue.lean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by qinxue on 2017/10/11.
 */
@MessageTag(value = "app:custom", flag = MessageTag.ISCOUNTED)
public class CustomMessage extends MessageContent {
    private static final String TAG = "CustomMessage";
    private String content = "";
    private User user;

    public CustomMessage(String content, User user) {
        Log.i(TAG, "CustomMessage() user = " + user);
        this.content = content;
        this.user = user;
    }


    public CustomMessage(Parcel in) {
        this.content = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public String getString() {
        return content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(User user) {
        return user;
    }

    public CustomMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                content = jsonObj.optString("content");
            JSONObject jsonObjUser = (JSONObject) jsonObj.get("user");
            user = new User(jsonObjUser);
        } catch (JSONException e) {
            Log.i(TAG, "e = " + e.getMessage());
        }
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", content);
            Log.i(TAG, "encode() user = " + user);
            jsonObj.put("user", user.getJsonObject());
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeParcelable(user, flags);
    }

    public static final Parcelable.Creator<CustomMessage> CREATOR = new Creator<CustomMessage>() {
        @Override
        public CustomMessage createFromParcel(Parcel source) {
            CustomMessage customMessage = new CustomMessage(source);
            return customMessage;
        }

        @Override
        public CustomMessage[] newArray(int size) {
            return new CustomMessage[size];
        }
    };


    @Override
    public String toString() {
        return "content: " + content + " user: " + user.toString();
    }
}
