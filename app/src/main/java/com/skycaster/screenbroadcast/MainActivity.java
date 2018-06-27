package com.skycaster.screenbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final String TAG = "skycaster test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final IntentFilter filter = new IntentFilter();
        // 灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);

        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                Log.d(TAG, "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d(TAG, "screen on");
                    try {
                        // 来控制按键板背光亮
                        Runtime.getRuntime().exec(new String[]{"sh", "-c" , "echo 1 > /sys/class/gpio/gpio215/value\n"});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d(TAG, "screen off");
                    try {
                        // 控制按键板背光灭
                        Runtime.getRuntime().exec(new String[]{"sh", "-c" , "echo 0 > /sys/class/gpio/gpio215/value\n"});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        Log.d(TAG, "registerReceiver");
        registerReceiver(mBatInfoReceiver, filter);

    }
}
