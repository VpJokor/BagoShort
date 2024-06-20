package com.selfagent.func;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.selfagent.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class HomeTagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public int index = 0;

    public HomeTagAdapter(@Nullable List<String> data) {
        super(R.layout.fragment_home_tag_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String item) {
        TextView label = baseViewHolder.getView(R.id.label);
        label.setText(item);
        if ( getItemPosition(item) == index ){
            label.setTextSize(14.5f);
            label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            label.setTextColor(getContext().getColor(R.color.black));
        }else {
            label.setTextSize(14f);
            label.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            label.setTextColor(getContext().getColor(R.color.color_grey_900));
        }
    }

}
