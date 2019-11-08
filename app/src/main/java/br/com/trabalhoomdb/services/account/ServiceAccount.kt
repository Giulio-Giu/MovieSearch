package br.com.trabalhoomdb.services.account

import br.com.trabalhoomdb.models.account.Account
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceAccount {

    @POST("account/auth")
    fun auth(@Body account: Account): Call<Account>

    @POST("account")
    fun insert(@Body account: Account): Call<Account>

    @POST("account/forgot")
    fun forgotPassword(@Body email: String): Call<Account>

}