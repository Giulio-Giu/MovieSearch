package br.com.trabalhoomdb.presentation.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.common.utils.Keyboard
import br.com.trabalhoomdb.databinding.FragmentHomeBinding
import br.com.trabalhoomdb.domain.models.Film
import br.com.trabalhoomdb.presentation.ui.activities.HomeActivity
import br.com.trabalhoomdb.presentation.viewmodels.HomeViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var search: String = ""
    private var isInitial: Boolean = false
    private var imageUrl = ""
    lateinit var contextActivity: HomeActivity

    private var _binding: FragmentHomeBinding? = null
    private val viewBinding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contextActivity = context as HomeActivity
        initObservables()
        initView()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        viewBinding.tvHomeMessageError.visibility = View.GONE
        viewBinding.constraintHomeResult.visibility = View.VISIBLE

        val shared = contextActivity.getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        val historicSearch = shared.getString(getString(R.string.PREF_HISTORIC_SEARCH), "")

        if (!historicSearch.isNullOrEmpty()) {
            isInitial = false
            search = historicSearch
        } else {
            isInitial = true
            search = getString(R.string.home_default_search)
        }
        callSearchFilm(isFromInitView = true)
    }

    private fun initObservables() {
        viewModel.searchFilmLiveData.observe(
            viewLifecycleOwner
        ) { getItems(it, contextActivity, search) }
    }

    private fun initListeners() {
        viewBinding.homeBtnSearch.setOnClickListener {
            callSearchFilm()
        }

        viewBinding.etMovieSearchHint.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) ||
                actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO
            ) {
                callSearchFilm()
                Keyboard.hideKeyboard(requireView())
            }
            false
        }

        viewBinding.ivMoviePoster.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl))
            intent.setDataAndType(Uri.parse(imageUrl), "image/png")
            startActivity(intent)
        }

        viewBinding.viewMovieEpisodesGuide.setOnClickListener {
            gotoEpisodeGuide()
        }
    }

    private fun gotoEpisodeGuide() {
        val shared = contextActivity.getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        val search = shared.getString(getString(R.string.PREF_SEARCH), "")

        if (search.isNullOrEmpty()) {
            contextActivity.intent.putExtra(
                getString(R.string.PREF_SEARCH),
                viewBinding.etMovieSearchHint.text.toString().trim()
            )
        } else {
            contextActivity.intent.putExtra(getString(R.string.PREF_SEARCH), search)
        }

        (activity as HomeActivity).setFragment(EpisodesFragment())
    }

    private fun callSearchFilm(isFromInitView: Boolean = false) {
        if (isFromInitView) {
            viewModel.searchFilm(search, HomeActivity.getApiKey())
        } else {
            if (viewBinding.etMovieSearchHint.text.toString().trim().isEmpty()) {
                viewBinding.constraintHomeResult.visibility = View.GONE
                viewBinding.tvHomeMessageError.apply {
                    visibility = View.VISIBLE
                    text = resources.getString(R.string.home_message_error_searchField)
                }
            } else {
                viewBinding.tvHomeMessageError.visibility = View.GONE
                viewBinding.constraintHomeResult.visibility = View.VISIBLE
                isInitial = false
                viewModel.searchFilm(
                    viewBinding.etMovieSearchHint.text.toString().trim(),
                    HomeActivity.getApiKey()
                )
            }
        }
    }

    private fun getItems(film: Film, contextActivity: HomeActivity, search: String) {

        if (film.Response.toBoolean()) {
            viewBinding.tvHomeMessageError.visibility = View.GONE
            viewBinding.constraintHomeResult.visibility = View.VISIBLE

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
            viewBinding.tvMovieTitle.text = film.Title
            viewBinding.tvMovieImdbRating.text = film.imdbRating
            viewBinding.tvMovieImdbVotes.text = film.imdbVotes

            viewBinding.tvMovieType.text = setInitialCap(film.Type)
            viewBinding.tvMovieRuntime.text = film.Runtime
            viewBinding.tvMovieReleased.text = film.Released
            viewBinding.tvMovieYear.text = film.Year
            viewBinding.tvMovieCountry.text = film.Country

            Glide.with(contextActivity)
                .load(film.Poster)
                .into(viewBinding.ivMoviePoster)

            imageUrl = film.Poster

            viewBinding.tvMoviePlot.text = film.Plot
            viewBinding.tvMovieGenre.text = film.Genre
            viewBinding.tvMovieActors.text = film.Actors
            viewBinding.tvMovieWriters.text = film.Writer
            viewBinding.tvMovieAwards.text = film.Awards

            /**fazer separado o que for só de série e o q for só de filme */
            /**Série*/
            if (film.Type.equals("series", ignoreCase = true)) {
                viewBinding.groupShowOnlySeries.visibility = View.VISIBLE
                viewBinding.groupShowOnlyMovie.visibility = View.GONE
                viewBinding.groupShowBoxOffice.visibility = View.GONE
                viewBinding.groupShowProduction.visibility = View.GONE

                viewBinding.tvMovieTotalSeasons.text =
                    getString(R.string.seasons_count, film.totalSeasons)

            } else {
                /**Filme*/
                viewBinding.groupShowOnlySeries.visibility = View.GONE
                viewBinding.groupShowOnlyMovie.visibility = View.VISIBLE

                viewBinding.tvMovieDirector.text = film.Director

                if (!film.BoxOffice.equals("N/A", ignoreCase = true)) {
                    viewBinding.groupShowBoxOffice.visibility = View.VISIBLE
                    viewBinding.tvMovieBoxOffice.text = film.BoxOffice
                }

                if (!film.Production.equals("N/A", ignoreCase = true)) {
                    viewBinding.groupShowProduction.visibility = View.VISIBLE
                    viewBinding.tvMovieProduction.text = film.Production
                }
            }
        } else {
            viewBinding.tvHomeMessageError.apply {
                visibility = View.VISIBLE
                text = resources.getString(R.string.home_message_error_searchResult)
            }
            viewBinding.constraintHomeResult.visibility = View.GONE
        }
    }

    private fun setInitialCap(text: String): String {
        val aux = text.substring(1, text.length)
        return text.first().uppercaseChar() + aux
    }
}
