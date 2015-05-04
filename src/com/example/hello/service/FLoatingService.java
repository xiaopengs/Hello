package com.example.hello.service;

import com.example.hello.R;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;

public class FLoatingService extends Service {
	private static final String TAG = "FLoatingService";
	private WindowManager windowManager;
	private ImageView chatHead;
	private PopupWindow pwindo;
	WindowManager.LayoutParams mLayoutParams;
	boolean mHasDoubleClicked = false;
	long lastPressTime;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		chatHead = new ImageView(this);
		chatHead.setId(10);
		chatHead.setImageResource(R.drawable.floating2);
		addFloatWindow();
		setTouchEventListener();
	}

	@Override
	public void onDestroy() {
		if(chatHead!= null && chatHead.isShown()){
			windowManager.removeView(chatHead);
		}
		super.onDestroy();
	}
	
	private void setTouchEventListener() {
		try {
			chatHead.setOnTouchListener(new View.OnTouchListener() {
				private WindowManager.LayoutParams paramsF = mLayoutParams;
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						// Get current time in nano seconds.
						long pressTime = System.currentTimeMillis();
						// If double click...
						if (pressTime - lastPressTime <= 300) {
							Log.i(TAG, "xiaopeng double click...");
							// FLoatingService.this.stopSelf();
							initiatePopupWindow(chatHead);
							mHasDoubleClicked = true;
						} else { // If not double click....
							mHasDoubleClicked = false;
						}
						lastPressTime = pressTime;
						initialX = paramsF.x;
						initialY = paramsF.y;
						initialTouchX = event.getRawX();
						initialTouchY = event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						break;
					case MotionEvent.ACTION_MOVE:
						paramsF.x = initialX
								+ (int) (event.getRawX() - initialTouchX);
						paramsF.y = initialY
								+ (int) (event.getRawY() - initialTouchY);
						mLayoutParams = paramsF;
						windowManager.updateViewLayout(chatHead, paramsF);
						break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

		chatHead.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// initiatePopupWindow(chatHead);
			}
		});

	}

	private void initiatePopupWindow(View anchor) {
		try {
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			ListPopupWindow popup = new ListPopupWindow(this);
			popup.setAnchorView(anchor);
			popup.setWidth((int) (display.getWidth() / (1.5)));
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, new String[] {
							"HOME", "hello2" });
			popup.setAdapter(arrayAdapter);
			popup.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id3) {
					if(position == 0){
						Intent intent = new Intent(Intent.ACTION_MAIN); 
				        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意 
				        intent.addCategory(Intent.CATEGORY_HOME); 
				        startActivity(intent); 
					}
					
				}
			});
			popup.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addFloatWindow() {
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;
		mLayoutParams = params;
		windowManager.addView(chatHead, params);
	}

}
