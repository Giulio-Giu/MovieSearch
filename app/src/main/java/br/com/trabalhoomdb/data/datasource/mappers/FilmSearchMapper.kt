package br.com.trabalhoomdb.data.datasource.mappers

import br.com.trabalhoomdb.data.datasource.remote.modelresponse.FilmResponse
import br.com.trabalhoomdb.domain.models.Film

fun FilmResponse.toFilmSearchDomainModel() = Film(
    Title = Title.orEmpty(),
    Year = Year.orEmpty(),
    Released = Released.orEmpty(),
    Runtime = Runtime.orEmpty(),
    Genre = Genre.orEmpty(),
    Director = Director.orEmpty(),
    Writer = Writer.orEmpty(),
    Actors = Actors.orEmpty(),
    Plot = Plot.orEmpty(),
    Country = Country.orEmpty(),
    Awards = Awards.orEmpty(),
    Poster = Poster.orEmpty(),
    Ratings = Ratings?.toListRatingDomainModel().orEmpty(),
    imdbRating = imdbRating.orEmpty(),
    imdbVotes = imdbVotes.orEmpty(),
    Type = Type.orEmpty(),
    BoxOffice = BoxOffice.orEmpty(),
    Production = Production.orEmpty(),
    totalSeasons = totalSeasons.orEmpty(),
    Response = Response
)