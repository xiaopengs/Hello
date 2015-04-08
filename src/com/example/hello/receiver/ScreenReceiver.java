package com.example.hello.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hello.demo.QQDialog;

public class ScreenReceiver extends BroadcastReceiver {

    public ScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("sunfei", "get broadcast " + intent.getAction());
        if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
            context.startActivity(new Intent(context, QQDialog.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }
}
