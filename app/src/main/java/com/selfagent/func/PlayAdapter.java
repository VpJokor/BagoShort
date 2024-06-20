package com.selfagent.func;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;

import com.selfagent.R;
import com.selfagent.core.utils.ShowUtil;
import com.selfagent.core.widget.like.LikeView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import com.selfagent.core.widget.player.JzvdStdTikTok;

public class PlayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public boolean showMenu ;

    public PlayAdapter(boolean isShow) {
        super(R.layout.fragment_play_item);
        showMenu = isShow;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.getView(R.id.left_layout).setVisibility( showMenu ? View.VISIBLE :View.GONE );
        baseViewHolder.getView(R.id.btm_layout).setVisibility( showMenu ? View.VISIBLE :View.GONE );
        setVideo(baseViewHolder,"http://150.158.90.84:9000/article.video/d4f59d53c967a71c94b1735c514f3ec0.mp4","");
        setOptions(baseViewHolder,s);
    }

    private void setVideo(@NotNull BaseViewHolder baseViewHolder, String videoUrl , String cover){
        JzvdStdTikTok videoplayer = baseViewHolder.getView(R.id.videoplayer);
//        if (cover == null || cover.trim().equals("")) cover = BaseApplication.getProxy(getContext()).getProxyUrl(MinioUtil.getInstance().getUrl(articleBean.title));
        Glide.with(getContext())
                .load(cover)
                .optionalCenterCrop()
                .into(videoplayer.posterImageView);
        if (videoUrl!=null){
            JZDataSource jzDataSource = new JZDataSource(videoUrl,"");
            //            JZDataSource jzDataSource = new JZDataSource(BaseApplication.getProxy(getContext()).getProxyUrl(videoUrl),"");
            jzDataSource.looping = true;
            videoplayer.setUp(jzDataSource, Jzvd.SCREEN_NORMAL);
        }
    }

    private void setOptions(@NonNull BaseViewHolder baseViewHolder, String s){
        baseViewHolder.findView(R.id.share).setOnClickListener(view -> {
            PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.6f, 1.0f);
            PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.6f, 1.0f);
            ObjectAnimator clickAnimator = ObjectAnimator.ofPropertyValuesHolder(view, holderX, holderY).setDuration(500);
            clickAnimator.setInterpolator(new OvershootInterpolator());
            clickAnimator.start();
            ShowUtil.showToast(getContext(),"分享");
        });
        LikeView collection = baseViewHolder.getView(R.id.collection);
        collection.setOnClickListener(view -> collection.toggle());
    }
}
