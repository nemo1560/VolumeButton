package com.nemo1560.notificationvolume;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService(this);
    }

    private void initService(Activity activity){
        Intent intent = new Intent(activity,NotificationService.class);
        activity.startService(intent);
        activity.finishAffinity();
    }
}