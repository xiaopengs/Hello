package com.example.hello.service;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import com.example.hello.R;
import com.example.hello.demo.DemoActivity;

public class BackgroundService extends Service {
    private static final String TAG = "BackgroundService";

    private static int NOTIFICATION_ID = 0;

    LinearLayout mLayout;
    WindowManager.LayoutParams mLayoutParams;
    WindowManager mWinManager;
    NotificationManager mNotificationManager;
    private Bitmap icon;
    private static int messageNum = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("xiaopeng", "xiaopeng onCreate");
        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.app_icon);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createWindow();


//        startActivity();
    }

    private void dismiss(Context c) {
        KeyguardManager km = (KeyguardManager) c.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock kl = km.newKeyguardLock("test");
        kl.disableKeyguard();
    }

    private void unDismiss(Context c) {
        KeyguardManager km = (KeyguardManager) c.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock kl = km.newKeyguardLock("test");
        kl.reenableKeyguard();
    }

    /*
    for android 4.4
     */
    private void dismissReject() {
        try {
            Class claz = Class.forName("android.app.ActivityManagerNative");
            Method m2 = claz.getMethod("getDefault");
            Method m = claz.getMethod("dismissKeyguardOnNextActivity");
            Object obj = m2.invoke(null);
            m.invoke(obj);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void createWindow() {
    	Log.i("xiaopeng", "xiaopeng createWindow");
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mLayout = (LinearLayout) inflater.inflate(R.layout.float_window, null);

        Button button = (Button) mLayout.findViewById(R.id.skip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "turn to mainActivity");

                dismiss(getApplicationContext());

                Intent it = new Intent(BackgroundService.this, DemoActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);

                startActivity(it);
            }
        });

        Button notifyBtn = (Button) mLayout.findViewById(R.id.notification);
        Button bigNotifyBtn = (Button) mLayout.findViewById(R.id.big_notify);
        Button customNotifyBtn = (Button) mLayout.findViewById(R.id.custom_notify);

        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormal();
            }
        });

        bigNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBigText();
            }
        });

        customNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustom();
            }
        });

/*        Button  reset = (Button) mLayout.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unDismiss(getApplicationContext());
            }
        });*/

        mLayoutParams = new WindowManager.LayoutParams();
        mWinManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);

        //设置悬浮框x、y初始值
        mLayoutParams.x = 0;
        mLayoutParams.y = -24;
        //设置悬浮框长宽
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        mLayoutParams.gravity = Gravity.START | Gravity.TOP;

        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mWinManager.addView(mLayout, mLayoutParams);
    }

    private void startActivity() {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent it = new Intent("com.sonmoe.skipkeyguard.alarmreceiver.startactivity");
        int requestCode = 0;
        PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(),
                requestCode, it, PendingIntent.FLAG_UPDATE_CURRENT);
        int delay = 5;
        int triggerAtTime = (int) (SystemClock.elapsedRealtime() + delay * 1000);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime, pendIntent);
        Log.i(TAG, "start a alarm delay " + delay + " seconds.");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWinManager.removeView(mLayout);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNormal() {

        Notification n = new Notification.Builder(getApplicationContext())
                .setLargeIcon(icon).setSmallIcon(R.drawable.ic_launcher)
                .setTicker("showNormal").setContentInfo("contentInfo")
                .setContentTitle("ContentTitle").setContentText("ContentText" + Math.random())
                .setNumber(++messageNum)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(getIntent())
                .setSound(null).build();
        mNotificationManager.notify(NOTIFICATION_ID++, n);
    }

    private void showBigText() {
        Notification.BigTextStyle textStyle = new Notification.BigTextStyle();
        textStyle
                .setBigContentTitle("BigContentTitle")
                .setSummaryText("SummaryText")
                .bigText(
                        "I am Big Texttttttttttttttttttttttttttttttttttttttttttt!!!!!!!!!!!!!!!!!!!......"
                                + Math.random());

        Notification notification = new Notification.Builder(getApplicationContext())
                .setLargeIcon(icon).setSmallIcon(R.drawable.ic_launcher)
                .setTicker("showBigView_Text").setContentInfo("contentInfo")
                .setContentTitle("ContentTitle").setContentText("ContentText")
                .setStyle(textStyle)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .setSound(null)
                //.setPublicVersion(getNot())//for 5.0
                .setContentIntent(getIntent())
                .build();
        mNotificationManager.notify(NOTIFICATION_ID++, notification);

    }

    private void showCustom() {
        Notification.Builder builder1 = new Notification.Builder(getApplicationContext());
        builder1.setLargeIcon(icon).setSmallIcon(R.drawable.app_icon)
                .setTicker("欢迎注册开源中国").setContentInfo("开源")
                .setContentTitle("开源中国").setContentText("欢迎注册开源中国")
                .setNumber(++messageNum)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(getIntent())
                .setSound(null);

       /* builder1.extend(new Notification.Extender() {
            @Override
            public Notification.Builder extend(Notification.Builder builder) {
                 RemoteViews expandView = new RemoteViews(getPackageName(),
                        R.layout.custom_notification);
                builder.setContent(expandView).setSmallIcon(R.drawable.music_icon)
                        .setLargeIcon(icon).setOngoing(true)
                        .setTicker("music is playing");

                return builder;
            }
        });*/

        Notification n = builder1.build();
        n.bigContentView = new RemoteViews(getPackageName(),
                R.layout.custom_notification);
        mNotificationManager.notify(NOTIFICATION_ID++, n);
    }


    private Notification getNot() {
        Notification n = new Notification.Builder(getApplicationContext())
                .setLargeIcon(icon).setSmallIcon(R.drawable.ic_launcher)
                .setTicker("showNormal").setContentInfo("contentInfo")
                .setContentTitle("ContentTitle").setContentText("隐藏内容" + Math.random())
                .setNumber(++messageNum)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(getIntent())
                .setSound(null).build();
        return n;
    }

    private PendingIntent getIntent(){
        Intent openintent = new Intent(this, DemoActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, openintent, 0);
        return contentIntent;
    }
}
