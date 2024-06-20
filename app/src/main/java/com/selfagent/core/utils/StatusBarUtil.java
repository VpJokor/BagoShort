package com.selfagent.core.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtil {

    private static final String TAG = "StatusBarUtil";

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getColor(colorId));
    }

//    修改状态栏字体颜色
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        if (activity==null || activity.getWindow()==null || activity.getWindow().getDecorView() ==null) return;
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    //改变状态栏返回键颜色
//    public static void setToolbarCustomTheme(Context context , ActionBar actionBar, int color) {
//        Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.base_back);
//        if(upArrow != null) {
////            upArrow.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
//            BlendModeColorFilter colorFilter = new BlendModeColorFilter(ContextCompat.getColor(context, color), BlendMode.SRC_ATOP);
//            upArrow.setColorFilter(colorFilter);
//            upArrow.setTint(context.getColor(color));
//            if(actionBar != null) {
//                actionBar.setHomeAsUpIndicator(upArrow);
//            }else {
//                Log.e(TAG, "setToolbarCustomTheme: actionBar is null" );
//            }
//        }
//    }
//
//    public static void setToolbarCustomThemeAlpha(Context context , ActionBar actionBar, int alpha,int color) {
//        Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.base_back);
//        if(upArrow != null) {
////            upArrow.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
//            BlendModeColorFilter colorFilter = new BlendModeColorFilter(ContextCompat.getColor(context, color), BlendMode.SRC_ATOP);
//            upArrow.setColorFilter(colorFilter);
//            upArrow.setAlpha(alpha);
//            if(actionBar != null) {
//                actionBar.setHomeAsUpIndicator(upArrow);
//            }
//        }
//    }
//
//    public static void setToolbarCustomThemeAlphaOragine(Context context , ActionBar actionBar, int alpha,int color) {
//        Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.base_back);
//        if(upArrow != null) {
////            upArrow.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
//            BlendModeColorFilter colorFilter = new BlendModeColorFilter(color, BlendMode.SRC_ATOP);
//            upArrow.setColorFilter(colorFilter);
//            upArrow.setAlpha(alpha);
//            if(actionBar != null) {
//                actionBar.setHomeAsUpIndicator(upArrow);
//            }
//        }
//    }
}
