package br.com.trabalhoomdb.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.Episode
import br.com.trabalhoomdb.models.ResponseEpisodes
import br.com.trabalhoomdb.services.RetrofitInitializerOmdb
import br.com.trabalhoomdb.ui.activities.HomeActivity
import br.com.trabalhoomdb.ui.adapters.EpisodesAdapter
import kotlinx.android.synthetic.main.fragment_episodes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodesFragment : Fragment() {

    private val apiKey = "2f5cfd66"
    private lateinit var search: String
    lateinit var contextActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contextActivity = context as HomeActivity

        search = contextActivity.intent.getStringExtra(getString(R.string.PREF_SEARCH)) ?: ""

        tv_fragmentEpisode_messageError.visibility = View.GONE

        listEpisodes("1")
        et_fragmentEpisode_seasonSearch.setText("1")

        fragmentEpisode_btn_search.setOnClickListener {
            callListEpisodes()
        }

        et_fragmentEpisode_seasonSearch.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) ||
                actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO
            ) {
                //do what you want on the press of 'done'
                callListEpisodes()
                (activity as HomeActivity).hideSoftKeyboard(contextActivity)
            }
            false
        }
    }

    private fun callListEpisodes() {
        if (et_fragmentEpisode_seasonSearch.text.toString().trim().isEmpty()) {
            tv_fragmentEpisode_messageError.visibility = View.VISIBLE
            fragmentEpisode_recyclerView.visibility = View.GONE
            tv_fragmentEpisode_messageError.text =
                resources.getString(R.string.fragmentEpisode_message_error_seasonNumberField)
        } else {
            tv_fragmentEpisode_messageError.visibility = View.GONE
            fragmentEpisode_recyclerView.visibility = View.VISIBLE
            listEpisodes(et_fragmentEpisode_seasonSearch.text.toString().trim())
        }
    }

    private fun listEpisodes(season: String) {
        val e = RetrofitInitializerOmdb(contextActivity).serviceOmdb()

        val call = e.listEpisodes(search, season, apiKey)

        call.enqueue(object : Callback<ResponseEpisodes> {
            override fun onResponse(
                call: Call<ResponseEpisodes>?,
                response: Response<ResponseEpisodes>?
            ) {
                response?.let {
                    if (it.body().Response.equals("true", ignoreCase = true)) {
                        tv_fragmentEpisode_messageError.visibility = View.GONE
                        fragmentEpisode_recyclerView.visibility = View.VISIBLE
                        callAdapter(it.body().Episodes)
                    } else {
                        Toast.makeText(
                            contextActivity,
                            resources.getString(R.string.fragmentEpisode_message_error_searchResult),
                            Toast.LENGTH_LONG
                        ).show()
                        tv_fragmentEpisode_messageError.visibility = View.VISIBLE
                        fragmentEpisode_recyclerView.visibility = View.GONE
                        tv_fragmentEpisode_messageError.text =
                            resources.getString(R.string.fragmentEpisode_message_error_searchResult)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseEpisodes>?, t: Throwable?) {
                Toast.makeText(
                    contextActivity,
                    getString(R.string.fragmentEpisode_message_failure_enqueue),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
    }

    fun callAdapter(episodes: List<Episode>) {
        val adapter = EpisodesAdapter(contextActivity, episodes)
        fragmentEpisode_recyclerView.adapter = adapter
        fragmentEpisode_recyclerView.layoutManager = LinearLayoutManager(contextActivity)
    }
}
