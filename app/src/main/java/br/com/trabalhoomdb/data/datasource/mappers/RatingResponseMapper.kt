package br.com.trabalhoomdb.data.datasource.mappers

import br.com.trabalhoomdb.data.datasource.remote.modelresponse.RatingResponse
import br.com.trabalhoomdb.domain.models.Rating

fun List<RatingResponse>.toListRatingDomainModel() = mapNotNull {
    try {
        it.toRatingDomainModel()
    } catch (e: Exception) {
        null
    }
}

fun RatingResponse.toRatingDomainModel() = Rating(
    Source = Source.orEmpty(),
    Value = Value.orEmpty()
)