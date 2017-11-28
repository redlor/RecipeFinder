package com.example.android.recipefinder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hp on 23/09/2017.
 */

public interface ApiInterface {
    // Customize the Url request
    @GET("search")
    Call<RecipeResponse> getRepo(@Query("key") String key,
                                 @Query("q") String passedTitle

    );


}

