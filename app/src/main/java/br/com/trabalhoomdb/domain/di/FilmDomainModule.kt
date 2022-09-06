package br.com.trabalhoomdb.domain.di

import br.com.trabalhoomdb.domain.usecases.abs.FilmSearchUseCase
import br.com.trabalhoomdb.domain.usecases.abs.ListEpisodesUseCase
import br.com.trabalhoomdb.domain.usecases.impl.FilmSearchUseCaseImpl
import br.com.trabalhoomdb.domain.usecases.impl.ListEpisodesUseCaseImpl
import org.koin.dsl.module

fun getFilmDomainModules() = module {
    factory<FilmSearchUseCase> {
        FilmSearchUseCaseImpl(filmSearchRepository = get())
    }
    factory<ListEpisodesUseCase> {
        ListEpisodesUseCaseImpl(filmSearchRepository = get())
    }
}