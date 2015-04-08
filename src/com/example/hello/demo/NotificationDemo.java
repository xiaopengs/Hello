package com.example.hello.demo;

import com.example.hello.R;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

public class NotificationDemo extends Activity implements OnClickListener{
	/** Notification构造器 */
	private NotificationCompat.Builder mCompatBuilder;
	
	private Button btnClearNotification,btnTest1,btnTest2,btnTest3;
	/** Notification管理 */
	public NotificationManager mNotificationManager;
	/** Notification构造器 */
	Builder mBuilder;
	/** Notification的ID */
	int notifyId = 101;
	/** Notification的ID */
	int notifyId2 = 101;
	
	private BigPictureStyle mNotificationStyle;
	
	private int number = 111;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//createNotification(this);
		
		setContentView(R.layout.demo_notification);
		btnClearNotification = (Button)findViewById(R.id.btn_clear);
		btnClearNotification.setOnClickListener(this);
		btnTest1 = (Button)findViewById(R.id.btn_test1);
		btnTest1.setOnClickListener(this);
		btnTest2 = (Button)findViewById(R.id.btn_test2);
		btnTest2.setOnClickListener(this);
		btnTest3 = (Button)findViewById(R.id.btn_test3);
		btnTest3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_clear:
			mNotificationManager.cancel(notifyId2);
			break;
		case R.id.btn_test1:
			createNotification1(this);
			break;
		case R.id.btn_test2:
			createNotification2(this);
			break;
		case R.id.btn_test3:
			createNotification3(this, number);
			number++;
			break;
		}
		
	}
	
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:  
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
	 * 点击去除： Notification.FLAG_AUTO_CANCEL 
	 */
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
	
	private void createNotification1(Context c){
		mBuilder = new Notification.Builder(this);
		mBuilder.setContentTitle("测试标题")
		.setContentText("测试内容")
		.setNumber(5)
		.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
		.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
		.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
		.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
//		.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
		.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
		.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
		//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
		.setSmallIcon(R.drawable.ic_launcher);
		Bitmap b = BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_appwidget_music_play);
		mNotificationStyle = new Notification.BigPictureStyle().bigPicture(b);
		mBuilder.setStyle(mNotificationStyle);
		
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	private void createNotification2(Context c){
		mCompatBuilder = new NotificationCompat.Builder(this);
		
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom);
		mRemoteViews.setImageViewResource(R.id.custom_icon, R.drawable.sing_icon);
//		view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
		mRemoteViews.setTextViewText(R.id.tv_custom_title, "今日头条");
		mRemoteViews.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布");
		  
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		String[] events = {"dd", "33", "44"};
		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("大视图内容:");
		// Moves events into the big view
	    for (int i=0; i < events.length; i++) {
	    	inboxStyle.addLine(events[i]);
	    }
	    
		mCompatBuilder.setContent(mRemoteViews)
		 			  .setSmallIcon(R.drawable.ic_launcher)
					  .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
					  .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
					  .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
					  .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
					  .setOngoing(false)
					  .setStyle(inboxStyle);
			         
		mNotificationManager.notify(notifyId2, mCompatBuilder.build());
	}
	
	private void createNotification3(Context c, int id){         
		Notification n = new Notification(R.drawable.ic_launcher, "Hello,there!", System.currentTimeMillis());              
		n.flags = Notification.FLAG_AUTO_CANCEL;
		                  
		n.setLatestEventInfo(
				c, 
		        "Hello,there!",  
		        "Hello,there,I'm john.",  
		        getDefalutIntent(Notification.FLAG_AUTO_CANCEL)); 
		RemoteViews bigContent = new RemoteViews(getPackageName(), R.layout.big_content);
		n.bigContentView = bigContent;
		
		mNotificationManager.notify(id, n); 
		
	}
	
	
	
}
