package com.example.myappnotification.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    String token;
    View v;

    FusedLocationProviderClient client;
    double LAT, LON;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        // test
        requestPermissions();
        mFullName   = v.findViewById(R.id.fullNameRegist);
        mEmail      = v.findViewById(R.id.EmailRegis);
        mPassword   = v.findViewById(R.id.passwordRegis);
        mPhone      = v.findViewById(R.id.phoneRegis);
        mRegisterBtn= v.findViewById(R.id.registerBtn);
        mLoginBtn   = v.findViewById(R.id.createTextRegis);
        progressBar = v.findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            /*startActivity(new Intent(getActivity(),MainActivity.class));//********************
            getActivity().finish();*/

            // go to RecyclerView Fragment
            RVFragment rvFragment = new RVFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer,rvFragment)
                    .addToBackStack("")
                    .commit();

        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone    = mPhone.getText().toString();

                getLocationAddress();

                // insert Token
                SharedPreferences sharedPref = getActivity().getSharedPreferences(Constance.MY_PREFS, Context.MODE_PRIVATE);
                token = sharedPref.getString(Constance.TOKEN, "");


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            /*startActivity(new Intent(getActivity(),MainActivity.class));//**********************/

                            // go to RecyclerView Fragment
                            RVFragment rvFragment = new RVFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.contentContainer,rvFragment)
                                    .addToBackStack("")
                                    .commit();

                            // Write a message to the database **********
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRegisRef = database.getReference("users");
                            // test***********
                            DatabaseReference myLoginRef = database.getReference("login");

                            MyUserRegister models = new MyUserRegister(userID,fullName,email,password,LAT,LON,token);
                            //test*******
                            MyUserLogin myUserLogin = new MyUserLogin(userID,fullName,email,LAT,LON,token);
                            Log.d("check","TOKEN INIT "+token);

                            myRegisRef.child(userID).setValue(models);
                            //test******
                            myLoginRef.child(userID).setValue(myUserLogin);

                            Toast.makeText(getActivity(), "User Created."+fullName, Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),LoginFragment.class));
            }
        });

        return v;
    }



    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    private void getLocationAddress() {

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {

                    // ส่งค่าข้อมูล ไปที่ MapActivity
                    /*Intent i = new Intent(MainActivity.this, MapActivity.class);
                    i.putExtra("LON", location.getLatitude());
                    i.putExtra("LAT", location.getLongitude());
                    startActivity(i);*/

                    LAT = location.getLatitude();
                    LON = location.getLongitude();
                    Log.d("check","Add LONG "+location.getLongitude());

                    Toast.makeText(getActivity(),"Long "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                }

                // getAddress + writeDataToFirebase
                //addAddress(location);
            }

        });
    }

}
