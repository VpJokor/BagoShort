package com.bagoshort.core.data;

/**
 * 短剧Bean
 */
public class Short {
    //id 仅本地维护
    public long id;
    //封面
    public String cover;
    //名字
    public String name;
    //简介
    public String desc;
    //评分 1-5 随机生成
    public float grad;
    //分类
    public long tagId;
    public String tagName;
    //共xx集
    public int itemCount;
    //阅读次数，提前生成假数据(随机生成)
    public long readCount;
    public long versionId;
    public boolean isDelete;
}
