package br.com.trabalhoomdb.models.omdb

import br.com.trabalhoomdb.models.omdb.Episode

data class ResponseEpisodes(
    var Season: String,
    var Episodes: List<Episode>,
    var Response: String
)