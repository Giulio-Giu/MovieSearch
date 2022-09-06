package br.com.trabalhoomdb.data.datasource.mappers

import br.com.trabalhoomdb.data.datasource.remote.modelresponse.ListEpisodesResponse
import br.com.trabalhoomdb.domain.models.ListEpisodes

fun ListEpisodesResponse.toEpisodesListDomainModel() = ListEpisodes(
    Season = Season.orEmpty(),
    Episodes = Episodes?.toListEpisodesDomainModel().orEmpty(),
    Response = Response
)