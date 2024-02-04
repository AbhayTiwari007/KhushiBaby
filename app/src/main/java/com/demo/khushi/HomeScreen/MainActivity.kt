package com.demo.khushi.HomeScreen

import MoviePagingAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.khushi.Adapters.MovieLoadStateAdapter
import com.demo.khushi.MovieDetails.Ui.MovieDetailsActivity
import com.demo.khushi.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var movieAdapter: MoviePagingAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        textView = findViewById(R.id.errorTextView)

        lifecycleScope.launch {
            movieViewModel.getPopularMovies().collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        movieAdapter = MoviePagingAdapter { movieId ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movieId)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { movieAdapter.retry() },
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }

        movieAdapter.addLoadStateListener { loadState ->
            progressBar.visibility = if (loadState.refresh is androidx.paging.LoadState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }

            val errorState = loadState.refresh as? androidx.paging.LoadState.Error
            errorState?.let {
                textView.visibility = View.VISIBLE
                Toast.makeText(this, "Something went wrong ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear_data -> {
                clearData()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun clearData() {
        lifecycleScope.launch {
            try {
                movieViewModel.clearData()

                movieAdapter.refresh()
                Toast.makeText(this@MainActivity, "Data cleared successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("ClearDataError", "Error clearing data: ${e.message}")
                Toast.makeText(this@MainActivity, "Failed to clear data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
