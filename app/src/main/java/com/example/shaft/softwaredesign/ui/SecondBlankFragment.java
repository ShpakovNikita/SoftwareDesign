package com.example.shaft.softwaredesign.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.repository.UrlRepository;
import com.example.shaft.softwaredesign.rss.adapter.CardAdapter;
import com.example.shaft.softwaredesign.rss.adapter.HistoryAdapter;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.Date;

public class SecondBlankFragment extends Fragment {

    RecyclerView rv_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_second_blank, container, false);

        rv_list = view.findViewById(R.id.list_view);

        readData();
        return view;
    }

    private void readData(){
        ArrayList<HistoryUnit> list = new ArrayList<>();

        list.add(new HistoryUnit(new Date(), "feeds.bbci.co.uk/news/rss.xml"));
        list.add(new HistoryUnit(new Date(), "http://www.androidcentral.com/feed"));
        list.add(new HistoryUnit(new Date(), "http://feeds.bbci.co.uk/news/politics/rss.xml"));
        list.add(new HistoryUnit(new Date(), "feeds.bbci.co.uk/news/rss.xml"));

        HistoryAdapter adapter = new HistoryAdapter(getActivity(), list, (url) -> {
            UrlRepository.getInstance().setUrl(url);

            NavController navController =
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_secondBlankFragment_to_firstBlankFragment);
        });
        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL,
                        false));
    }
}
