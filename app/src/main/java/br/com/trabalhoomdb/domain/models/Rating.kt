package br.com.trabalhoomdb.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating (
    var Source: String = "",
    var Value: String = ""
): Parcelable