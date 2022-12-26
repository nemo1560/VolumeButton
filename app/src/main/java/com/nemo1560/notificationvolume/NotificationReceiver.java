package com.nemo1560.notificationvolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class NotificationReceiver extends BroadcastReceiver {
    private AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if(intent.hasExtra("DownVolume")){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
        }
        if(intent.hasExtra("UpVolume")){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
        }
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_SAME,AudioManager.FLAG_SHOW_UI);
    }
}
