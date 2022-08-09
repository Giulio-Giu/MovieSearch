package br.com.trabalhoomdb.common.di

import br.com.trabalhoomdb.presentation.di.getHomeViewModelModules

class DIViewModelModules {
    fun getViewModelModules() = listOf(
        getHomeViewModelModules()
    )
}