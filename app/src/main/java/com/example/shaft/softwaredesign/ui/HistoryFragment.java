package com.example.shaft.softwaredesign.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.cache.rss.StorageAdapter;
import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.repository.UrlRepository;
import com.example.shaft.softwaredesign.rss.adapter.HistoryAdapter;
import com.example.shaft.softwaredesign.utils.UrlUtils;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    RecyclerView rvList;
    ArrayList<HistoryUnit> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cache, container, false);

        rvList = view.findViewById(R.id.list_view);

        readData();

        if (!data.isEmpty()){
            TextView tv = (TextView) view.findViewById(R.id.cache_tv);
            tv.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void readData(){
        data = StorageAdapter.getInstance(getActivity().getApplicationContext()).getHistory();

        HistoryAdapter adapter = new HistoryAdapter(getActivity(), data, (url, pos) -> {
            UrlRepository.getInstance().setUrl(UrlUtils.GetUrl(url));
            UrlRepository.getInstance().setPos(pos);
            NavController navController =
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_secondBlankFragment_to_firstBlankFragment);
        });
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL,
                        false));
    }
}
