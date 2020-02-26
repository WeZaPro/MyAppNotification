package com.example.myappnotification.MyModel;


import android.os.Parcel;
import android.os.Parcelable;

public class MyUserRegister implements Parcelable {

    String uid;
    String name;
    String email;
    String password;
    double Lat;
    double Long;
    String token;

    public MyUserRegister() {
    }

    public MyUserRegister(String uid, String name, String email, String password, double lat, double aLong,String token) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        Lat = lat;
        Long = aLong;
        this.token = token;
    }


    protected MyUserRegister(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        Lat = in.readDouble();
        Long = in.readDouble();
        token = in.readString();
    }

    public static final Creator<MyUserRegister> CREATOR = new Creator<MyUserRegister>() {
        @Override
        public MyUserRegister createFromParcel(Parcel in) {
            return new MyUserRegister(in);
        }

        @Override
        public MyUserRegister[] newArray(int size) {
            return new MyUserRegister[size];
        }
    };

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        dest.writeString(password);
        dest.writeDouble(Lat);
        dest.writeDouble(Long);
        dest.writeString(token);
    }
}
