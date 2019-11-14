package br.com.trabalhoomdb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.omdb.Film
import br.com.trabalhoomdb.services.omdb.RetrofitInitializerOmdb
import br.com.trabalhoomdb.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    val apiKey = "2f5cfd66"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_home_messageError.visibility = TextView.INVISIBLE
        constraint_home_result.visibility = ConstraintLayout.INVISIBLE

        home_btn_search.setOnClickListener {
            if (et_movieSearchHint.text.toString().trim().isEmpty()) {
                tv_home_messageError.visibility = TextView.VISIBLE
            } else {
                tv_home_messageError.text = resources.getString(R.string.home_message_error_searchField)
                tv_home_messageError.visibility = TextView.INVISIBLE
                searchFilm(et_movieSearchHint.text.toString().trim())
            }
        }
    }

    fun searchFilm(search: String) {
        val contextActivity = context as HomeActivity

        val f = RetrofitInitializerOmdb().serviceOmdb()

        val call = f.searchFilm(search, apiKey)

        call.enqueue(object : Callback<Film> {

            override fun onResponse(call: Call<Film>?, response: Response<Film>?) {
                response?.let {
                    if (it.body().Response.equals("true")) {
                        getItems(it.body())
                    } else {
                        Toast.makeText(contextActivity, resources.getString(R.string.home_message_error_searchResult), Toast.LENGTH_LONG).show()
                        
                        constraint_home_result.visibility = ConstraintLayout.INVISIBLE
                        tv_home_messageError.text = resources.getString(R.string.home_message_error_searchResult)
                        tv_home_messageError.visibility = TextView.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<Film>?, t: Throwable?) {
                Toast.makeText(contextActivity, "Get some error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getItems(film: Film) {

        tv_movie_title.text = film.Title
        tv_movie_imdbRating.text = film.imdbRating
        tv_movie_imdbVotes.text = film.imdbVotes
        tv_movie_type.text = film.Type
        tv_movie_runtime.text = film.Runtime
        tv_movie_released.text = film.Released
        tv_movie_year.text = film.Year
        tv_movie_country.text = film.Country

        //fazer separado o que for só de serie e o q for só de filme
        val type = film.Type
        if (type.equals("series")){
            val type2 = "S" + type.substring(1,type.length)

            // ....
        } else {
            val type2 = "M" + type.substring(1,type.length)

            // ....
        }
        //fazer o poster com glide

        tv_movie_plot.text = film.Plot
        tv_movie_genre.text = film.Genre
        tv_movie_actors.text = film.Actors
        tv_movie_writers.text = film.Writer
        tv_movie_awards.text = film.Awards

        //
        //
    }
}
