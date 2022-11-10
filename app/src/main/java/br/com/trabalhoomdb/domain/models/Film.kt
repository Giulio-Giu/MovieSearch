package br.com.trabalhoomdb.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    var Title: String = "",
    var Year: String = "",
    var Released: String = "",
    var Runtime: String = "",
    var Genre: String = "",
    var Director: String = "",
    var Writer: String = "",
    var Actors: String = "",
    var Plot: String = "",
    var Country: String = "",
    var Awards: String = "",
    var Poster: String = "",
    var Ratings: List<Rating> = emptyList(),
    var imdbRating: String = "",
    var imdbVotes: String = "",
    var Type: String = "",
    var BoxOffice: String = "",
    var Production: String = "",
    var totalSeasons: String = "",
    var Response: String = ""
) : Parcelable