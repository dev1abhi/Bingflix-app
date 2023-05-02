package com.example.bingflix


import com.example.bingflix.dataclasses.GetMoviesResponse
import retrofit2.Call //retrofit class
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "68e094699525b18a70bab2f86b1fa706",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "68e094699525b18a70bab2f86b1fa706",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>


    @GET("movie/upcoming")
        fun getUpcomingMovies(
            @Query("api_key") apiKey: String = "68e094699525b18a70bab2f86b1fa706",
            @Query("page") page: Int
        ): Call<GetMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "68e094699525b18a70bab2f86b1fa706",
        @Query("region") region: String = "IN",
        @Query("page") page: Int,
        @Query("language") language: String = "hi,en"
    ): Call<GetMoviesResponse>

}

