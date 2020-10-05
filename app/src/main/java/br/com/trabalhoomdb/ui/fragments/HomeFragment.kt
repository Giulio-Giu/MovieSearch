package br.com.trabalhoomdb.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.Film
import br.com.trabalhoomdb.services.RetrofitInitializerOmdb
import br.com.trabalhoomdb.ui.activities.HomeActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashSet

class HomeFragment : Fragment() {

    private val apiKey = "2f5cfd66"
    private var imageUrl = ""
    lateinit var contextActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contextActivity = context as HomeActivity

        tv_home_messageError.visibility = View.INVISIBLE
        constraint_home_result.visibility = View.INVISIBLE

        searchFilm(getString(R.string.home_default_search), true)

        val shared = contextActivity.getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        val historicSearch = shared.getString(getString(R.string.PREF_HISTORIC_SEARCH), "")

        if (!historicSearch.isNullOrEmpty()) {
            searchFilm(historicSearch, false)

            //remover do shared para não fazer a pesquisa depois de sair entrar dnv no app
//            shared.edit()
//                .remove(getString(R.string.PREF_HISTORIC_SEARCH))
//                .apply()
        }

        if (et_movieSearchHint.text.toString().trim().isNotEmpty()) {
            tv_home_messageError.visibility = View.INVISIBLE
            searchFilm(et_movieSearchHint.text.toString().trim(), false)
        }

        home_btn_search.setOnClickListener {
            if (et_movieSearchHint.text.toString().trim().isEmpty()) {
                tv_home_messageError.visibility = View.VISIBLE
                tv_home_messageError.text =
                    resources.getString(R.string.home_message_error_searchField)
            } else {
                tv_home_messageError.visibility = View.INVISIBLE
                searchFilm(et_movieSearchHint.text.toString().trim(), false)
            }
        }

        iv_movie_poster.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl))
            intent.setDataAndType(Uri.parse(imageUrl), "image/png")
            startActivity(intent)
        }

        tv_movie_episodeGuide.setOnClickListener {
            gotoEpisodeGuide()
        }

        tv_movie_totalSeasons.setOnClickListener {
            gotoEpisodeGuide()
        }

        ib_episodes_arrow.setOnClickListener {
            gotoEpisodeGuide()
        }
    }

    private fun gotoEpisodeGuide() {

        contextActivity = context as HomeActivity

        val shared = contextActivity.getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        val search = shared.getString(getString(R.string.PREF_SEARCH), "")

        if (search.isNullOrEmpty()) {
            contextActivity.intent.putExtra(
                getString(R.string.PREF_SEARCH),
                et_movieSearchHint.text.toString().trim()
            )
        } else {
            contextActivity.intent.putExtra(getString(R.string.PREF_SEARCH), search)
        }

        (activity as HomeActivity).setFragment(EpisodesFragment())
    }

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

    fun getItems(film: Film, contextActivity: HomeActivity, search: String, isInitial: Boolean) {

        if (!isInitial) {
            val shared = contextActivity.getSharedPreferences(
                getString(R.string.PREF_APP_NAME),
                Context.MODE_PRIVATE
            )

            var searchList: MutableSet<String>? =
                shared.getStringSet(getString(R.string.PREF_LIST_SEARCH), null)

            if (searchList.isNullOrEmpty()) {
                searchList = HashSet()
                searchList.add(search)
            } else {
                searchList.add(search)
            }

            shared.edit()
                .putStringSet(getString(R.string.PREF_LIST_SEARCH), searchList)
                .putString(getString(R.string.PREF_SEARCH), search)
                .apply()
        }

        /**itens comuns*/
        tv_movie_title.text = film.Title
        tv_movie_imdbRating.text = film.imdbRating
        tv_movie_imdbVotes.text = film.imdbVotes
        val type = film.Type
        tv_movie_runtime.text = film.Runtime
        tv_movie_released.text = film.Released
        tv_movie_year.text = film.Year
        tv_movie_country.text = film.Country

        Glide.with(contextActivity)
            .load(film.Poster)
            .into(iv_movie_poster)

        imageUrl = film.Poster

        tv_movie_plot.text = film.Plot
        tv_movie_genre.text = film.Genre
        tv_movie_actors.text = film.Actors
        tv_movie_writers.text = film.Writer
        tv_movie_awards.text = film.Awards

        /**fazer separado o que for só de série e o q for só de filme */
        if (type.equals("series", ignoreCase = true)) {
            tv_movie_type.text = "S" + type.substring(1, type.length)

            group_show_only_series.visibility = View.VISIBLE
            group_show_only_movie.visibility = View.GONE
            group_show_boxOffice.visibility = View.GONE
            group_show_production.visibility = View.GONE

            tv_movie_totalSeasons.text = "${film.totalSeasons} seasons"

        } else {
            tv_movie_type.text = "M" + type.substring(1, type.length)

            group_show_only_series.visibility = View.GONE
            group_show_only_movie.visibility = View.VISIBLE

            tv_movie_director.text = film.Director

            if (film.BoxOffice.toUpperCase(Locale.getDefault()) != "N/A") {
                group_show_boxOffice.visibility = View.VISIBLE
                tv_movie_boxOffice.text = film.BoxOffice
            }

            if (film.Production.toUpperCase(Locale.getDefault()) != "N/A") {
                group_show_production.visibility = View.VISIBLE
                tv_movie_production.text = film.Production
            }
        }
    }
}
