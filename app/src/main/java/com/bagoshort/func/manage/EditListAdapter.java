package com.bagoshort.func.manage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bagoshort.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class EditListAdapter extends BaseQuickAdapter<EditShort, BaseViewHolder> {
    public EditListAdapter(@Nullable List<EditShort> data) {
        super(R.layout.activity_publish_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, EditShort item) {

    }

}
