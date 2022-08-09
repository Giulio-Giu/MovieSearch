package br.com.trabalhoomdb.presentation.di

import br.com.trabalhoomdb.presentation.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getHomeViewModelModules() = module {
    viewModel {
        HomeViewModel(filmSearchUseCase = get(), listEpisodesUseCase = get())
    }
}