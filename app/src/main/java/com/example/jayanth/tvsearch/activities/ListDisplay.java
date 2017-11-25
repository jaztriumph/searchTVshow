package com.example.jayanth.tvsearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jayanth.tvsearch.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ListDisplay extends AppCompatActivity {
    String BASE_IMG_URL = "http://image.tmdb.org/t/p/w500/";
    ImageView detailImgView;
    TextView detailTextView;
    String intentImageUrl;
    String imageUrl;
    String name;
    ProgressBar detailImgProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display);
        detailImgView = findViewById(R.id.detail_imageView);
        detailTextView = findViewById(R.id.detail_textView);
        detailImgProgressBar = findViewById(R.id.detail_img_progressBar);
        name = getIntent().getStringExtra("name");
        intentImageUrl = getIntent().getStringExtra("poster");
        loadData();
    }

    private void loadData() {
        if (intentImageUrl != null) {
            imageUrl = BASE_IMG_URL + intentImageUrl;
            Picasso.with(this).load(imageUrl).into(detailImgView, new Callback() {
                @Override
                public void onSuccess() {
                    detailImgProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onError() {

                }
            });
        } else {
            detailImgView.setImageResource(R.mipmap.movie_img);
            detailImgProgressBar.setVisibility(View.INVISIBLE);
        }

        detailTextView.setText(name);
    }


}

