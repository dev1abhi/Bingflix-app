package com.example.bingflix

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bingflix.dataclasses.Movie


class SecondActivity:AppCompatActivity() {
    private lateinit var popularMovies: RecyclerView  //type
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private var popularMoviesPage = 1



    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private var topRatedMoviesPage = 1

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    private var upcomingMoviesPage = 1

    private lateinit var nowPlaying: RecyclerView
    private lateinit var nowPlayingAdapter: Movies2Adapter
    private lateinit var nowPlayingLayoutMgr: LinearLayoutManager
    private var nowPlayingPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bundle: Bundle? = intent.extras
        val msg = bundle!!.getString("user_message")   //msg is storing the string data from main activity
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        //adapter calling for popular movies
        popularMovies = findViewById(R.id.popular_movies)  // id of recycler view
        popularMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf()){ movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter



        //adapter calling for top rated movies
        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()){ movie -> showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter



        //adapter calling for upcoming movies
        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf()){ movie -> showMovieDetails(movie) }
        upcomingMovies.adapter = upcomingMoviesAdapter


        nowPlaying = findViewById(R.id.Now_playing)
        nowPlayingLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        nowPlaying.layoutManager = nowPlayingLayoutMgr
        nowPlayingAdapter = Movies2Adapter(mutableListOf()){ movie -> showMovieDetails(movie) }
        nowPlaying.adapter = nowPlayingAdapter  //Recyclerview.adaptername

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()


        MoviesRepository.getNowPlayingMovies(
            onSuccess = ::onNowPlayingMoviesFetched,
            onError = ::onError
        )

    }


    private fun onNowPlayingMoviesFetched(movies: List<Movie>) {
        nowPlayingAdapter.appendMovies(movies)
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MoviesDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

        private fun getPopularMovies() {
            MoviesRepository.getPopularMovies(
                popularMoviesPage,                      //popularmoviespage = 1 then 2 , one by one sending for next pagenumbers
                onSuccess = ::onPopularMoviesFetched,  // :: means class/function reference
                onError = ::onError
            )
        }


    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)   //populating the adapter with movies
        attachPopularMoviesOnScrollListener()
    }


    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                // the total number of movies inside our popularMoviesAdapter.
                // This will keep increasing the more we call popularMoviesAdapter.appendMovies().

                val visibleItemCount = popularMoviesLayoutMgr.childCount
                // the current number of child views attached to the RecyclerView that are currently being recycled over and over again.
                // The value of this variable for common screen sizes will range roughly around 4-5 which are 3 visible views,
                // +1 left view that’s not seen yet and +1 right view that’s not seen yet also.
                // The value will be higher if you have a bigger screen.

                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()
                //is the position of the leftmost visible item in our list.

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }



    private fun getTopRatedMovies() {
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }



    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}

