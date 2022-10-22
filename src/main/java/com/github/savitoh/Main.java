package com.github.savitoh;

import com.github.savitoh.imdb.ImdbApiClient;
import com.github.savitoh.imdb.ImdbMoviesJsonParser;
import com.github.savitoh.imdb.Movie;
import com.github.savitoh.marvel.MarvelApiClient;
import com.github.savitoh.marvel.Series;
import com.github.savitoh.marvel.SeriesJsonParser;
import java.io.FileWriter;
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {

    final String imdbTop250MoviesApiUri = "https://imdb-api.com/en/API/Top250Movies/";
    final String imdbApiKey = System.getenv("IMDB_API_KEY");
    final ImdbApiClient imdbApiClient = new ImdbApiClient(imdbTop250MoviesApiUri, imdbApiKey);

    final String seriesMarvelUri = "https://gateway.marvel.com:443/v1/public/series";
    final String marvelPublicApiKey = System.getenv("MARVEL_PUBLIC_API_KEY");
    final String marvelPrivateApiKey = System.getenv("MARVEL_PRIVATE_API_KEY");
    final MarvelApiClient marvelApiClient =
        new MarvelApiClient(seriesMarvelUri, marvelPublicApiKey, marvelPrivateApiKey);

    final String seriesJson = marvelApiClient.getBody();
    final List<Series> series = new SeriesJsonParser(seriesJson).parse();
    final String top250MoviesJson = imdbApiClient.getBody();
    final List<Movie> movies = new ImdbMoviesJsonParser(top250MoviesJson).parse();

    try (final var writer = new FileWriter("movies.html")) {
      HtmlGenerator htmlGenerator = new HtmlGenerator(writer);
      htmlGenerator.generate(movies);
    }
  }
}
