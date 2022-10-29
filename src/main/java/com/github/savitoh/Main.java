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
                      seriesResult
                          .getResponse()
                          .map(
                              seriesJson -> {
                                try {
                                  return new SeriesJsonParser(seriesJson).parse();
                                } catch (JsonProcessingException e) {
                                  throw new RuntimeException(e);
                                }
                              })
                          .orElseGet(
                              () -> {
                                LOGGER.log(
                                    Level.SEVERE,
                                    "An error occurred during Marvel API access.",
                                    seriesResult.getThrowable().get());
                                return List.of();
                              });

                  final List<Content> movies =
                      moviesResult
                          .getResponse()
                          .map(
                              moviesJson -> {
                                try {
                                  return new ImdbMoviesJsonParser(moviesJson).parse();
                                } catch (JsonProcessingException e) {
                                  throw new RuntimeException(e);
                                }
                              })
                          .orElseGet(
                              () -> {
                                LOGGER.log(
                                    Level.SEVERE,
                                    "An error occurred during IMDB API access.",
                                    moviesResult.getThrowable().get());
                                return List.of();
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
