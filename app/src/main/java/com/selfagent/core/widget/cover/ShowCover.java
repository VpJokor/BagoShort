package com.selfagent.core.widget.cover;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selfagent.R;
import com.selfagent.core.utils.AnimationUtil;


public class ShowCover {

    public View cover;
    private long start = 0;
    //显示背景蒙层
    public void showCover(Activity context) {
        start = System.currentTimeMillis();
        if (cover==null) cover = LayoutInflater.from(context).inflate(R.layout.layout_cover,null);
        if (cover.getParent()==null){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            context.addContentView(cover,params);
        }
        AnimationUtil.fadeIn(cover);
    }

    public void showCover(Activity context,int dur) {
        start = System.currentTimeMillis();
        if (cover==null) cover = LayoutInflater.from(context).inflate(R.layout.layout_cover,null);
        if (cover.getParent()==null){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            context.addContentView(cover,params);
        }
        AnimationUtil.fadeIn(cover,dur);
    }

    //隐藏背景蒙层
    public void hideCover(long dur){
        if (cover == null) return;
        if (dur == 0) AnimationUtil.fadeOut(cover);
        else {
            if ( System.currentTimeMillis() - start < dur ) new Handler().postDelayed(() -> AnimationUtil.fadeOut(cover), dur/2);
            else AnimationUtil.fadeOut(cover);
        }
    }

}
