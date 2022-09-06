package br.com.trabalhoomdb.data.datasource.mappers

import br.com.trabalhoomdb.data.datasource.remote.modelresponse.EpisodeResponse
import br.com.trabalhoomdb.domain.models.Episode

fun List<EpisodeResponse>.toListEpisodesDomainModel() = mapNotNull {
    try {
        it.toEpisodeDomainModel()
    } catch (e: Exception) {
        null
    }
}

fun EpisodeResponse.toEpisodeDomainModel() = Episode(
    Title = Title.orEmpty(),
    Released = Released.orEmpty(),
    Episode = Episode.orEmpty(),
    imdbRating = imdbRating.orEmpty()
)