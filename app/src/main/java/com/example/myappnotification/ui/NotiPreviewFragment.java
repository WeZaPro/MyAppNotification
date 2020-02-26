package com.example.myappnotification.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myappnotification.MyModel.MyUserLogin;
import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.MySingleTon.MySingleton;
import com.example.myappnotification.MyViewModel.MyViewModel;
import com.example.myappnotification.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotiPreviewFragment extends Fragment {

    MyViewModel myViewModel;
    TextView tvShowToken, tvShowEmail, tvShowUser,tvShowUID;
    Button btnSendNotify;

    String NOTIFICATION_TITLE;
    String NOTIFICATION_BODY;
    String NOTIFICATION_IMAGE = "https://tinyjpg.com/images/social/website.jpg";
    String TOPIC;
    final String TAG = "NOTIFICATION TAG";

    final private String serverKey = "key=" + "AAAAeYzZat4:APA91bEAaNeZHKa8nfSAdo8bQA4VOyWEJmYCCcGIPuLyix1p3MeHw0vMDPyhhcGPFEv-GXtS3gTeas8iTBQG-hi-MK3KiAuG1Dp6u7xPghFBmbTvPQIsBxRzGxw03-EY2-FSrBXNxkc5";
    final private String contentType = "application/json";
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";

    //String tempToken = "cAN6xAbjbxI:APA91bHDv3Pk_sBlM-mw2YOA-Pb3c-FE-2AKBnsdRGahqYV5iGmn7lKQPDe8W51e3w20r9vIHO443yNCwloNtC-ilFJk7acshKHxDk102KPdqSUmjCkEkHIm997fO29ePyU_gguR4_4m";
    String tokened;
    String user,email,UID;

    MyUserLogin myUserLogin;

    public NotiPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_noti_preview, container, false);

        tvShowToken = v.findViewById(R.id.tvShowToken);
        tvShowEmail = v.findViewById(R.id.tvShowEmail);
        tvShowUser = v.findViewById(R.id.tvShowUser);
        tvShowUID = v.findViewById(R.id.tvShowUID);
        btnSendNotify = v.findViewById(R.id.btnSendNotify);

        myUserLogin = new MyUserLogin();
        if (getArguments() != null) {
            myUserLogin = getArguments().getParcelable("key");

            Toast.makeText(getActivity(),"TOKEN IS "+myUserLogin.getToken(),Toast.LENGTH_SHORT).show();

            tvShowToken.setText(myUserLogin.getToken());
            tvShowEmail.setText(myUserLogin.getEmail());
            tvShowUser.setText(myUserLogin.getName());
            tvShowUID.setText(myUserLogin.getUid());

            user = myUserLogin.getName();
            email = myUserLogin.getEmail();
            UID = myUserLogin.getUid();

            tokened = myUserLogin.getToken();
            Log.d("check", "getToken " + tokened);

        }

        btnSendNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotify(v);

            }
        });
       return v;
    }


    private void sendNotify(View v) {
        /*TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = tvShowUser.toString();
        NOTIFICATION_BODY = tvShowEmail.toString();*/

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();

        try {

            notifcationBody.put("title", user);
            notifcationBody.put("body", email);
            notifcationBody.put("uid", UID);
            notifcationBody.put("token", tokened);
            notifcationBody.put("image", NOTIFICATION_IMAGE);

            notification.put("to", tokened.toString()); // send to Token
            //notification.put("to",tempToken);
            notification.put("data", notifcationBody);

        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        sendNotification(notification);
    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Request error", Toast.LENGTH_LONG).show();
                Log.i(TAG, "onErrorResponse: Didn't work");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

}
