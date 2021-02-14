package com.example.musicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MusicService extends Service {
     String TAG ;
    private MediaPlayer mediaPlayer;
    boolean isRunning = false;
    int[] ids = new int[5];
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    int song=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createForegroundService(intent);
        String input = intent.getStringExtra("songName");
        ids[0] = R.raw.tumhiho;
        ids[1] = R.raw.kalejelibaasdikaka;
        ids[2] = R.raw.temporarypyarkaka;
        ids[3] = R.raw.titliaanafsanakhan;
        if(input.equals("tumHiHo"))
            mediaPlayer = MediaPlayer.create(this, ids[0]);
        else if(input.equals("kaleJeLibas"))
            mediaPlayer = MediaPlayer.create(this, ids[1]);
        else if(input.equals("tempPyaar"))
            mediaPlayer = MediaPlayer.create(this, ids[2]);
        else if(input.equals("titliyaan"))
            mediaPlayer = MediaPlayer.create(this, ids[3]);

        if (!isRunning) {
            isRunning = true;
            mediaPlayer.start();
        }
        return START_STICKY;
    }
    public void createForegroundService(Intent intent){
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Player")
                .setContentText("Foreground service for music app")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    public void startMusic(Intent intent){

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        changeSong();
    }

    public void changeSong(){
        Log.i(TAG,"Plus11111");
        mediaPlayer.stop();
        song++;
        System.out.println(song);
        Log.i(TAG,"Plus22222");
        if(song>5)
            song=0;
        mediaPlayer = MediaPlayer.create(this, ids[song]);
    }

}