package com.example.shaft.softwaredesign.rss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.cache.rss.model.HistoryUnit;
import com.example.shaft.softwaredesign.utils.DateUtils;
import com.prof.rssparser.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CardViewHolder> {

    private Context context;
    private List<HistoryUnit> data;

    public HistoryAdapter(Context context, List<HistoryUnit> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.history_layout, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.link.setText(data.get(position).getLink());
        holder.date.setText(DateUtils.parseToString(data.get(position).getVisitDate()));
        holder.root.setOnClickListener((v) -> {
            Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        TextView link;
        TextView date;
        CardView root;

        public CardViewHolder(View itemView){
            super(itemView);

            date = itemView.findViewById(R.id.pub_date);
            link = itemView.findViewById(R.id.link);
            root = itemView.findViewById(R.id.parent_root);
        }

    }
}
