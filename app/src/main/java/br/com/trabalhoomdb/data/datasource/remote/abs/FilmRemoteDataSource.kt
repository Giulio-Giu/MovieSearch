package br.com.trabalhoomdb.data.datasource.remote.abs

import br.com.trabalhoomdb.data.datasource.remote.modelresponse.FilmResponse
import br.com.trabalhoomdb.data.datasource.remote.modelresponse.ListEpisodesResponse

interface FilmRemoteDataSource {
    suspend fun getFilmSearch(query: String, apiKey: String): FilmResponse
    suspend fun getListEpisodes(query: String, season: String, apiKey: String): ListEpisodesResponse
}