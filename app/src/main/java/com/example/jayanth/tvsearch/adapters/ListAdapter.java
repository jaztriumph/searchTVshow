package com.example.jayanth.tvsearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayanth.tvsearch.R;
import com.example.jayanth.tvsearch.models.Movie;
import com.example.jayanth.tvsearch.models.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jayanth on 07-11-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Movie movieInfo=null;
    List<Result> results=null;
    public ListAdapter(Movie movieInfo) {
        this.movieInfo=movieInfo;
        results=movieInfo.getResults();
    }

    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {
        holder.rowTextView.setText(results.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView rowTextView;
        ImageView rowImageView;

        public ListViewHolder(View itemView) {
            super(itemView);
            rowTextView = itemView.findViewById(R.id.row_textView);
            rowImageView = itemView.findViewById(R.id.row_imageView);
        }
    }
}
