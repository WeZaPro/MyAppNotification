package com.example.myappnotification.MsgService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.myappnotification.MyConstance.Constance;
import com.example.myappnotification.R;
import com.example.myappnotification.ui.ShowNotiActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static String TAG = "token";
    private final String ADMIN_CHANNEL_ID = "admin_channel";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID);

        // Link ไป Web
        /*final Intent intent = new Intent(this, MainActivity.class);*/

        // Link ไป ShowNotiActivity + ส่งข้อมูลใน Notification
        final Intent intent = new Intent(this, ShowNotiActivity.class);
        intent.putExtra("title", remoteMessage.getData().get("title"));
        intent.putExtra("body", remoteMessage.getData().get("body"));
        intent.putExtra("uid", remoteMessage.getData().get("uid"));
        intent.putExtra("image", remoteMessage.getData().get("image"));
        intent.putExtra("token", remoteMessage.getData().get("token"));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);


        // Link ไป Web
        //Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sirivatana.co.th/"));

        /*PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        String imageUri = remoteMessage.getData().get("image");
        Bitmap bitmap = getBitmapfromUrl(imageUri);

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(bitmap).setContentTitle(remoteMessage.getData()
                .get("title")).setContentTitle(remoteMessage.getData()
                .get("uid")).setContentText(remoteMessage.getData()
                .get("token")).setContentText(remoteMessage.getData()
                .get("body")).setStyle(new NotificationCompat
                .BigPictureStyle().bigPicture(bitmap))
                .setAutoCancel(true)
                //.setSound(notificationSoundUri)
                .setContentIntent(pendingIntent);

        Log.d("check", "pendingIntent "+pendingIntent.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        // Create Notification
        notificationManager.notify(notificationID, notificationBuilder.build());

        Log.d("checkin","APP CHECK IN");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager) {
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to devicee notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    private Bitmap getBitmapfromUrl(String imageUri) {

        try {
            URL url = new URL(imageUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        SharedPreferences sharedPref = getSharedPreferences(Constance.MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constance.TOKEN, token);
        editor.commit();

        Log.d("token", "send token: " + token);

    }

}
