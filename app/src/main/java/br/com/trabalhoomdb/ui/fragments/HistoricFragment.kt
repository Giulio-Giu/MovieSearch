package br.com.trabalhoomdb.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.ui.activities.HomeActivity
import br.com.trabalhoomdb.ui.adapters.HistoricAdapter
import kotlinx.android.synthetic.main.fragment_historic.*

class HistoricFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contextActivity = context as HomeActivity

        val sharedPreferences = contextActivity.getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        val searchList = sharedPreferences.getStringSet(getString(R.string.PREF_LIST_SEARCH), null)

        if (searchList.isNullOrEmpty()) {
            tv_fragmentHistoric_messageError.visibility = TextView.VISIBLE
            historic_recyclerView.visibility = RecyclerView.INVISIBLE
        } else {
            tv_fragmentHistoric_messageError.visibility = TextView.INVISIBLE
            historic_recyclerView.visibility = RecyclerView.VISIBLE

            val adapter = HistoricAdapter(contextActivity, searchList.toList())

            adapter.clickListener = object : HistoricAdapter.ClickListener {
                override fun onClick(search: String) {
                    sharedPreferences.edit()
                        .putString(getString(R.string.PREF_HISTORIC_SEARCH), search)
                        .apply()
                    (activity as HomeActivity).setFragment(HomeFragment())
                }
            }

            historic_recyclerView.adapter = adapter
            historic_recyclerView.layoutManager = LinearLayoutManager(contextActivity)

            btn_fragmentHistoric_clear.setOnClickListener {
                sharedPreferences.edit().putStringSet(getString(R.string.PREF_LIST_SEARCH), null)
                    .apply()

                tv_fragmentHistoric_messageError.visibility = TextView.VISIBLE
                historic_recyclerView.visibility = RecyclerView.INVISIBLE
            }
        }
    }
}
