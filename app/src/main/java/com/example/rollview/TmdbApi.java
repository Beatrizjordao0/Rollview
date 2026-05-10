package com.example.rollview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// A palavra 'interface' é obrigatória aqui, em vez de 'class'
public interface TmdbApi {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}