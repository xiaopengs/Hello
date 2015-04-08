package com.example.hello;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.hello.service.ScreenService;

/**
 * Created by seonfly on 2015-03-19.
 */
public class HelloApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this,ScreenService.class));
    }

}
