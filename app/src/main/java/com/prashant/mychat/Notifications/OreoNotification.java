package com.prashant.mychat.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;

public class OreoNotification extends ContextWrapper {

    private static final String CHANNEL_ID = "com.prashant.mychat";
    private static final String CHANNEL_NAME = "mychat";
    private static final Uri DEFAULT_SOUND = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    private NotificationManager notificationManager;

    public OreoNotification(Context base) {
        super(base);
        createChannel();
    }

    private void createChannel() {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        //CONTENT_TYPE_SONIFICATION indicates that this audio is meant for sounds associated with notifications
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        channel.setSound(DEFAULT_SOUND, audioAttributes);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager(){
        if (notificationManager == null){
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return  notificationManager;
    }

    public  Notification.Builder getOreoNotification(String title, String body,
                                                     PendingIntent pendingIntent, String icon){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(Integer.parseInt(icon))
                .setAutoCancel(true);
    }
}
