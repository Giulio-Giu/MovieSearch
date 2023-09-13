package br.com.trabalhoomdb.data.service.abs

import br.com.trabalhoomdb.data.datasource.remote.modelresponse.FilmResponse
import br.com.trabalhoomdb.data.datasource.remote.modelresponse.ListEpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmSearchApi {
    companion object {
        private const val API_KEY_SEARCH = "apikey"
        private const val API_QUERY_SEARCH = "t"
        private const val API_SEASON_SEARCH = "season"
    }

    @GET("default.aspx")
    suspend fun searchFilm(
        @Query(API_QUERY_SEARCH) queryValue: String,
        @Query(API_KEY_SEARCH) apikey: String,
    ): FilmResponse

    @GET("default.aspx")
    fun listEpisodes(
        @Query(API_QUERY_SEARCH) queryValue: String,
        @Query(API_SEASON_SEARCH) season: String,
        @Query(API_KEY_SEARCH) apikey: String,
    ): ListEpisodesResponse

}