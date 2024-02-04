package com.demo.khushi.MovieDetails.repository
import com.demo.khushi.interfaces.MovieApi
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getMovieDetails(movieId: Int) =
        movieApi.getMovieDetails(movieId)
}
