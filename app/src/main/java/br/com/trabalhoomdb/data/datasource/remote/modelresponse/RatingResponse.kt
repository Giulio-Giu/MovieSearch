package br.com.trabalhoomdb.data.datasource.remote.modelresponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingResponse (
    var Source: String? = null,
    var Value: String? = null
): Parcelable