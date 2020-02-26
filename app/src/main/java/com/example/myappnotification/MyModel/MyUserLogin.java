package com.example.myappnotification.MyModel;

import android.os.Parcel;
import android.os.Parcelable;

public class MyUserLogin implements Parcelable {

    String uid;
    String name;
    String email;
    double Lat;
    double Long;

    String token;

    public MyUserLogin() {
    }

    public MyUserLogin(String uid, String name, String email, double lat, double aLong,String token) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.token = token;
        Lat = lat;
        Long = aLong;
    }

    protected MyUserLogin(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        Lat = in.readDouble();
        Long = in.readDouble();
        token = in.readString();
    }

    public static final Creator<MyUserLogin> CREATOR = new Creator<MyUserLogin>() {
        @Override
        public MyUserLogin createFromParcel(Parcel in) {
            return new MyUserLogin(in);
        }

        @Override
        public MyUserLogin[] newArray(int size) {
            return new MyUserLogin[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeDouble(Lat);
        dest.writeDouble(Long);
        dest.writeString(token);
    }
}
