package com.example.android.recipefinder;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class RecipeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchManager searchManager;
    private ImageView mEmptyStateImageView;

    // Hide the soft keyboard when clicking the search button
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // Hiding the image view since the two activities share the same layout
        ImageView mEmptyStateImageView = (ImageView) findViewById(R.id.empty_image_view);
        mEmptyStateImageView.setVisibility(View.GONE);

        // passing the value inserted in the Search Bar to the other activity
        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideSoftKeyboard(RecipeActivity.this);
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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Hide the progress Bar when the activity is closed
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
