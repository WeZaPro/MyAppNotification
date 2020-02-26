package com.example.myappnotification.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myappnotification.MyModel.MyTrackNotify;
import com.example.myappnotification.MyModel.MyUserLogin;
import com.example.myappnotification.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowNotiActivity extends AppCompatActivity {

    TextView tvGetValueFormNotifyTitle;
    TextView tvGetValueFormNotifyBody;
    TextView tvGetValueFormNotifyUID;
    ImageView mvGetValueFormNotifyImage;
    TextView tvGetValueFormNotifyToken;

    String email,uid,token;

    FusedLocationProviderClient client;
    double LAT, LON;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_noti);
        tvGetValueFormNotifyTitle = findViewById(R.id.tvGetValueFormNotifyTitle);
        tvGetValueFormNotifyBody = findViewById(R.id.tvGetValueFormNotifyBody);
        tvGetValueFormNotifyUID = findViewById(R.id.tvGetValueFormNotifyUID);
        tvGetValueFormNotifyToken = findViewById(R.id.tvGetValueFormNotifyToken);
        mvGetValueFormNotifyImage = findViewById(R.id.mvGetValueFormNotifyImage);

        String title = getIntent().getStringExtra("title");
        email = getIntent().getStringExtra("body");
        uid = getIntent().getStringExtra("uid");
        token = getIntent().getStringExtra("token");
        String image = getIntent().getStringExtra("image");

        tvGetValueFormNotifyTitle.setText("TITLE "+title);
        tvGetValueFormNotifyBody.setText("BODY "+email);
        tvGetValueFormNotifyUID.setText("UID "+uid);
        tvGetValueFormNotifyToken.setText("TOKEN "+token);

        Glide.with(getApplicationContext())
                .load(image)
                .into(mvGetValueFormNotifyImage);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(),"onstart",Toast.LENGTH_SHORT).show();
        getLocationAddress();
    }

    private void getLocationAddress() {

        client = LocationServices.getFusedLocationProviderClient(ShowNotiActivity.this);
        client.getLastLocation().addOnSuccessListener(ShowNotiActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {

                    LAT = location.getLatitude();
                    LON = location.getLongitude();
                    Log.d("check","Add LONG "+location.getLongitude());

                    Toast.makeText(ShowNotiActivity.this,"Long "+location.getLongitude(),Toast.LENGTH_LONG).show();

                    //trackLocationNotify();
                    writeDataTofirebase();
                }
            }

        });
    }

    private void writeDataTofirebase() {
        MyTrackNotify myTrackNotify = new MyTrackNotify(uid,email,token,LAT,LON);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trackNotify");
        myRef.child(uid).setValue(myTrackNotify);

    }


}
