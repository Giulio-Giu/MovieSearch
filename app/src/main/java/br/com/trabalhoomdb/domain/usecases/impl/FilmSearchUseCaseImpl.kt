package br.com.trabalhoomdb.domain.usecases.impl

import br.com.trabalhoomdb.domain.models.Film
import br.com.trabalhoomdb.domain.repository.FilmSearchRepository
import br.com.trabalhoomdb.domain.usecases.abs.FilmSearchUseCase

class FilmSearchUseCaseImpl(
    private val filmSearchRepository: FilmSearchRepository
) : FilmSearchUseCase {
    override suspend fun invoke(query: String, apiKey: String) : Film {
        return filmSearchRepository.getFilmSearch(query, apiKey)
    }
}