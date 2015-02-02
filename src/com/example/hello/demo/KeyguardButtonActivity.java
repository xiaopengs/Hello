package com.example.hello.demo;

import com.example.hello.R;
import com.example.hello.service.BackgroundService;
import com.example.hello.service.FLoatingService;
import com.example.hello.service.FLoatingService2;

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
		//startService(new Intent(this, BackgroundService.class));
		//startService(new Intent(this, FLoatingService.class));
		startService(new Intent(this, FLoatingService2.class));
	}
	
}
