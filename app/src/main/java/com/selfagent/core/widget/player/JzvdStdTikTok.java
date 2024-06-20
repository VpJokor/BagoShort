package com.selfagent.core.widget.player;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


import com.selfagent.R;

import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

public class JzvdStdTikTok extends JzvdStd {

    public JzvdStdTikTok(Context context) {
        super(context);
    }

    public JzvdStdTikTok(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        topContainer.setVisibility(GONE);
        bottomProgressBar.setVisibility(VISIBLE);
        posterImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    //changeUiTo 真能能修改ui的方法
    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
        topContainer.setVisibility(GONE);
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int posterImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(topCon);
        startButton.setVisibility(startBtn);
        loadingProgressBar.setVisibility(loadingPro);
        posterImageView.setVisibility(posterImg);
        mRetryLayout.setVisibility(retryLayout);
    }

    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                topContainer.setVisibility(INVISIBLE);
                startButton.setVisibility(INVISIBLE);
                if (clarityPopWindow != null) clarityPopWindow.dismiss();

            });
        }
    }

    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
        startButton.performClick();
        topContainer.setVisibility(GONE);
    }

    public void updateStartImage() {
        if (state == STATE_PLAYING) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.tiktok_play_tiktok);
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_ERROR) {
            startButton.setVisibility(INVISIBLE);
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_AUTO_COMPLETE) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.tiktok_play_tiktok);
            replayTextView.setVisibility(VISIBLE);
        } else {
            startButton.setImageResource(R.drawable.tiktok_play_tiktok);
            replayTextView.setVisibility(GONE);
        }
    }

    private boolean isShowProgress = true;
    @Override
    public void onPrepared() {
        super.onPrepared();
        //时长小于40s的视频默认不展示进度条
        isShowProgress = mediaInterface.getDuration() >= 40000;
        showProgress();

    }


    private void showProgress(){
        Log.e(TAG, "onPrepared: duration = " + mediaInterface.getDuration());
        if ( !isShowProgress && !mTouchingProgressBar ) {
            //清理播放进度
            JZUtils.clearSavedProgress(getContext(),jzDataSource.getCurrentUrl());
            progressBar.setVisibility(INVISIBLE);
        }
        else {
            progressBar.setVisibility(VISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_jzstd_video;
    }
}
