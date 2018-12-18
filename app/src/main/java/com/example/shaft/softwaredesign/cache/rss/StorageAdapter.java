package com.example.shaft.softwaredesign.cache.rss;

import android.content.Context;
import android.net.Uri;

import com.example.shaft.softwaredesign.cache.rss.converters.StorageUnitConverter;
import com.example.shaft.softwaredesign.cache.rss.model.StorageUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.prof.rssparser.Article;

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
    private ArrayList<StorageUnit> data;

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
        data.add(parsedData);

        if (data.size() > 10){
            data.remove(0);
        }
    }

    public ArrayList<StorageUnit> getAllData(){
        return data;
    }

    public void flushChanges(){
        File file = new File(context.getFilesDir(),DATA_CACHE_FOLDER);
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, FILE_NAME);
            FileWriter writer = new FileWriter(gpxfile);

            writer.append(dataToJson(data));
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void loadData(){
        File file = new File(context.getFilesDir(),DATA_CACHE_FOLDER);
        if(!file.exists()){
            file.mkdir();
        }

        String strData = "";

        try{
            File gpxfile = new File(file, FILE_NAME);
            FileReader reader = new FileReader(gpxfile);
            StringBuilder fileContents = new StringBuilder((int)file.length());

            try (Scanner scanner = new Scanner(file)) {
                while(scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine() + System.lineSeparator());
                }

                strData = fileContents.toString();
                data = jsonToData(strData);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<StorageUnit> jsonToData(String json){
        ArrayList<StorageUnit> listData;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(StorageUnit.class, new StorageUnitConverter());
        Gson gson = builder.create();
        Type type = new TypeToken<ArrayList<StorageUnit>>(){}.getType();
        listData = gson.fromJson(json, type);
        return listData;
    }

    private static String dataToJson(ArrayList<StorageUnit> data){
        String strData;
        Gson gson = new Gson();
        strData = gson.toJson(data);
        return strData;
    }

}
