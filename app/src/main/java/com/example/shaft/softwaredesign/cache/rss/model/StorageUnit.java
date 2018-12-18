package com.example.shaft.softwaredesign.cache.rss.model;

import com.prof.rssparser.Article;

import java.util.ArrayList;

public class StorageUnit {
    private ArrayList<Article> cache;
    private HistoryUnit history;

    public StorageUnit(ArrayList<Article> rssCache, HistoryUnit history) {
        this.cache = rssCache;
        this.history = history;
    }

    public StorageUnit(){}

    public ArrayList<Article> getCache() {
        return cache;
    }

    public void setCache(ArrayList<Article> cache) {
        this.cache = cache;
    }

    public HistoryUnit getHistory() {
        return history;
    }

    public void setHistory(HistoryUnit history) {
        this.history = history;
    }
}

