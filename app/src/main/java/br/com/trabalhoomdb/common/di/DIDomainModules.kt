package br.com.trabalhoomdb.common.di

import br.com.trabalhoomdb.domain.di.getFilmDomainModules

class DIDomainModules {
    fun getDomainModules() = listOf(
        getFilmDomainModules()
    )
}