package com.github.savitoh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.savitoh.imdb.ImdbApiClient;
import com.github.savitoh.imdb.ImdbMoviesJsonParser;
import com.github.savitoh.marvel.MarvelApiClient;
import com.github.savitoh.marvel.SeriesJsonParser;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) throws Exception {

    final String imdbTop250MoviesApiUri = "https://imdb-api.com/en/API/Top250Movies/";
    final String imdbApiKey = System.getenv("IMDB_API_KEY");
    final ApiClient imdbApiClient = new ImdbApiClient(imdbTop250MoviesApiUri, imdbApiKey);

    final String seriesMarvelUri = "https://gateway.marvel.com:443/v1/public/series";
    final String marvelPublicApiKey = System.getenv("MARVEL_PUBLIC_API_KEY");
    final String marvelPrivateApiKey = System.getenv("MARVEL_PRIVATE_API_KEY");
    final ApiClient marvelApiClient =
        new MarvelApiClient(seriesMarvelUri, marvelPublicApiKey, marvelPrivateApiKey);

    final CompletableFuture<ApiResult> seriesFuture = marvelApiClient.getBodyAsync();
    final CompletableFuture<ApiResult> moviesFuture = imdbApiClient.getBodyAsync();

    List<Content> contents =
        CompletableFuture.allOf(seriesFuture, moviesFuture)
            .thenApply(
                v -> {
                  var seriesResult = seriesFuture.join();
                  var moviesResult = moviesFuture.join();
                  final List<Content> series =
                      seriesResult.parse(
                          json -> {
                            try {
                              return new SeriesJsonParser(json).parse();
                            } catch (JsonProcessingException e) {
                              LOGGER.log(Level.SEVERE, "Parse JSON with error: \n{0}", json);
                              throw new RuntimeException(e);
                            }
                          });
                  final List<Content> movies =
                      moviesResult.parse(
                          json -> {
                            try {
                              return new ImdbMoviesJsonParser(json).parse();
                            } catch (JsonProcessingException e) {
                              LOGGER.log(Level.SEVERE, "Parse JSON with error: \n{0}", json);
                              throw new RuntimeException(e);
                            }
                          });
                  return Stream.concat(series.stream(), movies.stream()).toList();
                })
            .join();

    try (final var writer = new FileWriter("movies.html")) {
      HtmlGenerator htmlGenerator = new HtmlGenerator(writer);
      htmlGenerator.generate(contents);
    }
  }
}
