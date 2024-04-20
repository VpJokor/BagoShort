package com.bagoshort.func;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagoshort.R;
import com.bagoshort.core.data.BannerData;
import com.bagoshort.core.utils.ShowUtil;
import com.bagoshort.core.utils.SizeUtil;
import com.bagoshort.core.utils.blur.BlurTransformation;
import com.bagoshort.databinding.FragmentHomeBinding;
import com.bagoshort.databinding.FragmentHomeHeaderBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {}
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        initToolBar();
        initHeader();
        initList();
        return binding.getRoot();
    }

    private void initToolBar(){
        binding.toolbar.setTitle("");
        binding.appBarLayout.setPadding(0, SizeUtil.getStatusBarHeight(getContext()),0,0);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        binding.search.setOnClickListener(v -> {
            ShowUtil.showToast(getContext(),"搜索");
        });
        binding.coin.setOnClickListener(view -> {
            ShowUtil.showToast(getContext(),"充值");
        });
    }

    private FragmentHomeHeaderBinding headerBinding;
    private void initHeader(){
        headerBinding = FragmentHomeHeaderBinding.inflate(getLayoutInflater());
        Glide.with(getContext())
                .load(R.mipmap.history)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(headerBinding.historyImg);
        Glide.with(getContext())
                .load(R.mipmap.collection)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(headerBinding.collectionImg);
        List<BannerData> bannerList = new ArrayList<>();
        bannerList.add(new BannerData("", "标题1"));
        bannerList.add(new BannerData("", "标题2"));
        bannerList.add(new BannerData("", "标题3"));
        headerBinding.banner.setAdapter(new BannerImageAdapter<BannerData>(bannerList) {
                    @Override
                    public void onBindView(BannerImageHolder holder, BannerData data, int position, int size) {
                        Glide.with(getContext())
                                .load( position %2 == 0 ? R.mipmap.collection : R.mipmap.history )
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.imageView);
                        holder.imageView.setOnClickListener(view -> {
                            ShowUtil.showToast(getContext(),"Click Banner");
                        });
                    }
                }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(requireContext()));

    }

    private HomeAdapter listAdapter ;
    private void initList(){
        binding.list.setLayoutManager(new GridLayoutManager(getContext(),3));
        listAdapter = new HomeAdapter();
        listAdapter.addHeaderView(headerBinding.getRoot());
        listAdapter.setUseEmpty(true);
        listAdapter.setEmptyView(R.layout.fragment_home_empty);
        listAdapter.getLoadMoreModule().setOnLoadMoreListener(this::loadMore);
        listAdapter.getLoadMoreModule().setPreLoadNumber(6);
        binding.list.setAdapter(listAdapter);
        binding.refresh.setOnRefreshListener(refreshLayout -> refresh());
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(""+i);
        }
        listAdapter.setList(datas);
    }

    private void loadMore(){
        ShowUtil.showToast(getContext(),"加载更多");
    }

    private void refresh(){
        binding.refresh.finishRefresh(300);
        ShowUtil.showToast(getContext(),"刷新");
    }

    private void search(){

    }
}