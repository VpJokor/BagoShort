package com.selfagent.core;

import com.selfagent.core.data.Collection;
import com.selfagent.core.data.History;
import com.selfagent.core.data.ShortItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Api {
    private static List<Long> playRecommends = new ArrayList<>();
    public static List<Long> tags = new ArrayList<>();
    public static List<Long> homeRecommeds = new ArrayList<>();

    public static List<History> historys = new ArrayList<>();
    public static List<Collection> collections = new ArrayList<>();

    public static List<Long> getTags(){
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

    public static List<Long> getPlayRecommends(Set<Long> readIds){
        return playRecommends;
    }

    public static List<Long> getHomeRecommends(int index){
        return homeRecommeds;
    }

    public static List<Long> searchShort(String key){
        return null;
    }


}
