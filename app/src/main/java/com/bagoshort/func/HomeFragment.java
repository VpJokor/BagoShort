package com.bagoshort.func;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagoshort.R;
import com.bagoshort.core.utils.ShowUtil;
import com.bagoshort.core.utils.SizeUtil;
import com.bagoshort.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    public HomeFragment() {}
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        initToolBar();
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
//        binding.friends.setOnClickListener(view -> {
//
//        });
    }

    private void initList(){

    }

    private void initSearch(){

    }
}