package com.example.bingflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.bingflix.dataclasses.Movie

class Movies2Adapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit

) : RecyclerView.Adapter<Movies2Adapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.banner, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.npbanner)

        fun bind(movie: Movie) {
            Glide.with(itemView)  //Glide is a quick and productive image loading library for Android focused on smooth scrolling.
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener {
                onMovieClick.invoke(movie)
            }
        }
    }
}