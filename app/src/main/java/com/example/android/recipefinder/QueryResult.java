package com.example.android.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 23/09/2017.
 */

public class QueryResult extends AppCompatActivity {
    private static String LOG_TAG = QueryResult.class.getSimpleName();
    private static String API_KEY = "3a27c4a2d45265c07efff4a8a8a6d00e";
    String passedTitle = "";
    ProgressBar progressBar;
    private TextView mEmptyStateTextView;
    private ImageView mEmptyStateImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

        passedTitle = getIntent().getStringExtra("Title");

        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.VISIBLE);

        ImageView recipeImage = (ImageView) findViewById(R.id.recipe);
        recipeImage.setVisibility(View.GONE);

        //Set the empty state View
        mEmptyStateImageView = (ImageView) findViewById(R.id.empty_image_view);
        mEmptyStateImageView.setVisibility(View.GONE);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateTextView.setVisibility(View.GONE);

        // Restarts the activity when a anew search is made
        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = searchView.getQuery().toString();
                Intent i = new Intent(getApplicationContext(), QueryResult.class);
                i.putExtra("Title", text);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        // Calls the Interface for handling the custom request
        Call<RecipeResponse> call = apiService.getRepo(API_KEY, passedTitle);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                int statusCode = response.code();
                progressBar.setVisibility(View.GONE);
                List<Recipe> recipes = response.body().getRecipes();
                Log.d(LOG_TAG, "Number of recipes received: " + recipes.size());

                // If a list of Recipes has been received, set the onClickListener on each item
                if (recipes != null && !recipes.isEmpty()) {
                    recyclerView.setAdapter(new RecipeAdapter(recipes, R.layout.recipe_list_item, getApplicationContext(), new RecipeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Recipe recipe) {
                            Uri recipeUri = Uri.parse(recipe.getUrl());
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, recipeUri);
                            if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(websiteIntent);
                            }
                        }
                    }));
                    // If not, informs the user that no recipes have been found
                } else {
                    mEmptyStateImageView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.no_recipes);
                }
            }

            // Handles the case when there is not internet connection
            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(LOG_TAG, t.toString());
                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    mEmptyStateTextView.setVisibility(View.GONE);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    progressBar.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });

    }

}


