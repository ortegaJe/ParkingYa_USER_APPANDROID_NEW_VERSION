package com.example.jeffe.login;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_ID_REGISTER_USER = "IdNotificationReg";
    public static final String CHANNEL_NAME_REGISTER_USER = "IdNotificationName";
    public NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
            createChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {

        NotificationChannel registerUsersChannel = new NotificationChannel(CHANNEL_ID_REGISTER_USER,
                                                                           CHANNEL_NAME_REGISTER_USER,
                                                                           NotificationManager.IMPORTANCE_HIGH);
        registerUsersChannel.setDescription("channel for notify register users");
        registerUsersChannel.enableLights(true);
        registerUsersChannel.enableVibration(true);
        registerUsersChannel.setLightColor(Color.GREEN);
        registerUsersChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(registerUsersChannel);
    }

    public NotificationManager getManager(){
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getRegisterUserChannelNotify(String title, String body){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID_REGISTER_USER)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }
}
