package br.com.trabalhoomdb.common.di

import br.com.trabalhoomdb.data.datasource.remote.di.getFilmDataSourceModules
import br.com.trabalhoomdb.data.di.getFilmDataModules
import br.com.trabalhoomdb.data.service.di.getFilmServiceModules

class DIDataModule {

    fun getRepositoryModules() = listOf(
        getFilmDataModules()
    )

    fun getDataSourceModules() = listOf(
        getFilmDataSourceModules()
    )

    fun getServicesModules() = listOf (
        getFilmServiceModules()
    )
}