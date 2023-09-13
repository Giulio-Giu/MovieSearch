package br.com.trabalhoomdb.data.datasource.remote.modelresponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeResponse(
    var Title: String? = null,
    var Released: String? = null,
    var Episode: String? = null,
    var imdbRating: String? = null
) : Parcelable