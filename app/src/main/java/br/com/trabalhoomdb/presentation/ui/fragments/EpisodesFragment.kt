package br.com.trabalhoomdb.presentation.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.common.utils.Keyboard
import br.com.trabalhoomdb.databinding.FragmentEpisodesBinding
import br.com.trabalhoomdb.domain.models.Episode
import br.com.trabalhoomdb.presentation.ui.activities.HomeActivity
import br.com.trabalhoomdb.presentation.ui.adapters.EpisodesAdapter
import br.com.trabalhoomdb.presentation.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodesFragment : Fragment() {

    private lateinit var search: String
    lateinit var contextActivity: HomeActivity
    private var _binding: FragmentEpisodesBinding? = null
    private val viewBinding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObservables()
        initListeners()

        contextActivity = context as HomeActivity

        search = contextActivity.intent.getStringExtra(getString(R.string.PREF_SEARCH)) ?: ""
    }

    private fun initView() {
        viewBinding.tvFragmentEpisodeMessageError.visibility = View.GONE
        viewBinding.fragmentEpisodeRecyclerView.visibility = View.GONE
        (activity as HomeActivity).showHideProgressBar(true)

        listEpisodes(search)
        viewBinding.etFragmentEpisodeSeasonSearch.setText("1")
    }

    private fun initListeners() {
        viewBinding.fragmentEpisodeBtnSearch.setOnClickListener {
            callListEpisodes()
        }

        viewBinding.etFragmentEpisodeSeasonSearch.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) ||
                actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO
            ) {
                callListEpisodes()
                Keyboard.hideKeyboard(requireView())
            }
            false
        }
    }

    private fun initObservables() {
        viewModel.listEpisodesLiveData.observe(viewLifecycleOwner)
        { callAdapter(it.Episodes) }
    }

    private fun callListEpisodes() {
        if (viewBinding.etFragmentEpisodeSeasonSearch.text.toString().trim().isEmpty()) {
            viewBinding.tvFragmentEpisodeMessageError.visibility = View.VISIBLE
            viewBinding.fragmentEpisodeRecyclerView.visibility = View.GONE
            viewBinding.tvFragmentEpisodeMessageError.text =
                resources.getString(R.string.fragmentEpisode_message_error_seasonNumberField)
        } else {
            viewBinding.tvFragmentEpisodeMessageError.visibility = View.GONE
            viewBinding.fragmentEpisodeRecyclerView.visibility = View.VISIBLE
            listEpisodes(viewBinding.etFragmentEpisodeSeasonSearch.text.toString().trim())
        }
    }

    private fun listEpisodes(season: String) {
        viewModel.listEpisodes(search, season, HomeActivity.getApiKey())
    }

    private fun callAdapter(episodes: List<Episode>) {
        val adapter = EpisodesAdapter(episodes)
        viewBinding.fragmentEpisodeRecyclerView.visibility = View.VISIBLE
        viewBinding.fragmentEpisodeRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
