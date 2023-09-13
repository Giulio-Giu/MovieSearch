package br.com.trabalhoomdb.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.trabalhoomdb.domain.models.Film
import br.com.trabalhoomdb.domain.models.ListEpisodes
import br.com.trabalhoomdb.domain.usecases.abs.FilmSearchUseCase
import br.com.trabalhoomdb.domain.usecases.abs.ListEpisodesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val filmSearchUseCase: FilmSearchUseCase,
    private val listEpisodesUseCase: ListEpisodesUseCase
) : ViewModel() {

    private val searchFilmMutableLiveData = MutableLiveData<Film>()
    val searchFilmLiveData: LiveData<Film>
        get() = searchFilmMutableLiveData

    private val listEpisodesMutableLiveData = MutableLiveData<ListEpisodes>()
    val listEpisodesLiveData: LiveData<ListEpisodes>
        get() = listEpisodesMutableLiveData

    fun searchFilm(search: String, apiKey: String) {
        viewModelScope.launch {
            val filmResult = filmSearchUseCase.invoke(search, apiKey)
            searchFilmMutableLiveData.postValue(filmResult)
        }
    }

    fun listEpisodes(search: String, season: String, apiKey: String) {
        viewModelScope.launch {
            val listEpisodes = listEpisodesUseCase.invoke(search, season, apiKey)
            listEpisodesMutableLiveData.postValue(listEpisodes)
        }
    }
}