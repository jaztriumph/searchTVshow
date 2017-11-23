package com.example.jayanth.tvsearch.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.jayanth.tvsearch.activities.ListDisplay;
import com.example.jayanth.tvsearch.activities.MainActivity;
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

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public int pageNo = 1;
    public final int TOTAL_PAGES;
    private String imageUrl;
    private Movie movieInfo = null;
    public List<Result> results = null;
    private Context context = null;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Activity activity;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public ListAdapter(final RecyclerView recyclerView, Movie movieInfo, Context context) {
        this.movieInfo = movieInfo;
        results = movieInfo.getResults();
        TOTAL_PAGES = movieInfo.getTotalPages();
        this.context = context;
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return results.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
            ListViewHolder holder = new ListViewHolder(view);
            return holder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder listViewholder = (ListViewHolder) holder;
            listViewholder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent listDetailIntent = new Intent(context, ListDisplay.class);
//                    listDetailIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    listDetailIntent.putExtra("name", results.get(position).getName());
                    listDetailIntent.putExtra("poster", results.get(position).getPosterPath());

                    context.startActivity(listDetailIntent);
                }
            });
            listViewholder.rowTextView.setText(results.get(position).getName());
            if (results.get(position).getPosterPath() != null) {
                imageUrl = BASE_IMG_URL + results.get(position).getPosterPath();
                Picasso.with(context).load(imageUrl).into(listViewholder.rowImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        listViewholder.imgProgressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError() {

                    }
                });
            } else {
                listViewholder.rowImageView.setImageResource(R.mipmap.movie_img);
                listViewholder.imgProgressBar.setVisibility(View.INVISIBLE);

//            Toast.makeText(context,results.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.itemLoadingProgressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView rowTextView;
        ImageView rowImageView;
        ProgressBar imgProgressBar;
        View view;

        public ListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rowTextView = itemView.findViewById(R.id.row_textView);
            rowImageView = itemView.findViewById(R.id.row_imageView);
            imgProgressBar = itemView.findViewById(R.id.img_progressBar);
//            imgProgressBar.setVisibility(View.VISIBLE);
//            itemView.setOnClickListener(this);

        }


    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar itemLoadingProgressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            itemLoadingProgressBar = itemView.findViewById(R.id.lazy_loading_bar);
        }
    }
}
