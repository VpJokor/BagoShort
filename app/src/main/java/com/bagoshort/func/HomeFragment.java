package com.bagoshort.func;

import static android.app.Activity.RESULT_OK;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private ActivityResultLauncher<Intent> launcher ;
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
            if (launcher ==null) launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (data == null) return;
                int resultCode = result.getResultCode();
                if ( resultCode == RESULT_OK) {

                }
            });
            launcher.launch(new Intent(getContext(), SearchActivity.class));
        });
        binding.coin.setOnClickListener(view -> {
            ShowUtil.showToast(getContext(),"充值弹框");
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
                .setIndicator(new CircleIndicator(requireContext()))
                .setLoopTime(3000);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.tabs.setLayoutManager(layoutManager);
        List<String> datas = new ArrayList<>();
        datas.add("热门推荐");
        datas.add("王妃");
        datas.add("小人物");
        datas.add("古装");
        datas.add("时空之旅");
        datas.add("喜剧");
        datas.add("都市脑洞");
        datas.add("先婚后爱");
        datas.add("现代言情");

        HomeTagAdapter homeTagAdapter = new HomeTagAdapter(datas);
        homeTagAdapter.setOnItemClickListener((adapter, view, position) -> {
            //老Item缩小
            TextView oldlabel = (TextView) homeTagAdapter.getViewByPosition(homeTagAdapter.index,R.id.label);
            if (oldlabel!=null){
                ValueAnimator smallAnimator = ValueAnimator.ofFloat(14.5f, 14f);
                smallAnimator.addUpdateListener(valueAnimator -> {
                    oldlabel.setTextSize((Float)valueAnimator.getAnimatedValue());
                });
                smallAnimator.setDuration(100);
                smallAnimator.start();
                oldlabel.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                oldlabel.setTextColor(getContext().getColor(R.color.color_grey_900));
            }else homeTagAdapter.notifyItemChanged(homeTagAdapter.index);
            //更新index
            homeTagAdapter.index = position;
            //新item放大
            TextView label = view.findViewById(R.id.label);
            ValueAnimator bigAnimator = ValueAnimator.ofFloat(14f, 14.5f);
            bigAnimator.addUpdateListener(valueAnimator -> {
                label.setTextSize((Float)valueAnimator.getAnimatedValue());
            });
            bigAnimator.setDuration(100);
            bigAnimator.start();
            label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            label.setTextColor(getContext().getColor(R.color.black));
            // TODO: 2023/12/26 刷新列表数据

        });
        binding.tabs.setAdapter(homeTagAdapter);
        binding.showTabs.setOnClickListener(view -> new HomeTagPopView(requireContext(), homeTagAdapter.index, position -> {
            homeTagAdapter.index = position;
            homeTagAdapter.notifyDataSetChanged();
            binding.tabs.smoothScrollToPosition(position);
            // TODO: 2023/12/26 刷新列表数据
        }).show());
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