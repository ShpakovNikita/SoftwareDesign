package com.example.shaft.softwaredesign.rss.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.rss.model.Card;
import com.example.shaft.softwaredesign.ui.WebActivity;
import com.example.shaft.softwaredesign.utils.DateUtils;
import com.prof.rssparser.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private List<Article> data;
    private CardClickListener onClickBinding;

    public CardAdapter(Context context, List<Article> data, CardClickListener listener) {
        this.context = context;
        this.data = data;
        this.onClickBinding = listener;
    }

    public void clearData() {
        if (data != null)
            data.clear();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_layout, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.card_description.setText(data.get(position).getTitle());
        holder.title.setText(data.get(position).getTitle());

        holder.date.setText(DateUtils.parseToString(data.get(position).getPubDate()));

        holder.card_image.setOnClickListener((v) -> {onClickBinding.onCardClicked(v, data.get(position).getLink());});

        Picasso.get()
                .load(data.get(position).getImage())
                .placeholder(R.drawable.pic)
                .into(holder.card_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Animation fadeOut = new AlphaAnimation(0, 1);
                        fadeOut.setInterpolator(new LinearInterpolator());
                        fadeOut.setDuration(500);
                        holder.card_image.startAnimation(fadeOut);
                    }

                    @Override
                    public void onError(Exception e) {
                        // No error interception
                    }
                });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        ImageView card_image;
        TextView card_description;
        TextView date;
        TextView title;

        public CardViewHolder(View itemView){
            super(itemView);

            card_image = itemView.findViewById(R.id.card_image);
            card_description = itemView.findViewById(R.id.card_description);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
        }

    }
}
