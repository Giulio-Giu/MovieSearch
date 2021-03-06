package br.com.trabalhoomdb.services

import br.com.trabalhoomdb.models.ResponseEpisodes
import br.com.trabalhoomdb.models.Film
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceOmdb {

    @GET("default.aspx")
    fun searchFilm(@Query("t") t: String, @Query("apikey") apikey: String): Call<Film>

    @GET("default.aspx")
    fun listEpisodes(
        @Query("t") t: String,
        @Query("Season") season: String,
        @Query("apikey") apikey: String
    ): Call<ResponseEpisodes>

}