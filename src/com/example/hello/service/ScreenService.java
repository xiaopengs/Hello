package com.example.hello.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.hello.constants.Contants;
import com.example.hello.receiver.ScreenReceiver;

public class ScreenService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener {
    private BroadcastReceiver mScreenReceiver;
    private SharedPreferences mSharedPreferences;

    public ScreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        regScreenReceiver();
        mSharedPreferences = getSharedPreferences(Contants.SPKEY, MODE_PRIVATE);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mScreenReceiver != null){
            unRegReceiver(mScreenReceiver);
        }
    }

    private void regScreenReceiver(){
        mScreenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);
    }

    private void unRegReceiver(BroadcastReceiver receiver){
        unregisterReceiver(receiver);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Contants.QQ)){
            boolean isOpen = sharedPreferences.getBoolean(Contants.QQ,false);
            Log.i(Contants.TAG, "qq switch is changed");
            if(isOpen){
                regScreenReceiver();
            }else {
                if(mScreenReceiver != null){
                    unRegReceiver(mScreenReceiver);
                }
            }
        }
    }
}
