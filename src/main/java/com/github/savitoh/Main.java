package com.github.savitoh;

import com.github.savitoh.http.imdb.ImdbApiClient;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    final String imdbTop250MoviesUri = "https://imdb-api.com/en/API/Top250Movies/";
    final String imdbApiKey = System.getenv("IMDB_API_KEY");
    final ImdbApiClient imdbApiClient = new ImdbApiClient(imdbTop250MoviesUri, imdbApiKey);

    final String top250MoviesJson = imdbApiClient.getBody();
    final List<Movie> movies = new ImdbMoviesJsonParser(top250MoviesJson).parse();
    try (final var writer = new FileWriter("movies.html")) {
      HtmlGenerator htmlGenerator = new HtmlGenerator(writer);
      htmlGenerator.generate(movies);
    }
  }
}
