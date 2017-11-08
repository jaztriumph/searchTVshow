package com.example.jayanth.tvsearch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayanth.tvsearch.R;
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
    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText=findViewById(R.id.movie_search);
        searchButton=findViewById(R.id.search_button);

        searchButton=findViewById(R.id.search_button);
        recyclerView = findViewById(R.id.result_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        loadData("One");
//        loadData("man");

        searchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if((actionId == EditorInfo.IME_ACTION_SEARCH))
                {
                    searchButton.performClick();
                    return true;
                }
                return false;
            }
        });

//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void loadData(String query) {
        Call<Movie> call = apiInterface.getMovies(query, 1);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieInfo = response.body();
                Log.v("movieInfo", response.body().getTotalResults().toString());
                if (movieInfo != null) {
                    recycleAdapter = new ListAdapter(movieInfo,getApplicationContext());
                    recyclerView.setAdapter(recycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickSearch(View view) {
        hideSoftKeyboard();
        searchEditText.clearFocus();
        String query=searchEditText.getText().toString().trim();
        if(query.equals(""))
        {
            Toast.makeText(view.getContext(),"Search can't be empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadData(query);
        }

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



}
