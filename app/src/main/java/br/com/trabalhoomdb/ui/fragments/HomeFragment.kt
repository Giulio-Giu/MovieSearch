package br.com.trabalhoomdb.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.omdb.Episode
import br.com.trabalhoomdb.models.omdb.Film
import br.com.trabalhoomdb.services.omdb.RetrofitInitializerOmdb
import br.com.trabalhoomdb.ui.activities.HomeActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    val apiKey = "2f5cfd66"
    var imageUrl = ""
    lateinit var contextActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contextActivity = context as HomeActivity

        tv_home_messageError.visibility = TextView.INVISIBLE
        constraint_home_result.visibility = ConstraintLayout.INVISIBLE

        val shared = contextActivity.getSharedPreferences(getString(R.string.PREF_APP_NAME), Context.MODE_PRIVATE)
        val historicSearch = shared.getString(getString(R.string.PREF_HISTORIC_SEARCH), "")

        if (!historicSearch.isNullOrEmpty()) {
            searchFilm(historicSearch)

            //remover do shared para não fazer a pesquisa depois de sair entrar dnv no app
            shared.edit()
                .remove(getString(R.string.PREF_HISTORIC_SEARCH))
                .apply()
        }

        home_btn_search.setOnClickListener {
            if (et_movieSearchHint.text.toString().trim().isEmpty()) {
                tv_home_messageError.visibility = TextView.VISIBLE
                tv_home_messageError.text = resources.getString(R.string.home_message_error_searchField)
            } else {
                tv_home_messageError.visibility = TextView.INVISIBLE
                searchFilm(et_movieSearchHint.text.toString().trim())
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

    fun gotoEpisodeGuide() {
        contextActivity.intent.putExtra("search", et_movieSearchHint.text.toString().trim())
        (activity as HomeActivity).setFragment(EpisodesFragment())
    }

    fun searchFilm(search: String) {

        val f = RetrofitInitializerOmdb().serviceOmdb()

        val call = f.searchFilm(search, apiKey)

        call.enqueue(object : Callback<Film> {

            override fun onResponse(call: Call<Film>?, response: Response<Film>?) {
                response?.let {
                    if (it.body().Response.equals("true", ignoreCase = true)) {
                        constraint_home_result.visibility = ConstraintLayout.VISIBLE
                        getItems(it.body(), contextActivity, search)
                    } else {
                        Toast.makeText(
                            contextActivity,
                            resources.getString(R.string.home_message_error_searchResult),
                            Toast.LENGTH_LONG
                        ).show()
                        constraint_home_result.visibility = ConstraintLayout.INVISIBLE
                        tv_home_messageError.text = resources.getString(R.string.home_message_error_searchResult)
                        tv_home_messageError.visibility = TextView.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<Film>?, t: Throwable?) {
                Toast.makeText(contextActivity, getString(R.string.home_message_failure_enqueue), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun getItems(film: Film, contextActivity: HomeActivity, search: String) {

        val shared = contextActivity.getSharedPreferences(getString(R.string.PREF_APP_NAME), Context.MODE_PRIVATE)

        var searchList: MutableSet<String>? = shared.getStringSet(getString(R.string.PREF_LIST_SEARCH), null)

        if (searchList.isNullOrEmpty()) {
            searchList = HashSet()
            searchList.add(search)
        } else {
            searchList.add(search)
        }

        shared.edit()
            .putStringSet(getString(R.string.PREF_LIST_SEARCH), searchList)
            .apply()

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

        /**fazer separado o que for só de serie e o q for só de filme */
        if (type.equals("series", ignoreCase = true)) {
            tv_movie_type.text = "S" + type.substring(1, type.length)

            group_show_only_series.visibility = Group.VISIBLE
            group_show_only_movie.visibility = Group.GONE
            tv_movie_totalSeasons.text = film.totalSeasons

        } else {
            tv_movie_type.text = "M" + type.substring(1, type.length)

            group_show_only_series.visibility = Group.GONE
            group_show_only_movie.visibility = Group.VISIBLE

            tv_movie_director.text = film.Director
            tv_movie_boxOffice.text = film.BoxOffice
            tv_movie_production.text = film.Production

        }

    }
}
