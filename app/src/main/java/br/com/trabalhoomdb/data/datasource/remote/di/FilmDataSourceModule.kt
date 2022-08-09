package br.com.trabalhoomdb.data.datasource.remote.di

import br.com.trabalhoomdb.data.datasource.remote.abs.FilmRemoteDataSource
import br.com.trabalhoomdb.data.datasource.remote.impl.FilmRemoteDataSourceImpl
import org.koin.dsl.module

fun getFilmDataSourceModules() = module {
    factory<FilmRemoteDataSource> {
        FilmRemoteDataSourceImpl(serviceFilmSearch = get())
    }
}