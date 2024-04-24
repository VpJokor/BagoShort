package com.bagoshort.func.manage;

import com.bagoshort.core.data.Server;
import com.bagoshort.core.data.Short;
import com.bagoshort.core.data.ShortItem;
import com.bagoshort.core.data.Version;

import java.util.ArrayList;
import java.util.List;

public class Publish {
    public Server server;
    public List<EditShort> editShorts = new ArrayList<>();
    public int currentEdit;
}
