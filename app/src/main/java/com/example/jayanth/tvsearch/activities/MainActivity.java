package com.example.jayanth.tvsearch.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayanth.tvsearch.R;
import com.example.jayanth.tvsearch.adapters.ListAdapter;
import com.example.jayanth.tvsearch.models.Movie;
import com.example.jayanth.tvsearch.networking.ApiClient;
import com.example.jayanth.tvsearch.networking.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDisplay;
    private RecyclerView recyclerViewResult;
    private Movie movieInfo;
    Movie loadMovie = null;
    private ListAdapter recycleAdapter;
    private ApiInterface apiInterface;
    private LinearLayoutManager layoutManagerDisplay;
    private LinearLayoutManager layoutManagerResult;
    ProgressBar progressBarDisplay;
    ProgressBar progressBarResult;
    FrameLayout frameLayoutResult;
    Context context;
    Button reload;
    TextView typeSearch;

//    private EditText searchEditText;
//    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;
        reload = findViewById(R.id.reload_button);
        frameLayoutResult = findViewById(R.id.result_frame);
        typeSearch = findViewById(R.id.type_search);
        progressBarDisplay = findViewById(R.id.display_progressbar);
        progressBarResult = findViewById(R.id.result_progressbar);
        recyclerViewResult = findViewById(R.id.result_list);
        recyclerViewDisplay = findViewById(R.id.display_list);
        layoutManagerDisplay = new LinearLayoutManager(this);
        layoutManagerResult = new LinearLayoutManager(this);
        recyclerViewDisplay.setLayoutManager(layoutManagerDisplay);
        recyclerViewResult.setLayoutManager(layoutManagerResult);
        recyclerViewResult.setHasFixedSize(true);
        recyclerViewDisplay.setHasFixedSize(true);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        loadData("One", recyclerViewDisplay, true, 1, "movie");
    }

    public void loadData(final String query, final RecyclerView recyclerView, final boolean onload, int page, final String type) {
        Call<Movie> call = null;
        if (type.equals("tv")) {
            call = apiInterface.getTv(query, page);
        } else {
            call = apiInterface.getPopularTv(page);
        }
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieInfo = response.body();
                loadMovie = movieInfo;
                if (onload) {
                    if (movieInfo != null) {
                        progressBarDisplay.setVisibility(View.GONE);
                        progressBarResult.setVisibility(View.INVISIBLE);
                        recycleAdapter = new ListAdapter(recyclerView, movieInfo, context);
                        recyclerView.setAdapter(recycleAdapter);
                        recycleAdapter.setOnLoadMoreListener(new ListAdapter.OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                recycleAdapter.pageNo++;
                                if (recycleAdapter.pageNo < recycleAdapter.TOTAL_PAGES) {
//                                    Toast.makeText(getApplicationContext(), String.valueOf(recycleAdapter.pageNo), Toast.LENGTH_SHORT).show();

                                    recycleAdapter.results.add(null);
                                    recycleAdapter.notifyItemInserted(recycleAdapter.results.size() - 1);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadData(query, recyclerView, false, recycleAdapter.pageNo, type);
                                            recycleAdapter.results.remove(recycleAdapter.results.size() - 1);
                                            if (loadMovie != null) {
                                                recycleAdapter.results.addAll(loadMovie.getResults());
                                                recycleAdapter.notifyDataSetChanged();
                                                recycleAdapter.setLoaded();

                                            }
                                        }
                                    }, 0);

                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                progressBarDisplay.setVisibility(View.INVISIBLE);
                progressBarResult.setVisibility(View.INVISIBLE);
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "NO internet connectivity", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
//        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        ComponentName componentName=new ComponentName(getApplicationContext(),SearchableActivity.class);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                hideSoftKeyboard();
                searchView.clearFocus();
                String query = s.trim();
                if (s.equals("")) {
                    Toast.makeText(getApplicationContext(), "Search can't be empty", Toast.LENGTH_SHORT).show();
                } else {

//                    recyclerViewResult.setVisibility(View.VISIBLE);
                    frameLayoutResult.setVisibility(View.VISIBLE);
                    progressBarResult.setVisibility(View.VISIBLE);
                    typeSearch.setText("Search results...");
                    loadData(query, recyclerViewResult, true, 1, "tv");
                }


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
                frameLayoutResult.setVisibility(View.INVISIBLE);
                typeSearch.setText("Top shows");
//                recyclerViewResult.setVisibility(View.INVISIBLE);

                return true;
            }
        });
        return true;
    }

    public void reload(View view) {
        reload.setVisibility(View.INVISIBLE);
        loadData("One", recyclerViewDisplay, true, 1, "movie");

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
