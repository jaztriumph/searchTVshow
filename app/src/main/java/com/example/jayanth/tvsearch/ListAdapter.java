package com.example.jayanth.tvsearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayanth.tvsearch.R;

import java.util.ArrayList;

import com.example.jayanth.tvsearch.models.MovieInfo;

/**
 * Created by jayanth on 07-11-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {


    ListAdapter(ArrayList<MovieInfo> results) {

    }

    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
