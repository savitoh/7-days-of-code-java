package com.github.savitoh;

import com.github.savitoh.imdb.ImdbApiClient;
import com.github.savitoh.imdb.ImdbMoviesJsonParser;
import com.github.savitoh.imdb.Movie;
import java.io.FileWriter;
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {

    final String imdbTop250MoviesApiUri = "https://imdb-api.com/en/API/Top250Movies/";
    final String imdbApiKey = System.getenv("IMDB_API_KEY");
    final ImdbApiClient imdbApiClient = new ImdbApiClient(imdbTop250MoviesApiUri, imdbApiKey);

    final String top250MoviesJson = imdbApiClient.getBody();
    final List<Movie> movies = new ImdbMoviesJsonParser(top250MoviesJson).parse();
    try (final var writer = new FileWriter("movies.html")) {
      HtmlGenerator htmlGenerator = new HtmlGenerator(writer);
      htmlGenerator.generate(movies);
    }
  }
}
