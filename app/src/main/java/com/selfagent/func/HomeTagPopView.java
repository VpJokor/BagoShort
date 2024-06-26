package com.selfagent.func;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;


import com.selfagent.R;
import com.selfagent.core.utils.ContextUtil;
import com.selfagent.core.utils.SizeUtil;
import com.selfagent.core.widget.cover.ShowCover;
import com.selfagent.databinding.FragmentHomeTagPopBinding;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class HomeTagPopView extends FrameLayout {

    private FragmentHomeTagPopBinding binding;
    private DismissCallback disMissCallback;
    public PopupWindow popupWindow;

    public HomeTagPopView(@NonNull Context context, int index, DismissCallback callback) {
        super(context);
        binding = FragmentHomeTagPopBinding.inflate(LayoutInflater.from(context));
        disMissCallback = callback;
        List<String> datas = new ArrayList<>();
        datas.add("热门推荐");
        datas.add("王妃");
        datas.add("小人物");
        datas.add("古装");
        datas.add("时空之旅");
        datas.add("喜剧");
        datas.add("都市脑洞");
        datas.add("先婚后爱");
        HomeTagPopAdapter homeTagPopAdapter = new HomeTagPopAdapter(datas,index);
        homeTagPopAdapter.setOnItemClickListener((adapter, view, position) -> {
            int lastIndex = homeTagPopAdapter.index;
            homeTagPopAdapter.index = position;
            homeTagPopAdapter.notifyItemChanged(lastIndex);
            homeTagPopAdapter.notifyItemChanged(position);
        });
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        binding.tabs.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        binding.tabs.setAdapter(homeTagPopAdapter);
        binding.ok.setOnClickListener(view -> {
            if (disMissCallback!=null) disMissCallback.call(homeTagPopAdapter.index);
            popupWindow.dismiss();
        });
        binding.cancel.setOnClickListener(view -> {
            popupWindow.dismiss();
        });
        binding.getRoot().setOnTouchListener(new OnTouchListener() {
            private float startY; // 拖拽起始位置
            private float originalY; // 原始位置
            private int limitHeight = SizeUtil.dip2px(150);
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 记录起始位置和原始位置
                        startY = event.getRawY();
                        originalY = view.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 计算拖拽的距离
                        float deltaY = event.getRawY() - startY;
                        // 更新视图位置
                        if (deltaY>0){
                            view.setY(originalY + deltaY);
                            if ( deltaY < limitHeight ){
                                binding.indicator.setBendingRatio( deltaY / limitHeight );
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // 拖拽结束，根据最终位置判断是否关闭
                        float topY = view.getY();
                        boolean shouldDismiss = topY - limitHeight > 0;
                        if (shouldDismiss) {
                            // 关闭 PopupWindow
                            binding.indicator.setBendingRatio( 0f );
                            popupWindow.dismiss();
                        } else {
                            // 将视图恢复到原始位置
                            ValueAnimator valueAnimator = ValueAnimator.ofFloat(topY,originalY);
                            valueAnimator.setDuration(300);
                            valueAnimator.addUpdateListener(valueAnimator1 -> {
                                float itemY = (float)valueAnimator1.getAnimatedValue();
                                if ( itemY < limitHeight ) binding.indicator.setBendingRatio( itemY / limitHeight );
                                view.setY(itemY);
                            });
                            valueAnimator.start();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void show(){
        ShowCover cover = new ShowCover();
        popupWindow = new PopupWindow(binding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.MyPopWindowAnim);
        popupWindow.showAtLocation(ContextUtil.getActivityFromContextWrapper(getContext()).getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
        cover.showCover(ContextUtil.getActivityFromContextWrapper(getContext()));
        popupWindow.setOnDismissListener(() -> cover.hideCover(0));
    }


    public interface DismissCallback{
        void call(int position);
    }
}
