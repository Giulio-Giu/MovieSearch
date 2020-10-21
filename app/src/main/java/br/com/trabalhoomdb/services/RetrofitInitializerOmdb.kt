package br.com.trabalhoomdb.services

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.ui.activities.HomeActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInitializerOmdb(contextActivity: HomeActivity) {

    companion object {
        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        }
    }

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(contextActivity.resources.getString(R.string.service_baseUrl))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun serviceOmdb(): ServiceOmdb {
        return retrofit.create(ServiceOmdb::class.java)
    }
}