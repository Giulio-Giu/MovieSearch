package br.com.trabalhoomdb.presentation.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.databinding.FragmentHistoricBinding
import br.com.trabalhoomdb.presentation.eventclick.EventClickItemHistoric
import br.com.trabalhoomdb.presentation.ui.activities.HomeActivity
import br.com.trabalhoomdb.presentation.ui.adapters.HistoricAdapter

class HistoricFragment : Fragment() {

    lateinit var contextActivity: HomeActivity
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentHistoricBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // contextActivity = context as HomeActivity
        sharedPreferences = (context as HomeActivity).getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        initView()
        initListeners()
    }

    private fun initView() {
//        val sharedPreferences = contextActivity.getSharedPreferences(
//            getString(R.string.PREF_APP_NAME),
//            Context.MODE_PRIVATE
//        )

        val searchList = sharedPreferences.getStringSet(getString(R.string.PREF_LIST_SEARCH), null)

        if (searchList.isNullOrEmpty()) {
            viewBinding.tvFragmentHistoricMessageError.visibility = View.VISIBLE
            viewBinding.historicRecyclerView.visibility = View.INVISIBLE
        } else {
            viewBinding.tvFragmentHistoricMessageError.visibility = View.INVISIBLE
            viewBinding.historicRecyclerView.visibility = View.VISIBLE

            val adapter = HistoricAdapter(searchList.toList())

            adapter.eventClickItemHistoric = object : EventClickItemHistoric {
                override fun onClickItemHistoric(search: String) {
                    sharedPreferences.edit()
                        .putString(getString(R.string.PREF_HISTORIC_SEARCH), search)
                        .apply()
                    //contextActivity.intent.putExtra(
                    requireActivity().intent.putExtra(
                        getString(R.string.PREF_HISTORIC_SEARCH),
                        search
                    )
                    (activity as HomeActivity).setFragment(HomeFragment())
                }
            }

            viewBinding.historicRecyclerView.adapter = adapter
        }
    }

    private fun initListeners() {
        viewBinding.btnFragmentHistoricClear.setOnClickListener {
            sharedPreferences.edit().putStringSet(getString(R.string.PREF_LIST_SEARCH), null)
                .apply()

            viewBinding.tvFragmentHistoricMessageError.visibility = View.VISIBLE
            viewBinding.historicRecyclerView.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
