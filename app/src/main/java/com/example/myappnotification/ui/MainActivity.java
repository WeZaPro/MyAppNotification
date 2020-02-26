package com.example.myappnotification.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.myappnotification.MyInterface.MyListener;
import com.example.myappnotification.MyModel.MyUserLogin;
import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements MyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LoginFragment loginFragment = new LoginFragment();

        if(savedInstanceState == null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentContainer,loginFragment)
                    .commit();

        }
    }

    @Override
    public void myCallBack(MyUserLogin myUserLogin) {

        NotiPreviewFragment notiPreviewFragment = new NotiPreviewFragment();
                    Bundle b = new Bundle();
                    b.putParcelable("key",myUserLogin);
                    notiPreviewFragment.setArguments(b);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentContainer,notiPreviewFragment)
                            .addToBackStack("")
                            .commit();

    }

}
