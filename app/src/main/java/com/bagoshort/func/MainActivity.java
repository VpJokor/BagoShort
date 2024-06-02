package com.bagoshort.func;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bagoshort.R;
import com.bagoshort.core.utils.StatusBarUtil;
import com.bagoshort.databinding.ActivityMainBinding;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        StatusBarUtil.setAndroidNativeLightStatusBar(this,true);
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
        initPager();
        sendHttp();
    }

    //发送HTTP请求
    private void sendHttp(){
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Log.e(TAG, "onSuccess: " + t );
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                Log.e(TAG, "onSuccess: " + errorNo + ',' +strMsg );

            }
        };
        HttpParams params = new HttpParams();
//        params.putHeaders("Authorization", Cache.getInstance().getToken());
//        params.put("circle_id",circleId + "");
//        params.put("pasge_index",pasge_index + "");
//        params.put("pasge_size",10 + "");
        new RxVolley.Builder()
                .url("http://www.baidu.com")
                .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                .params(params)
                .shouldCache(false) //default: get true, post false
                .callback(callback)
                .encoding("UTF-8") //default
                .doTask();
    }

    public List<Fragment> fragmentList= new ArrayList<>();
    private void initPager(){
        fragmentList.add(new PlayFragment());
        fragmentList.add(new HomeFragment());
        MainAdapter adapter = new MainAdapter(this);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(2);
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.navigation.getMenu().getItem(position).setChecked(true);
                StatusBarUtil.setAndroidNativeLightStatusBar(MainActivity.this,position == 1);
                binding.navigation.setVisibility( position == 0 ? View.GONE : View.VISIBLE );
                if ( position == 0 ) {
                    if (fragmentList.size()>0)((PlayFragment)fragmentList.get(0)).isPageShow = true;
                    Jzvd.goOnPlayOnResume();
                } else{
                    if (fragmentList.size()>0)((PlayFragment)fragmentList.get(0)).isPageShow = false;
                    Jzvd.goOnPlayOnPause();
                }
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
        binding.pager.setCurrentItem(0);
    }

    /**
     *  用来控制 MainActivity的ViewPager2
     */
    public class MainAdapter extends FragmentStateAdapter {

        public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
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