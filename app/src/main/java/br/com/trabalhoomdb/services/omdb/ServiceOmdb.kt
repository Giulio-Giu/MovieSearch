package br.com.trabalhoomdb.services.omdb

import br.com.trabalhoomdb.models.omdb.ResponseEpisodes
import br.com.trabalhoomdb.models.omdb.Serie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceOmdb {

    @GET("default.aspx")
    fun searchSerie(@Query("t") t: String, @Query("apikey") apikey: String): Call<Serie>

    @GET("default.aspx")
    fun searchSeason(@Query("t") t: String, @Query("Season") season: String, @Query("apikey") apikey: String): Call<ResponseEpisodes>

}