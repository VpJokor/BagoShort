package com.selfagent.func.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.selfagent.core.utils.PreferenceUtil;
import com.selfagent.core.utils.SizeUtil;
import com.selfagent.core.utils.StatusBarUtil;
import com.selfagent.databinding.ActivityPublishBinding;
import com.selfagent.databinding.ActivityPublishHeaderBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends AppCompatActivity {

    //1. 短剧发布
    //1.1 短剧封面
    //1.2 短剧名称
    //1.3 短剧简介
    //1.4 短剧评分（默认随机，可手动修改）
    //1.5 分类选择
    //1.6 观看次数（随机生成）
    //1.7 短剧内容上传

    //2. 短剧列表
    //2.1 短剧删除

    //3. 短剧详情
    //3.1 剧集顺序修改
    //3.2 剧集删除
    //3.3 剧集上传

    //4. 短剧编辑
    //4.1 名字修改
    //4.2 封面修改
    //4.3 简介修改
    //4.4 评分修改
    //4.5 分类修改

    private ActivityPublishBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        readDraftFromCache();
        headerBinding = ActivityPublishHeaderBinding.inflate(getLayoutInflater());
        initToolBar();
        initEditList();
        initEditShort();
    }

    private void initToolBar(){
        StatusBarUtil.setAndroidNativeLightStatusBar(this,true);
        binding.toolbar.setTitle("");
        binding.appBarLayout.setPadding(0, SizeUtil.getStatusBarHeight(this),0,0);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        binding.back.setOnClickListener(view -> finish());
        binding.publish.setOnClickListener(view -> publish());
    }

    //编辑的短剧列表
    private void initEditList(){
        EditListAdapter editListAdapter = new EditListAdapter(draft.editShorts);
        binding.editList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.editList.setAdapter(editListAdapter);
        editListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            editListAdapter.notifyItemChanged(draft.currentEdit);
            draft.currentEdit = position;
            editListAdapter.notifyItemChanged(draft.currentEdit);
            binding.editList.scrollToPosition(draft.currentEdit);
            editItemAdapter.setList(draft.editShorts.get(draft.currentEdit).items);
            setInput(draft.editShorts.get(draft.currentEdit));
        });
    }

    ActivityPublishHeaderBinding headerBinding;
    private void setInput(EditShort data){

    }

    //正在编辑的短剧
    EditItemAdapter editItemAdapter;
    private void initEditShort(){
        editItemAdapter = new EditItemAdapter(draft.editShorts.get(draft.currentEdit).items);
        editItemAdapter.addHeaderView(headerBinding.getRoot());
        binding.items.setLayoutManager(new GridLayoutManager(this,3));
        binding.items.setAdapter(editItemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readDraftFromCache();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDraft();
    }

    private Publish draft;
    public void readDraftFromCache(){
        String strPublish = PreferenceUtil.getString("draft","");
        if (strPublish.equals("")) {
            draft = new Publish();
            draft.editShorts = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                EditShort item = new EditShort();
                List<String> items = new ArrayList<>();
                for (int j = 0; j < 30; j++) {
                    items.add(""+j);
                }
                item.items = items;
                draft.editShorts.add(item);
            }
        }
    }

    public void saveDraft(){
        PreferenceUtil.commitString("draft",new Gson().toJson(draft));
    }

    public void publish(){

    }
}