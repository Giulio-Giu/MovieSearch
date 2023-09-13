package br.com.trabalhoomdb.domain.usecases.abs

import br.com.trabalhoomdb.domain.models.ListEpisodes

interface ListEpisodesUseCase {
    suspend fun invoke(query: String, season: String, apiKey: String): ListEpisodes
}