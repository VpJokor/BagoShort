package com.bagoshort.func;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bagoshort.R;
import com.bagoshort.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

    }

}