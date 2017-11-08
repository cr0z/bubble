package com.crozsama.bubble.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SignInResponse {
    public int code;
    public Data data;

    public class Data {
        public String token;
        public Profile profile;
    }

    public static class Profile implements Parcelable {
        public String username;
        public String name;
        public String bio;
        public int gender;
        public String birthdate;
        public String contacts;
        public int followed_count;
        public int follower_count;

        protected Profile(Parcel in) {
            username = in.readString();
            name = in.readString();
            bio = in.readString();
            gender = in.readInt();
            birthdate = in.readString();
            contacts = in.readString();
            followed_count = in.readInt();
            follower_count = in.readInt();
        }

        public static final Creator<Profile> CREATOR = new Creator<Profile>() {
            @Override
            public Profile createFromParcel(Parcel in) {
                return new Profile(in);
            }

            @Override
            public Profile[] newArray(int size) {
                return new Profile[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(username);
            dest.writeString(name);
            dest.writeString(bio);
            dest.writeInt(gender);
            dest.writeString(birthdate);
            dest.writeString(contacts);
            dest.writeInt(followed_count);
            dest.writeInt(follower_count);
        }

        public String toString() {
            return new GsonBuilder().create().toJson(this);
        }
    }
}
