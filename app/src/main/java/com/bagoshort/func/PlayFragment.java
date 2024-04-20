package com.bagoshort.func;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagoshort.R;
import com.bagoshort.databinding.FragmentPlayBinding;

public class PlayFragment extends Fragment {

    public PlayFragment() {}
    private FragmentPlayBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

}