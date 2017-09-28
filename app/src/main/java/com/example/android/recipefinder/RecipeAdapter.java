package com.example.android.recipefinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    Context mContext;
    private List<Recipe> mRecipe;
    private OnItemClickListener listener;
    private int rowLayout;


    public RecipeAdapter(List<Recipe> recipes, int rowLayout, Context context, OnItemClickListener listener) {
        this.mRecipe = recipes;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder viewHolder, int position) {
        final Recipe currentRecipe = mRecipe.get(position);

        // Populate the ViewHolder
        viewHolder.title.setText(currentRecipe.getTitle());
        String formattedRating = currentRecipe.getRanking();
        String cutString = formattedRating.substring(0, 5);
        viewHolder.ranking.setText("Rating: " +  cutString);
        Picasso.with(mContext).load(currentRecipe.getImage()).into(viewHolder.image);

        viewHolder.bind(mRecipe.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    // Create an onItemClickListener for the RecyclerView%2d
    interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    // Create the custom ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_text_view)
        TextView title;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.ranking_text_view)
        TextView ranking;

        public ViewHolder(View listItemView) {
            super(listItemView);
            ButterKnife.bind(this, listItemView);
        }

        // Bind the onItemClickListener to the RecyclerView
        void bind(final Recipe recipe, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(recipe);
                }
            });
        }
    }
}