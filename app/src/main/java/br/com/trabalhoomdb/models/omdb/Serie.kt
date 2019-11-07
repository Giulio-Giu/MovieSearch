package br.com.trabalhoomdb.models.omdb

import br.com.trabalhoomdb.models.omdb.Rating

data class Serie(
    var Title: String,
    var Year: String,
    var Released: String,
    var Runtime: String,
    var Genre: String,
    var Director: String,
    var Writer: String,
    var Actors: String,
    var Plot: String,
    var Country: String,
    var Awards: String,
    var Poster: String,
    var Ratings: List<Rating>,
    var imdbRating: String,
    var imdbVotes: String,
    var totalSeasons: String,
    var Response: String
)