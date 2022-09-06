package br.com.trabalhoomdb.data.repository

import br.com.trabalhoomdb.data.datasource.mappers.toEpisodesListDomainModel
import br.com.trabalhoomdb.data.datasource.mappers.toFilmSearchDomainModel
import br.com.trabalhoomdb.data.datasource.remote.abs.FilmRemoteDataSource
import br.com.trabalhoomdb.domain.models.Film
import br.com.trabalhoomdb.domain.models.ListEpisodes
import br.com.trabalhoomdb.domain.repository.FilmSearchRepository

class FilmSearchRepositoryImpl(
    private val filmDataSource: FilmRemoteDataSource
) : FilmSearchRepository {
    override suspend fun getFilmSearch(query: String, apiKey: String): Film {
        return filmDataSource.getFilmSearch(query, apiKey).toFilmSearchDomainModel()
    }

    override suspend fun getListEpisodes(
        query: String,
        season: String,
        apiKey: String
    ): ListEpisodes {
        return filmDataSource.getListEpisodes(query, season, apiKey).toEpisodesListDomainModel()
    }
}