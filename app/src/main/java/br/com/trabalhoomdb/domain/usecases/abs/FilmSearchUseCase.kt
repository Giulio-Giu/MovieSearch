package br.com.trabalhoomdb.domain.usecases.abs

import br.com.trabalhoomdb.domain.models.Film

interface FilmSearchUseCase {
    suspend fun invoke(query: String, apiKey: String): Film?
}