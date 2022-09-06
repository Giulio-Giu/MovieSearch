package br.com.trabalhoomdb.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListEpisodes(
    var Season: String = "",
    var Episodes: List<Episode> = emptyList(),
    var Response: String = ""
): Parcelable