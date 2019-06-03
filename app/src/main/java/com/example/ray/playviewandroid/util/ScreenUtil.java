package com.example.ray.playviewandroid.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**屏幕相关类
 * @use ScreenUtil.xxxMethod(...);
 */
public class ScreenUtil {
	//	private static final String TAG = "SceenUtil";

	private ScreenUtil() {/* 不能实例化**/}


	public static int[] screenSize;
	public static int[] getScreenSize(Context context){
		if (screenSize == null || screenSize[0] <= 480 || screenSize[1] <= 800) {//小于该分辨率会显示不全
			screenSize = new int[2];

			DisplayMetrics dm = new DisplayMetrics();
			dm = context.getResources().getDisplayMetrics();
			// float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
			// int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
			// float xdpi = dm.xdpi;
			// float ydpi = dm.ydpi;
			// LogUtil.d(TAG + " DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
			// LogUtil.d(TAG + " DisplayMetrics", "density=" + density + "; densityDPI="
			// + densityDPI);

			screenSize[0] = dm.widthPixels;// 屏幕宽（像素，如：480px）
			screenSize[1] = dm.heightPixels;// 屏幕高（像素，如：800px）
		}

		return screenSize;
	}
	
	public static int getScreenWidth(Context context){
		return getScreenSize(context)[0];
	}
	public static int getScreenHeight(Context context){
		return getScreenSize(context)[1];
	}


}
