package com.example.myappnotification.MyModel;

public class MyTrackNotify {
    //MyUserLogin UserLogin = new MyUserLogin(Uid, name, email, LAT, LON, token);

    String uid;
    String email;
    String token;
    double Lat;
    double Lon;

    public MyTrackNotify(String uid, String email, String token, double lat, double lon) {
        this.uid = uid;
        this.email = email;
        this.token = token;
        Lat = lat;
        Lon = lon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }
}
