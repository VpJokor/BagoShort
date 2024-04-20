package com.bagoshort.core;

import com.bagoshort.core.data.Collection;
import com.bagoshort.core.data.History;
import com.bagoshort.core.data.RecommendItem;
import com.bagoshort.core.data.ShortItem;
import com.bagoshort.core.data.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Api {
    private static List<RecommendItem> playRecommends = new ArrayList<>();
    public static List<Tag> tags = new ArrayList<>();
    public static List<Short> homeRecommeds = new ArrayList<>();

    public static List<History> historys = new ArrayList<>();
    public static List<Collection> collections = new ArrayList<>();

    public static List<Tag> getTags(){
        return tags;
    };

    public static List<History> getHistorys(int index){
        return historys;
    }

    public static List<Collection> getCollections(int index){
        return collections;
    }

    public static List<Short> getShorts(){
        return getShorts(0,0);
    }

    public static List<Short> getShorts(int index){
        return getShorts(index,0);
    }

    public static List<Short> getShorts(int index,int tag){
        return null;
    }

    public static List<ShortItem> getShortItems(int shortId){
        return null;
    }

    public static List<RecommendItem> getPlayRecommends(Set<Long> readIds){
        return playRecommends;
    }

    public static List<Short> getHomeRecommends(int index){
        return homeRecommeds;
    }

    public static List<RecommendItem> searchShort(String key){
        return null;
    }

}
