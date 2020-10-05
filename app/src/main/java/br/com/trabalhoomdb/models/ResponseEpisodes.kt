package br.com.trabalhoomdb.models

data class ResponseEpisodes(
    var Season: String,
    var Episodes: List<Episode>,
    var Response: String
)