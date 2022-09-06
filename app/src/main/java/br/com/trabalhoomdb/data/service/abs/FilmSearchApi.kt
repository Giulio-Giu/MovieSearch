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

/**
 *
 *

private fun searchFilm(search: String, isInitial: Boolean) {

val f = RetrofitInitializerOmdb(contextActivity).serviceOmdb()

val call = f.searchFilm(search, apiKey)

call.enqueue(object : Callback<Film> {

override fun onResponse(call: Call<Film>?, response: Response<Film>?) {
response?.let {
if (it.body().Response.equals("true", ignoreCase = true)) {
constraint_home_result.visibility = View.VISIBLE
getItems(it.body(), contextActivity, search, isInitial)
} else {
Toast.makeText(
contextActivity,
resources.getString(R.string.home_message_error_searchResult),
Toast.LENGTH_LONG
).show()
constraint_home_result.visibility = View.INVISIBLE
tv_home_messageError.text =
resources.getString(R.string.home_message_error_searchResult)
tv_home_messageError.visibility = View.VISIBLE
}
}
}

override fun onFailure(call: Call<Film>?, t: Throwable?) {
Toast.makeText(
contextActivity,
getString(R.string.home_message_failure_enqueue),
Toast.LENGTH_LONG
)
.show()
}
})
}
 */