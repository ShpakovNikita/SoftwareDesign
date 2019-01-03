package com.example.shaft.softwaredesign.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.cache.rss.StorageAdapter;
import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.cache.rss.model.StorageUnit;
import com.example.shaft.softwaredesign.repository.UrlRepository;
import com.example.shaft.softwaredesign.rss.adapter.CardAdapter;
import com.example.shaft.softwaredesign.utils.NetworkUtils;
import com.example.shaft.softwaredesign.utils.UrlUtils;
import com.google.android.gms.common.util.Strings;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.Date;


public class RssViewFragment extends Fragment {

    public static final String URL_KEY = "url";
    RecyclerView rvList;
    SwipeRefreshLayout swipeRefreshLayout;
    CardAdapter adapter;
    ProgressBar bar;
    private String currentUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rss_view, container, false);

        /* To restrict Space Bar in Keyboard */
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
        };
        ((EditText) view.findViewById(R.id.editText)).setFilters(new InputFilter[] { filter });
        rvList = view.findViewById(R.id.list_view);
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
        getUrl();
        setAdapter(new ArrayList<>());

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener((v)->{
            EditText editText = (EditText) view.findViewById(R.id.editText);
            String url = editText.getText().toString();
            UrlRepository.getInstance().setUrl(UrlUtils.GetUrl(url));
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.container);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.canChildScrollUp();
        swipeRefreshLayout.setOnRefreshListener(()->{
                adapter.clearData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(true);
                readDataFromNetwork(UrlRepository.getInstance().getUrl().getValue());
        });

        return view;
    }

    private void setAdapter(ArrayList<Article> list){
        adapter = new CardAdapter(getActivity(), list, (View v, String url)->{
            Intent intent = new Intent(getActivity(), WebActivity.class);
            Bundle b = new Bundle();
            b.putString(URL_KEY, url);
            intent.putExtras(b);
            startActivity(intent);
        });
        rvList.setAdapter(adapter);
        if (getResources().getBoolean(R.bool.isTablet)){
            rvList.setLayoutManager(
                    new GridLayoutManager(getContext(),
                            2,
                            RecyclerView.VERTICAL,
                            false));
        }else{
            rvList.setLayoutManager(
                    new LinearLayoutManager(getContext(),
                            RecyclerView.VERTICAL,
                            false));
        }

    }

    private void readDataFromNetwork(String urlString){
        if (!NetworkUtils.isNetworkConnected(getActivity().getApplicationContext())){
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Unable to connect!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!swipeRefreshLayout.isRefreshing())
            bar.setVisibility(View.VISIBLE);

        if (Strings.isEmptyOrWhitespace(urlString)) {
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        Parser parser = new Parser();
        parser.execute(UrlUtils.GetUrl(urlString));
        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                setAdapter(list);

                HistoryUnit historyUnit = new HistoryUnit(new Date(), currentUrl);
                StorageUnit storageUnit = new StorageUnit(list, historyUnit);
                StorageAdapter.getInstance(
                        getActivity().getApplicationContext()).pushData(storageUnit);
                bar.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Exception e) {

                getActivity().runOnUiThread(()->{
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Unable to load data",
                                Toast.LENGTH_LONG).show();
                        Log.e("Unable to load ", e.getMessage());
                        bar.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                });
            }
        });
    }

    private void getUrl(){
        UrlRepository.getInstance().getUrl().observe(this, (url)->{
            if (UrlRepository.getInstance().isChanged()){
                ArrayList<StorageUnit> list = StorageAdapter.getInstance(
                        getActivity().getApplicationContext()).getAllData();

                setAdapter(list.get(UrlRepository.getInstance().getPos()).getCache());

                UrlRepository.getInstance().resetPos();
                currentUrl = url;
            }
            else {
                currentUrl = url;
                if (!Strings.isEmptyOrWhitespace(url)) {
                    readDataFromNetwork(url);
                }
            }
        });
    }

}
