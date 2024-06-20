package com.selfagent.func;

import androidx.annotation.NonNull;

import com.selfagent.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder>  implements LoadMoreModule {
    public HomeAdapter() {
        super(R.layout.fragment_home_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String aShort) {

    }

}
