package com.example.jayanth.tvsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.jayanth.tvsearch.adapters.ListAdapter;
import com.example.jayanth.tvsearch.models.Movie;
import com.example.jayanth.tvsearch.networking.ApiClient;
import com.example.jayanth.tvsearch.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter recycleAdapter;
    private ApiInterface apiInterface;
    private LinearLayoutManager layoutManager;
    private Movie movieInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.result_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Movie> call=apiInterface.getMovies("man",1);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieInfo=response.body();
                Log.v("movieInfo",response.body().getTotalResults().toString());
                if(movieInfo!=null) {
                    recycleAdapter = new ListAdapter(movieInfo);
                    recyclerView.setAdapter(recycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
