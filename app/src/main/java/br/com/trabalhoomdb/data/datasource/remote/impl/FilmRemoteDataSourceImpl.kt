package br.com.trabalhoomdb.data.datasource.remote.impl

import br.com.trabalhoomdb.data.datasource.remote.abs.FilmRemoteDataSource
import br.com.trabalhoomdb.data.datasource.remote.modelresponse.FilmResponse
import br.com.trabalhoomdb.data.datasource.remote.modelresponse.ListEpisodesResponse
import br.com.trabalhoomdb.data.service.abs.FilmSearchApi

class FilmRemoteDataSourceImpl(
    private val serviceFilmSearch: FilmSearchApi
) : FilmRemoteDataSource {

    override suspend fun getFilmSearch(query: String, apiKey: String): FilmResponse {
        return try {
            val result = serviceFilmSearch.searchFilm(query, apiKey)
            if (result.Response.toBoolean()) result else FilmResponse()
        } catch (e: Exception) {
            FilmResponse()
        }
    }

    override suspend fun getListEpisodes(
        query: String,
        season: String,
        apiKey: String
    ): ListEpisodesResponse {
        return try {
            val result = serviceFilmSearch.listEpisodes(query, season, apiKey)
            if (result.Response.toBoolean()) result else ListEpisodesResponse(Response = "False")
        } catch (e: Exception) {
            ListEpisodesResponse(Response = "False")
        }
    }
}