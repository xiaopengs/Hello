package com.example.hello.demo;

import java.util.ArrayList;

import android.os.Bundle;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.ListActivity;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class KeyguardDisableActivity extends Activity  {

	private boolean flag = true;
	
	private KeyguardManager km ;
	private KeyguardLock k;
	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        
        km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
		k = km.newKeyguardLock("lock");
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		
		btn = new Button(this);
		btn.setText("Keyguard enable. Click disable it");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(flag){
					Log.i("1111", "disable");
					k.disableKeyguard();
					btn.setText("Keyguard disabled. Click enable it");
					flag = false;
				}else{
					Log.i("1111", "reenableKeyguard");
					k.reenableKeyguard();
					btn.setText("Keyguard enable. Click disable it");
					flag = true;
				}
				
			}
		});
		
		setContentView(btn);
    }
}
