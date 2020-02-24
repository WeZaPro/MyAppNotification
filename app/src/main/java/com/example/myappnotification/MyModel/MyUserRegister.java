package com.example.myappnotification.MyModel;


public class MyUserRegister {

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
}
