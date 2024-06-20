package com.selfagent.func;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selfagent.R;
import com.selfagent.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

    }

}