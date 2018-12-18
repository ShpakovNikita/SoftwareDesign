package com.example.shaft.softwaredesign.cache.rss.converters;

import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.cache.rss.model.StorageUnit;
import com.example.shaft.softwaredesign.utils.DateUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.prof.rssparser.Article;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public class StorageUnitConverter implements JsonDeserializer<StorageUnit> {
    @Override
    public StorageUnit deserialize(JsonElement json, Type type,
                              JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        Date visitDate = DateUtils.parseToDate(object.get("history").getAsJsonObject().get("visitDate").getAsString());
        String link = object.get("history").getAsJsonObject().get("link").getAsString();

        HistoryUnit history = new HistoryUnit(visitDate, link);

        ArrayList<Article> rssCache = new ArrayList<>();

        JsonArray list = object.get("cache").getAsJsonArray();
        for (int i = 0; i < list.size(); ++i) {
            JsonObject item = list.get(i).getAsJsonObject();

            Article article = new Article();

            article.setImage(item.get("image").getAsString());
            article.setTitle(item.get("title").getAsString());
            article.setDescription(item.get("description").getAsString());
            article.setLink(item.get("link").getAsString());
            article.setPubDate(DateUtils.parseToDate(item.get("pubDate").getAsString()));

            rssCache.add(article);
        }

        return new StorageUnit(rssCache, history);
    }
}
