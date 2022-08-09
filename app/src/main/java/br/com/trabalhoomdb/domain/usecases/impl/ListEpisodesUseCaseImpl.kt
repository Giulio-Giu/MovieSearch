package br.com.trabalhoomdb.domain.usecases.impl

import br.com.trabalhoomdb.domain.models.ListEpisodes
import br.com.trabalhoomdb.domain.repository.FilmSearchRepository
import br.com.trabalhoomdb.domain.usecases.abs.ListEpisodesUseCase

class ListEpisodesUseCaseImpl(
    private val filmSearchRepository: FilmSearchRepository
) : ListEpisodesUseCase {
    override suspend fun invoke(query: String, season: String, apiKey: String): ListEpisodes {
        return filmSearchRepository.getListEpisodes(query, season, apiKey)
    }
}