package com.demo.khushi.HomeScreen
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.demo.khushi.Models.Movie
import com.demo.khushi.Utils.MoviePagingSource
import com.demo.khushi.interfaces.MovieDao
import com.demo.khushi.usecases.GetPopularMoviesUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val movieDao: MovieDao
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource( getPopularMoviesUseCase, movieDao) }
        ).flow
    }

    fun clearData() {
        viewModelScope.launch {
            try {
                movieDao.clearAllMovies()
            } catch (e: Exception) {

                _error.postValue("Failed to clear data: ${e.message}")
            }
        }
    }
}