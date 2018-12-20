package com.example.shaft.softwaredesign.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UrlRepository {
    private static UrlRepository instance;
    private MutableLiveData<String> url = new MutableLiveData<>();

    public static UrlRepository getInstance() {
        if (instance == null){
            instance = new UrlRepository();
        }
        return instance;
    }

    private UrlRepository() {
        url.setValue("");
    }

    public void setUrl(String data){
        url.postValue(data);
    }

    public LiveData<String> getUrl(){
        return url;
    }
}
