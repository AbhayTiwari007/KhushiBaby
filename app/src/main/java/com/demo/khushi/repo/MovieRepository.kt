package com.demo.khushi.repo

import com.demo.khushi.interfaces.MovieApi
import com.demo.khushi.usecases.GetPopularMoviesUseCase
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getPopularMovies( page: Int) =
        movieApi.getPopularMovies( page =  page)
}
