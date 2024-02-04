package com.demo.khushi.MovieDetails.usecases

import com.demo.khushi.MovieDetails.Models.MovieDetailsResponse
import com.demo.khushi.MovieDetails.repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int): MovieDetailsResponse {
        return movieDetailsRepository.getMovieDetails(movieId)
    }
}
