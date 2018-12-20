package com.example.shaft.softwaredesign.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UrlRepository {
    private static UrlRepository instance;
    private MutableLiveData<String> url = new MutableLiveData<>();
    private int pos = -1;

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

    public void resetPos(){
        pos = -1;
    }

    public void setPos(int intPost){
        pos = intPost;
    }

    public boolean isChanged(){
        if (pos != -1) {
            return true;
        }else{
            return false;
        }

    }

    public int getPos(){
        return pos;
    }
}
