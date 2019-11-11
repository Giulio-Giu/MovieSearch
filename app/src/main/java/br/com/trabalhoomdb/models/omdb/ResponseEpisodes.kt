package br.com.trabalhoomdb.models.omdb

data class ResponseEpisodes(
    var Season: String,
    var Episodes: List<Episode>,
    var Response: String
)