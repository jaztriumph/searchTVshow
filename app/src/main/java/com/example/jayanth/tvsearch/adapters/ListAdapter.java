package com.example.jayanth.tvsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayanth.tvsearch.R;
import com.example.jayanth.tvsearch.models.Movie;
import com.example.jayanth.tvsearch.models.Result;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

/**
 * Created by jayanth on 07-11-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";

    String imageUrl;
    Movie movieInfo = null;
    List<Result> results = null;
    Context context = null;

    public ListAdapter(Movie movieInfo, Context context) {
        this.movieInfo = movieInfo;
        results = movieInfo.getResults();
        this.context = context;
    }

    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ListViewHolder holder, int position) {

        holder.rowTextView.setText(results.get(position).getName());
        if (results.get(position).getPosterPath() != null) {
            imageUrl = BASE_IMG_URL + results.get(position).getPosterPath();
            Picasso.with(context).load(imageUrl).into(holder.rowImageView, new Callback() {
                @Override
                public void onSuccess() {
                    holder.imgProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onError() {

                }
            });
        } else {
            holder.rowImageView.setImageResource(R.mipmap.movie_img);
            holder.imgProgressBar.setVisibility(View.INVISIBLE);

//            Toast.makeText(context,results.get(position).getName(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView rowTextView;
        ImageView rowImageView;
        ProgressBar imgProgressBar;

        public ListViewHolder(View itemView) {
            super(itemView);
            rowTextView = itemView.findViewById(R.id.row_textView);
            rowImageView = itemView.findViewById(R.id.row_imageView);
            imgProgressBar = itemView.findViewById(R.id.img_progressBar);
//            imgProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
