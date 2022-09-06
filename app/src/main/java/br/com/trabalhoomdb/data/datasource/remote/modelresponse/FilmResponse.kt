package br.com.trabalhoomdb.data.datasource.remote.modelresponse

import android.os.Parcelable
import br.com.trabalhoomdb.domain.models.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmResponse(
    var Title: String? = null,
    var Year: String? = null,
    var Released: String? = null,
    var Runtime: String? = null,
    var Genre: String? = null,
    var Director: String? = null,
    var Writer: String? = null,
    var Actors: String? = null,
    var Plot: String? = null,
    var Country: String? = null,
    var Awards: String? = null,
    var Poster: String? = null,
    var Ratings: List<RatingResponse>? = null,
    var imdbRating: String? = null,
    var imdbVotes: String? = null,
    var Type: String? = null,
    var BoxOffice: String? = null,
    var Production: String? = null,
    var totalSeasons: String? = null,
    var Response: String = "",
) : Parcelable