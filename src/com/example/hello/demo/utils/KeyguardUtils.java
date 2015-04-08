package com.example.hello.demo.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class KeyguardUtils {
	
	private KeyguardUtils() {}

	/**
	 * for Android 4.4 dimiss Keyguard
	 */
	@SuppressWarnings("unchecked")
	public static void dismissReject() {
		try {
			@SuppressWarnings("rawtypes")
			Class claz = Class.forName("android.app.ActivityManagerNative");
			Method m2 = claz.getMethod("getDefault");
			Method m = claz.getMethod("keyguardWaitingForActivityDrawn");
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

	public static void overrideTranstion() {
		try {
			@SuppressWarnings("rawtypes")
			Class claz = Class.forName("android.view.WindowManagerGlobal");
			Method m2 = claz.getMethod("getDefault");
			Method m = claz.getMethod("keyguardWaitingForActivityDrawn");
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
}
