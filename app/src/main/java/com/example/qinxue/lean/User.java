package com.example.qinxue.lean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qinxue on 2018/4/3.
 */

public class User implements Parcelable {
    private String icon;
    private String id;
    private String name;

    public User(String icon, String id, String name) {
        this.icon = icon;
        this.id = id;
        this.name = name;
    }

    private User(Parcel source) {
        icon = source.readString();
        id = source.readString();
        name = source.readString();
    }

    public User(JSONObject userObject) {
        icon = userObject.optString("icon");
        id = userObject.optString("id");
        name = userObject.optString("name");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            User user = new User(source);
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public JSONObject getJsonObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("icon", icon);
            jsonObj.put("id", id);
            jsonObj.put("name", name);
        } catch (JSONException e) {

        }
        return jsonObj;
    }

    @Override
    public String toString() {
        return "{ icon = " + icon + " id = " + id + " name = " + name + " }";
    }
}
