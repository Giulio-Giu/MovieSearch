package br.com.trabalhoomdb.domain.repository

import br.com.trabalhoomdb.domain.models.Film
import br.com.trabalhoomdb.domain.models.ListEpisodes

interface FilmSearchRepository {
    suspend fun getFilmSearch(query: String, apiKey: String): Film
    suspend fun getListEpisodes(query: String, season: String, apiKey: String): ListEpisodes
}