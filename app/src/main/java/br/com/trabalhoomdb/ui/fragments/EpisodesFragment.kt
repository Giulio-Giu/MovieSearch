package br.com.trabalhoomdb.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.omdb.Episode
import br.com.trabalhoomdb.models.omdb.ResponseEpisodes
import br.com.trabalhoomdb.services.omdb.RetrofitInitializerOmdb
import br.com.trabalhoomdb.ui.activities.HomeActivity
import br.com.trabalhoomdb.ui.adapters.omdb.EpisodesAdapter
import kotlinx.android.synthetic.main.fragment_episodes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class EpisodesFragment : Fragment() {

    val apiKey = "2f5cfd66"
    lateinit var search: String
    lateinit var contextActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contextActivity = context as HomeActivity

        search = contextActivity.intent.getStringExtra("search")

        tv_fragmentEpisode_messageError.visibility = TextView.INVISIBLE

        fragmentEpisode_btn_search.setOnClickListener {
            if (et_fragmentEpisode_seasonSearch.text.toString().trim().isEmpty()) {
                tv_fragmentEpisode_messageError.visibility = TextView.VISIBLE
                tv_fragmentEpisode_messageError.text = resources.getString(R.string.fragmentEpisode_message_error_seasonNumberField)
            } else {
                tv_fragmentEpisode_messageError.visibility = TextView.INVISIBLE
                listEpisodes(et_fragmentEpisode_seasonSearch.text.toString().trim())
            }
        }
    }

    fun listEpisodes(season: String) {
        val e = RetrofitInitializerOmdb().serviceOmdb()

        val call = e.listEpisodes(search, season, apiKey)

        call.enqueue(object : Callback<ResponseEpisodes> {
            override fun onResponse(call: Call<ResponseEpisodes>?, response: Response<ResponseEpisodes>?) {
                response?.let {
                    if (it.body().Response.equals("true", ignoreCase = true)) {
                        tv_fragmentEpisode_messageError.visibility = TextView.INVISIBLE
                        callAdater(it.body().Episodes)
                    } else {
                        Toast.makeText(
                            contextActivity,
                            resources.getString(R.string.fragmentEpisode_message_error_searchResult),
                            Toast.LENGTH_LONG
                        ).show()
                        tv_fragmentEpisode_messageError.visibility = TextView.VISIBLE
                        tv_fragmentEpisode_messageError.text =
                            resources.getString(R.string.fragmentEpisode_message_error_searchResult)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseEpisodes>?, t: Throwable?) {
                Toast.makeText(contextActivity, getString(R.string.fragmentEpisode_message_failure_enqueue), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun callAdater(episodes: List<Episode>) {
        val adapter = EpisodesAdapter(contextActivity, episodes)
        fragmentEpisode_recyclerView.adapter = adapter
        fragmentEpisode_recyclerView.layoutManager = LinearLayoutManager(contextActivity)
    }
}
