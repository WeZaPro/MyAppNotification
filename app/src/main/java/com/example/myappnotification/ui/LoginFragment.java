package com.example.myappnotification.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappnotification.MyConstance.Constance;
import com.example.myappnotification.MyModel.MyUserLogin;
import com.example.myappnotification.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private static final int PERMISSION_ID = 44;
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    FusedLocationProviderClient client;
    double LAT, LON;
    String token;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false);

        //findView(v);
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ;

        mEmail = v.findViewById(R.id.EmailLogin);
        mPassword = v.findViewById(R.id.passwordLogin);
        progressBar = v.findViewById(R.id.progressBar);
        mLoginBtn = v.findViewById(R.id.loginBtn);
        mCreateBtn = v.findViewById(R.id.createTextinLogin);


        fAuth = FirebaseAuth.getInstance();
        client = LocationServices.getFusedLocationProviderClient(getActivity());


        //TODO  ทำ Save SharePreference User Login *************************
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Click " , Toast.LENGTH_SHORT).show();
                getLatLon();

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                // insert Token
                SharedPreferences sharedPref = getActivity().getSharedPreferences(Constance.MY_PREFS, Context.MODE_PRIVATE);
                token = sharedPref.getString(Constance.TOKEN, "");


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Track Location
                /*client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            LAT = location.getLatitude();
                            LON = location.getLongitude();
                            Log.d("check", "Add LONG " + location.getLongitude());

                            *//*Toast.makeText(getActivity(), "Long " + location.getLongitude(), Toast.LENGTH_SHORT).show();*//*
                        }

                    }

                });*/




              /*mfusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            Lat = location.getLatitude();
                            Lon = location.getLongitude();
                            Log.d("LAT","LAT mFush: "+location.getLatitude());

                            Toast.makeText(getActivity(),"Long "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/


                // authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                            // Write a message to the database *********
                            final String Uid = task.getResult().getUser().getUid();
                            final String name = task.getResult().getUser().getDisplayName();
                            final String email = task.getResult().getUser().getEmail();

                            MyUserLogin UserLogin = new MyUserLogin(Uid, name, email, LAT, LON, token);
                            Log.d("LAT", "LAT INIT: " + LAT);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("login");
                            myRef.child(Uid).setValue(UserLogin);

                            //startActivity(new Intent(getActivity(),MainActivity.class));//************

                            // go to RecyclerView Fragment
                            RVFragment rvFragment = new RVFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, rvFragment).addToBackStack("").commit();


                        } else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click ", Toast.LENGTH_SHORT).show();
                // go to RecyclerView Fragment
                RegisterFragment registerFragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, registerFragment).addToBackStack("").commit();
            }
        });

        return v;
    }

    private void getLatLon() {

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {

                    LAT = location.getLatitude();
                    LON = location.getLongitude();
                    Log.d("check", "Add LONG " + location.getLongitude());

                    Toast.makeText(getActivity(), "Long " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "ERROR " , Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

}
