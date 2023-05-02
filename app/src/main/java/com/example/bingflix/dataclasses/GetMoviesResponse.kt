package com.example.bingflix.dataclasses

import com.example.bingflix.dataclasses.Movie
import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(@SerializedName("page") val page: Int,
                             @SerializedName("results") val movies: List<Movie>,
                             @SerializedName("total_pages") val pages: Int)
