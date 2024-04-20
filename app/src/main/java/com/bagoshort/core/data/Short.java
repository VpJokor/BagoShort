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
    //分类
    public int tagId;
    public int tagName;
    //共xx集
    public int itemCount;
    //阅读次数，提前生成假数据
    public int readCount;
    public long createTime;
}
