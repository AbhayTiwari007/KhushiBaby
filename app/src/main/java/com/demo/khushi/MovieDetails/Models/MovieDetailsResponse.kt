package com.demo.khushi.MovieDetails.Models

import com.google.gson.annotations.SerializedName


data class MovieDetailsResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val collection: Collection?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date") val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) {
    data class Collection(
        val id: Int,
        val name: String,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("backdrop_path") val backdropPath: String?
    )

    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        @SerializedName("logo_path") val logoPath: String?,
        val name: String,
        @SerializedName("origin_country") val originCountry: String
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1") val iso3166_1: String,
        val name: String
    )

    data class SpokenLanguage(
        @SerializedName("english_name") val englishName: String,
        @SerializedName("iso_639_1") val iso639_1: String,
        val name: String
    )
}
