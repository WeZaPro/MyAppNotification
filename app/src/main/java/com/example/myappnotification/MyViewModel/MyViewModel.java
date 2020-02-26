package com.example.myappnotification.MyViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myappnotification.MyModel.MyUserRegister;

import java.util.List;

public class MyViewModel extends ViewModel {

    private final MutableLiveData<MyUserRegister> tokened = new MutableLiveData<MyUserRegister>();

    public void select(MyUserRegister item) {
        tokened.setValue(item);
    }

    public LiveData<MyUserRegister> getTokened() {
        return tokened;
    }
}

/*public class MyViewModel extends ViewModel{

    MutableLiveData<List<MyUserRegister>> token;

    public LiveData<List<MyUserRegister>> getToken(){
        if(token == null){
            token = new MutableLiveData<List<MyUserRegister>>();
            loadToken();
        }
        return token;
    }

    private void loadToken() {


    }
}*/
