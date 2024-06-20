package com.selfagent.func.manage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.selfagent.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class EditItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public EditItemAdapter(@Nullable List<String> data) {
        super(R.layout.activity_publish_short_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String item) {

    }

}
