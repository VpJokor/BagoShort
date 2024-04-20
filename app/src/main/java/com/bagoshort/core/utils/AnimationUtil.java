package com.bagoshort.core.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;




public class AnimationUtil {
    private static AnimationUtil _instance;
    public static AnimationUtil getInstance(){
        if (_instance ==null) _instance = new AnimationUtil();
        return _instance;
    }

    public static void fadeIn(View view, float startAlpha, float endAlpha, long duration) {
        view.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void fadeIn(View view,int dur) {
        fadeIn(view, 0F, 1F, dur);
        view.setEnabled(true);
    }

    public static void fadeIn(View view) {
        fadeIn(view, 0F, 1F, 800);
        view.setEnabled(true);
    }

    public static void fadeOut(View view) {
        if (view.getVisibility() != View.VISIBLE) return;
        view.setEnabled(false);
        Animation animation = new AlphaAnimation(1F, 0F);
        animation.setDuration(400);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }



}
