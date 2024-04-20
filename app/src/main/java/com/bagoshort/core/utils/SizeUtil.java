package com.bagoshort.core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;

public class SizeUtil {
	//获取状态栏高度
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) result = context.getResources().getDimensionPixelSize(resourceId);
		if (result == 0) result = (int)Math.ceil(25 * context.getResources().getDisplayMetrics().density);
		return result;
	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px( float dpValue) {
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(float pxValue) {
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(final float spValue) {
		final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static ScreenInfo getWidgetSizePx(View view){
		int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		view.measure(width,height);
		int height1=view.getMeasuredHeight();
		int width1=view.getMeasuredWidth();
		return new ScreenInfo( width1,height1);
	}

	public static ScreenInfo getScreenPxSize(Context context){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return new ScreenInfo( dm.widthPixels,dm.heightPixels);
	}

	public static class ScreenInfo{
		private int widthDp;
		private int heightDp;

		public ScreenInfo(int widthDp, int heightDp) {
			this.widthDp = widthDp;
			this.heightDp = heightDp;
		}

		public int getWidthDp() {
			return widthDp;
		}

		public int getHeightDp() {
			return heightDp;
		}
	}
}
