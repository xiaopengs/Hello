package com.example.hello.demo;

import com.example.hello.R;
import com.example.hello.demo.utils.KeyguardUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class QQDialog extends Activity {

	private LinearLayout mLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dialog_qq);
		final Context context = getApplicationContext();
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		mLinearLayout = (LinearLayout) findViewById(R.id.ll_dynamic);
		mLinearLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				KeyguardUtils.dismissReject();
				startActivity(new Intent(context, DemoActivity.class));
				QQDialog.this.finish();
			}
		});
	}

}
