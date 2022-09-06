package br.com.trabalhoomdb.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    var Title: String = "",
    var Released: String = "",
    var Episode: String = "",
    var imdbRating: String = ""
): Parcelable