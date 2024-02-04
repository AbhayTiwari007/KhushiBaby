package com.demo.khushi.usecases

import com.demo.khushi.Models.MovieResponse
import com.demo.khushi.interfaces.MovieDao
import com.demo.khushi.repo.MovieRepository
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke( page: Int): MovieResponse {
        val  response=movieRepository.getPopularMovies( page)
        if (response.results.isNullOrEmpty()){

        }
        return response
    }
}
