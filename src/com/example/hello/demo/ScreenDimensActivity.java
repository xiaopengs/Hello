package com.example.hello.demo;

import com.example.hello.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScreenDimensActivity extends Activity  {

	private boolean flag = true;
	private Button btnTest;
	private TextView tv;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x40000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        //this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
        setContentView(R.layout.activity_main);
        
        btnTest = (Button)findViewById(R.id.test);	
        tv = (TextView)findViewById(R.id.test_text);	
		btnTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent i = new Intent("com.android.internal.policy.impl.LockPatternKeyguardView.KEYGUARD");
				Log.i("MainActivityt", "send it");
				//sendBroadcast(i);
				//tv.setText(null);
				printWidth(tv);
			}
		});
    }
	
	private void printWidth(TextView TV){
		// 要获取屏幕的宽和高等参数，首先需要声明一个DisplayMetrics对象，屏幕的宽高等属性存放在这个对象中
        DisplayMetrics DM = new DisplayMetrics();
        // 获取窗口管理器,获取当前的窗口,调用getDefaultDisplay()后，其将关于屏幕的一些信息写进DM对象中,最后通过getMetrics(DM)获取
        getWindowManager().getDefaultDisplay().getMetrics(DM);

        int wdip = px2dip(getApplicationContext(), getApplicationContext()
                .getResources().getDisplayMetrics().widthPixels);
        int hdip = px2dip(getApplicationContext(), getApplicationContext()
                .getResources().getDisplayMetrics().heightPixels);

        // 打印获取的宽和高
        TV.setText("densityDpi: "
                + DM.densityDpi
                + "\n"
                + "density: "
                + DM.density
                + "\n"
                + "scaledDensity: "
                + DM.scaledDensity
                + "\n"
                + "heightPixels(The absolute height of the display in pixels.): \n "
                + DM.heightPixels
                + "\n"
                + "widthPixels(The absolute width of the display in pixels.): \n "
                + DM.widthPixels
                + "\n"
                + "xdpi(The exact physical pixels per inch of the screen in the X dimension): \n "
                + DM.xdpi
                + "\n"
                + "ydpi(The exact physical pixels per inch of the screen in the Y dimension): \n "
                + DM.ydpi + "\n"

                + "wdip: " + wdip + "\n"

                + "hdip: " + hdip + "\n");
	}
	
	
	@Override
	public void onAttachedToWindow() {
		//this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}
	
	/**
     * Change value from pix to dip
     * 
     * @param context
     * @param pxValue
     * @return
     */
    public final static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);
    }

}
