package com.bagoshort.func;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;

import com.bagoshort.R;
import com.bagoshort.core.widget.player.JzvdStdTikTok;
import com.bagoshort.core.widget.player.ViewPagerLayoutManager;
import com.bagoshort.databinding.FragmentPlayBinding;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

public class PlayFragment extends Fragment {

    public PlayFragment() {}
    private FragmentPlayBinding binding;

    public boolean isShowMenu = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayBinding.inflate(getLayoutInflater());
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
        initShowMenu();
        initList();
        return binding.getRoot();
    }

    private void initShowMenu(){
        binding.showManu.setChecked(isShowMenu);
        binding.showManu.setOnCheckedChangeListener((compoundButton, b) -> {
            isShowMenu = b;
            if (adapter!=null) {
                adapter.showMenu = isShowMenu;
                showInfo();
            }
        });
    }

    private void showInfo(){
        if (binding.list.getChildAt(0) == null) return;
        View childView = binding.list.getChildAt(0);
        if (childView == null) return;
        View btmView = childView.findViewById(R.id.btm_layout);
        View leftView = childView.findViewById(R.id.left_layout);
        if (leftView == null) return;
        leftView.setVisibility( isShowMenu ? View.VISIBLE :View.GONE );
        btmView.setVisibility( isShowMenu ? View.VISIBLE :View.GONE );
    }

    private PlayAdapter adapter;
    private ViewPagerLayoutManager mViewPagerLayoutManager ;
    private static int mCurrentPosition = 0;
    private static List<String> datas = new ArrayList<>();
    private void initList(){
        adapter = new PlayAdapter(isShowMenu);
        mViewPagerLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
        binding.list.setLayoutManager(mViewPagerLayoutManager);
        binding.list.setAdapter(adapter);
        mViewPagerLayoutManager.setOnViewPagerListener(new ViewPagerLayoutManager.OnViewPagerListener() {
            @Override
            public void onInitComplete() { autoPlayVideo();}
            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurrentPosition == position) Jzvd.releaseAllVideos();
            }
            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                autoPlayVideo();
                mCurrentPosition = position;
            }
        });
        binding.list.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) { }
            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd != null && Jzvd.CURRENT_JZVD != null && jzvd.jzDataSource != null && jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) Jzvd.releaseAllVideos();
            }
        });
        for (int i = 0; i < 10; i++) {
            datas.add(""+i);
        }
        adapter.setList(datas);
    }

    public void autoPlayVideo() {
        //视频播放
        if (binding.list.getChildAt(0) == null) return;
        View childView = binding.list.getChildAt(0);
        if (childView == null) return;
        JzvdStdTikTok player = childView.findViewById(R.id.videoplayer);
        if (player == null) return;
        player.startVideoAfterPreloading();
    }

}