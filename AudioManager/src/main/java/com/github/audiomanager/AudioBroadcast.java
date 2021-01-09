package com.github.audiomanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class AudioBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            int type = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
            int value = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            int preValue = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
            AudioTools.get().notifyListener(type, preValue, value);
        }
    }

    protected static BroadcastReceiver register(Context context) {
        if (context == null) {
            throw new IllegalStateException("AudioBroadcast.register(Context context) context is null");
        }
        AudioBroadcast audioBroadcast = new AudioBroadcast();
        IntentFilter intentFilter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        context.registerReceiver(audioBroadcast, intentFilter);
        return audioBroadcast;
    }

    protected static void unRegister(Context context, BroadcastReceiver broadcastReceiver) {
        if (context == null || broadcastReceiver == null) {
            return;
        }
        context.unregisterReceiver(broadcastReceiver);
    }
}
