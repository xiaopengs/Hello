package com.example.hello.demo;

import com.example.hello.R;
import com.example.hello.constants.Contants;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class QQDialogDemoActivity extends Activity{
	private Switch mSwitch ;
	private SharedPreferences mSharedPreference;

	public QQDialogDemoActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_qq_dialog_demo);
		mSharedPreference = getSharedPreferences(Contants.SPKEY, MODE_PRIVATE);
		mSwitch = (Switch) findViewById(R.id.openQQDialog);
		mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.i(Contants.TAG, "switch is " + (isChecked?" on":"off"));
				mSharedPreference.edit().putBoolean(Contants.QQ, isChecked).commit();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSwitch.setChecked(mSharedPreference.getBoolean(Contants.QQ, false));
	}
}
