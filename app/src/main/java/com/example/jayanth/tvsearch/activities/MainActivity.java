package com.example.jayanth.tvsearch.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.SearchView;
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

    private RecyclerView recyclerViewDisplay;
    private RecyclerView recyclerViewResult;
    private Movie movieInfo;
    private ListAdapter recycleAdapter;
    private ApiInterface apiInterface;
    private LinearLayoutManager layoutManagerDisplay;
    private LinearLayoutManager layoutManagerResult;
//    private EditText searchEditText;
//    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar bar = getSupportActionBar();
//        if(bar!=null)
//        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        searchEditText=findViewById(R.id.movie_search);
//        searchButton=findViewById(R.id.search_button);
        recyclerViewResult=findViewById(R.id.result_list);
        recyclerViewDisplay = findViewById(R.id.display_list);
        layoutManagerDisplay = new LinearLayoutManager(this);
        layoutManagerResult=new LinearLayoutManager(this);
        recyclerViewDisplay.setLayoutManager(layoutManagerDisplay);
        recyclerViewResult.setLayoutManager(layoutManagerResult);
        recyclerViewResult.setHasFixedSize(true);
        recyclerViewDisplay.setHasFixedSize(true);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        loadData("One",recyclerViewDisplay);
//        loadData("man");
//
//        searchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
//                if((actionId == EditorInfo.IME_ACTION_SEARCH))
//                {
//                    searchButton.performClick();
//                    return true;
//                }
//                return false;
//            }
//        });

//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void loadData(String query, final RecyclerView recyclerView) {
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

//    public void onClickSearch(View view) {
//        hideSoftKeyboard();
//        searchEditText.clearFocus();
//        String query=searchEditText.getText().toString().trim();
//        if(query.equals(""))
//        {
//            Toast.makeText(view.getContext(),"Search can't be empty",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            loadData(query);
//        }
//
//    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        final SearchView searchView= (SearchView) searchItem.getActionView();
//        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        ComponentName componentName=new ComponentName(getApplicationContext(),SearchableActivity.class);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String query=s.trim();
                if(s.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Search can't be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadData(query,recyclerViewResult);
                    recyclerViewResult.setVisibility(View.VISIBLE);
                }
                hideSoftKeyboard();
               searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                recyclerViewResult.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return true;
    }
}
