package br.com.trabalhoomdb.services.account

import br.com.trabalhoomdb.models.omdb.ResponseEpisodes
import br.com.trabalhoomdb.models.omdb.Serie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAccount {

    @GET("default.aspx")
    fun signIn(@Query("t") t: String, @Query("apikey") apikey: String): Call<Serie>

    @GET("default.aspx")
    fun signUp(@Query("t") t: String, @Query("Season") season: String, @Query("apikey") apikey: String): Call<ResponseEpisodes>

}