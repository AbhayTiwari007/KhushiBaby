package com.demo.khushi.Utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.demo.khushi.Models.Movie
import com.demo.khushi.Models.MovieEntity
import com.demo.khushi.usecases.GetPopularMoviesUseCase
import com.demo.khushi.interfaces.MovieDao

class MoviePagingSource(

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val movieDao: MovieDao
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val nextPageNumber = params.key ?: 1
            val movieList = getPopularMoviesUseCase(nextPageNumber)

            if (movieList.results.isNullOrEmpty()) {
                Log.d(TAG, "Loading from local database for page: $nextPageNumber")

            }

            Log.d(TAG, "Inserting movies into the local database")
            movieList.results.map { it.toEntity() }?.let { movieDao.insertMovies(it) }

            Log.d(TAG, "Loaded movies from the server for page: $nextPageNumber")
            return LoadResult.Page(
                data = movieList.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (movieList.results.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error loading data: ${e.message}")
            val localMovies = movieDao.getAllMovies().map { it.toMovie() }
            val nextPageNumber = params.key ?: 1
            return LoadResult.Page(
                data = localMovies,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (localMovies.isEmpty()) null else nextPageNumber + 1
            )
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    private fun MovieEntity.toMovie(): Movie {
        return Movie(id = id, overview = overview, releaseDate = releaseDate, title = title, posterPath = posterPath)
    }

    private fun Movie.toEntity(): MovieEntity {
        return  MovieEntity(id, title, overview, posterPath, releaseDate)
    }

    companion object {
        private const val TAG = "MoviePagingSource"
    }
}
