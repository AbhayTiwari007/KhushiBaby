// MovieDetailsActivity.kt
package com.demo.khushi.MovieDetails.Ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.demo.khushi.MovieDetails.Models.MovieDetailsResponse
import com.demo.khushi.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var imageBackdrop: ImageView
    private lateinit var textTitle: TextView
    private lateinit var textGenres: TextView
    private lateinit var textReleaseDate: TextView
    private lateinit var textOverview: TextView
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        imageBackdrop = findViewById(R.id.imageBackdrop)
        textTitle = findViewById(R.id.textTitle)
        textGenres = findViewById(R.id.textGenres)
        textReleaseDate = findViewById(R.id.textReleaseDate)
        textOverview = findViewById(R.id.textOverview)
        progressBar = findViewById(R.id.progressBar)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)

        viewModel.movieDetails.observe(this, Observer { details ->
            updateUI(details)
        })

        viewModel.isLoading.observe(this, Observer { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE

            if (loading) {
                Toast.makeText(this, "Loading movie details...", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.error.observe(this, Observer { error ->
            if (error != null) {

                Toast.makeText(this, "Error: ${error.toString()}", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.loadMovieDetails(movieId)
    }

    private fun updateUI(details: MovieDetailsResponse) {
        textTitle.text = details.title
        textGenres.text = details.genres.joinToString { it.name }
        textReleaseDate.text = "Release Date: ${details.releaseDate}"
        textOverview.text = details.overview

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w780${details.backdropPath}")
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imageBackdrop)
    }
}
