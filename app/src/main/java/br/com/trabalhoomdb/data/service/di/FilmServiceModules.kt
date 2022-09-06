package br.com.trabalhoomdb.data.service.di

import br.com.trabalhoomdb.common.utils.ServiceApi
import br.com.trabalhoomdb.data.service.abs.FilmSearchApi
import org.koin.dsl.module

fun getFilmServiceModules() = module {
    factory { ServiceApi().createService(FilmSearchApi::class.java) }
}