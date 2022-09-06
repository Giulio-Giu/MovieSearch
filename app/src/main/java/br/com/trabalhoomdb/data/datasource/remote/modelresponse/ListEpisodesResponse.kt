package br.com.trabalhoomdb.data.datasource.remote.modelresponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListEpisodesResponse(
    var Season: String? = null,
    var Episodes: List<EpisodeResponse>? = null,
    var Response: String
): Parcelable