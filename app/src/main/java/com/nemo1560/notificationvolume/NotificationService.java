package com.nemo1560.notificationvolume;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationService extends Service {

    private static final int NOTIFICATION_ID = 1000;
    private static final String CHANNEL_ID = "VolumeService";
    private static final CharSequence CHANNEL_NAME = "Volume Channel";
    private static RemoteViews contentView;
    private static Notification notification;
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder mBuilder;
    private static NotificationChannel channel;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setNotification();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void setNotification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);

            Intent downIntent = new Intent(this, NotificationReceiver.class);
            downIntent.putExtra("DownVolume",0);
            PendingIntent pendingDownIntent;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                pendingDownIntent = PendingIntent.getBroadcast(this, 1020, downIntent, PendingIntent.FLAG_IMMUTABLE);
            }else{
                pendingDownIntent = PendingIntent.getBroadcast(this, 1020, downIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            contentView.setOnClickPendingIntent(R.id.btn_down, pendingDownIntent);

            Intent upIntent = new Intent(this, NotificationReceiver.class);
            upIntent.putExtra("UpVolume",1);
            PendingIntent pendingUpIntent;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                pendingUpIntent = PendingIntent.getBroadcast(this, 1021, upIntent, PendingIntent.FLAG_IMMUTABLE);
            }else{
                pendingUpIntent = PendingIntent.getBroadcast(this, 1021, upIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            contentView.setOnClickPendingIntent(R.id.btn_up,pendingUpIntent);

            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setAutoCancel(false);
            mBuilder.setOngoing(true);
            mBuilder.setPriority(Notification.PRIORITY_HIGH);
            mBuilder.setOnlyAlertOnce(true);
            mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
            mBuilder.setContent(contentView);

            notification = mBuilder.build();
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(NOTIFICATION_ID, notification);
            startForeground(NOTIFICATION_ID,notification);
        }else{
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);

            Intent downIntent = new Intent(this, NotificationReceiver.class);
            downIntent.putExtra("DownVolume",0);
            PendingIntent pendingDownIntent = PendingIntent.getBroadcast(this, 1020, downIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            contentView.setOnClickPendingIntent(R.id.btn_down, pendingDownIntent);

            Intent upIntent = new Intent(this, NotificationReceiver.class);
            upIntent.putExtra("UpVolume",1);
            PendingIntent pendingUpIntent = PendingIntent.getBroadcast(this, 1021, upIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            contentView.setOnClickPendingIntent(R.id.btn_up,pendingUpIntent);

            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setAutoCancel(false);
            mBuilder.setOngoing(true);
            mBuilder.setPriority(Notification.PRIORITY_HIGH);
            mBuilder.setOnlyAlertOnce(true);
            mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
            mBuilder.setContent(contentView);
            mBuilder.setOngoing(true);

            notification = mBuilder.build();
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
