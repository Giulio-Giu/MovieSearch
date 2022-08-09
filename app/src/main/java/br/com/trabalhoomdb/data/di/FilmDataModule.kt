package br.com.trabalhoomdb.data.di

import br.com.trabalhoomdb.data.repository.FilmSearchRepositoryImpl
import br.com.trabalhoomdb.domain.repository.FilmSearchRepository
import org.koin.dsl.module

fun getFilmDataModules() = module {
    factory<FilmSearchRepository> {
        FilmSearchRepositoryImpl(filmDataSource = get())
    }
}