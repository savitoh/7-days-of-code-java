package com.github.savitoh;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    final String uri = "https://imdb-api.com/en/API/Top250Movies/";
    final String apiKey = System.getenv("IMDB_API_KEY");
    final ImdbApiClient imdbApiClient = new ImdbApiClient(uri, apiKey);

    final String top250MoviesJson = imdbApiClient.getBody();
    final List<Movie> movies = new ParserJsonMovies(top250MoviesJson).parse();
    try (final var writer = new FileWriter("movies.html")) {
      HtmlGenerator htmlGenerator = new HtmlGenerator(writer);
      htmlGenerator.generate(movies);
    }
  }
}
