package com.bagoshort.func;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.bagoshort.R;
import com.bagoshort.core.utils.StatusBarUtil;
import com.bagoshort.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        StatusBarUtil.setAndroidNativeLightStatusBar(this,true);
        initPager();
    }

    private void initPager(){
        MainAdapter adapter = new MainAdapter(this);
        binding.pager.setAdapter(adapter);
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.navigation.getMenu().getItem(position).setChecked(true);
                StatusBarUtil.setAndroidNativeLightStatusBar(MainActivity.this,position == 1);
            }
        });
        binding.navigation.setItemIconTintList(null);
        binding.navigation.setOnItemSelectedListener(item -> {
            if ( item.getItemId() == R.id.navigation_play ){
                binding.pager.setCurrentItem(0, true);
                return true;
            }
            if ( item.getItemId() == R.id.navigation_home ){
                binding.pager.setCurrentItem(1, true);
                return true;
            }
            return false;
        });
        binding.pager.setCurrentItem(1);
    }

    /**
     *  用来控制 MainActivity的ViewPager2
     */
    public static class MainAdapter extends FragmentStateAdapter {

        List<Fragment> fragmentList= new ArrayList<>();

        public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            fragmentList.add(new PlayFragment());
            fragmentList.add(new HomeFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }

    }

}