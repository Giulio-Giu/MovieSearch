package br.com.trabalhoomdb.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import br.com.trabalhoomdb.R
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

        val sharedPreferences =
            view.context.getSharedPreferences(getString(R.string.PREF_APP_NAME), Context.MODE_PRIVATE)

        if (sharedPreferences.getStringSet(getString(R.string.PREF_LIST_SEARCH), null).isNullOrEmpty()) {
            tv_fragmentHistoric_messageError.visibility = TextView.VISIBLE
            historic_recyclerView.visibility = RecyclerView.INVISIBLE
        } else {
            tv_fragmentHistoric_messageError.visibility = TextView.INVISIBLE
            historic_recyclerView.visibility = RecyclerView.VISIBLE

            historic_recyclerView.adapter = HistoricAdapter(view.context, sharedPreferences.getStringSet(getString(R.string.PREF_LIST_SEARCH), null)!!.toList())
        }
    }


}
