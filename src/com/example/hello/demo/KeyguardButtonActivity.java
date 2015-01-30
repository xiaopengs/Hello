package com.example.hello.demo;

import com.example.hello.R;
import com.example.hello.service.BackgroundService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class KeyguardButtonActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("xiaopeng", "xiaopeng");
		startService(new Intent(this, BackgroundService.class));
	}
	
}
