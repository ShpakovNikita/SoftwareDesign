package com.example.shaft.softwaredesign.cache.rss;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.shaft.softwaredesign.cache.rss.converters.StorageUnitConverter;
import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.cache.rss.model.StorageUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.prof.rssparser.Article;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class StorageAdapter {

    final private int DATA_CAPACITY = 10;
    final private String DATA_CACHE_FOLDER = "com.example.shaft.softwaredesign";
    final private String FILE_NAME = "cache.json";
    private static StorageAdapter instance;
    private Context context;
    private ArrayList<StorageUnit> data = new ArrayList<>();

    private StorageAdapter(Context context){
        this.context = context;
        loadData();
    }

    public static StorageAdapter getInstance(Context context){
        if (instance == null){
            instance = new StorageAdapter(context);
        }

        return instance;
    }

    public void pushData(StorageUnit parsedData){
        data.add(0, parsedData);

        if (data.size() > 10){
            data.remove(data.size() - 1);
        }
    }

    public ArrayList<StorageUnit> getAllData(){
        return data;
    }

    public ArrayList<HistoryUnit> getHistory(){
        ArrayList<HistoryUnit> history = new ArrayList<>();
        for (StorageUnit unit: data) {
            history.add(unit.getHistory());
        }

        return history;
    }

    public void flushChanges(){
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            FileWriter writer = new FileWriter(file);

            writer.append(dataToJson(data));
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void loadData(){
        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        String strData = "";

        try{
            BufferedReader reader = new BufferedReader(new FileReader(context.getFilesDir().getAbsolutePath() + File.separator + FILE_NAME));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            // delete the last new line separator
            reader.close();
            strData = stringBuilder.toString();
        }catch (Exception e){
            Log.e("err", e.getMessage());
            e.printStackTrace();
        }

        data = jsonToData(strData);
    }

    private static ArrayList<StorageUnit> jsonToData(String json){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(StorageUnit.class, new StorageUnitConverter());
        Gson gson = builder.create();
        Type type = new TypeToken<ArrayList<StorageUnit>>(){}.getType();
        ArrayList<StorageUnit> listData = gson.fromJson(json, type);

        if (listData == null)
            listData = new ArrayList<>();

        return listData;
    }

    private static String dataToJson(ArrayList<StorageUnit> data){
        String strData;
        Gson gson = new Gson();
        strData = gson.toJson(data);
        return strData;
    }

}
