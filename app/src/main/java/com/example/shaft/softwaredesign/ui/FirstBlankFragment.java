package com.example.shaft.softwaredesign.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.cache.rss.StorageAdapter;
import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.cache.rss.model.StorageUnit;
import com.example.shaft.softwaredesign.repository.UrlRepository;
import com.example.shaft.softwaredesign.rss.adapter.CardAdapter;
import com.example.shaft.softwaredesign.rss.adapter.CardClickListener;
import com.example.shaft.softwaredesign.rss.model.Card;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class FirstBlankFragment extends Fragment {

    public static final String URL_KEY = "url";
    RecyclerView rv_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_blank, container, false);

        rv_list = view.findViewById(R.id.list_view);
        // readData("http://www.androidcentral.com/feed");
        //
        getUrl();
        return view;
    }

    private void testStorage(ArrayList<Article> list){

        StorageUnit unit1 = new StorageUnit(list, new HistoryUnit(new Date(), "feeds.bbci.co.uk/news/rss.xml"));
        StorageUnit unit2 = new StorageUnit(list, new HistoryUnit(new Date(), "feeds.bbci.co.uk/news/rss.xml"));
        StorageUnit unit3 = new StorageUnit(list, new HistoryUnit(new Date(), "feeds.bbci.co.uk/news/rss.xml"));
        StorageUnit unit4 = new StorageUnit(list, new HistoryUnit(new Date(), "feeds.bbci.co.uk/politics/rss.xml"));

        StorageAdapter.getInstance(getActivity().getApplicationContext()).pushData(unit1);
        StorageAdapter.getInstance(getActivity().getApplicationContext()).pushData(unit2);
        StorageAdapter.getInstance(getActivity().getApplicationContext()).pushData(unit3);
        StorageAdapter.getInstance(getActivity().getApplicationContext()).pushData(unit4);

        StorageAdapter.getInstance(getActivity().getApplicationContext()).flushChanges();
    }

    private void readData(String urlString){
        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                CardAdapter adapter = new CardAdapter(getActivity(), list, (View v, String url)->{
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    Bundle b = new Bundle();
                    b.putString(URL_KEY, url);
                    intent.putExtras(b);
                    startActivity(intent);
                });
                rv_list.setAdapter(adapter);
                rv_list.setLayoutManager(
                        new LinearLayoutManager(getContext(),
                                RecyclerView.VERTICAL,
                                false));

                testStorage(list);

            }

            @Override
            public void onError(Exception e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Unable to load data",
                                Toast.LENGTH_LONG).show();
                        Log.e("Unable to load ", e.getMessage());
                    }
                });
            }
        });
    }

    private void getUrl(){
        UrlRepository.getInstance().getUrl().observe(this, (url)->{
            if (url != ""){
                if (url.startsWith("https://") || url.startsWith("http://")){
                    readData(url);
                }
                else{
                    String resUrl = "https://" + url;
                    readData(resUrl);
                }
            }
            else{

            }
        });
    }

}
