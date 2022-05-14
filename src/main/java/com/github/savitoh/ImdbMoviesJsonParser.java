package com.github.savitoh;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbMoviesJsonParser implements JsonParser {

  private static final String MOVIE_SPLIT_PATTERN = "(?<=\\}),\\s*(?=\\{)";
  private static final Pattern MOVIE_PATTERN =
      Pattern.compile("\"items\"\\s*:\\s*(\\[.*?\\]),", Pattern.DOTALL);

  private static final Pattern TITLE_PATTERN = Pattern.compile("\"title\"\\s*:\\s*\"(.*?)\",");
  private static final Pattern IMAGE_PATTERN = Pattern.compile("\"image\"\\s*:\\s*\"(.*?)\",");
  private static final Pattern IMDB_RATING_PATTERN =
      Pattern.compile("\"imDbRating\"\\s*:\\s*\"(.*?)\",");
  private static final Pattern YEAR_PATTERN = Pattern.compile("\"year\"\\s*:\\s*\"(.*?)\",");

  private final String jsonMovies;

  public ImdbMoviesJsonParser(final String json) {
    Objects.requireNonNull(json, "'json' cannot be null.");
    this.jsonMovies = json;
  }

  private Optional<String> extractByMatcher(final Matcher matcher) {
    if (matcher.find()) {
      return Optional.of(matcher.group(1));
    }
    return Optional.empty();
  }

  private String getTitle(final String jsonMovie) {
    return this.extractByMatcher(TITLE_PATTERN.matcher(jsonMovie))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Does not possible extract 'title'. Json passed:\n" + jsonMovie));
  }

  private String getYear(final String jsonMovie) {
    return this.extractByMatcher(YEAR_PATTERN.matcher(jsonMovie))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Does not possible extract 'year'. Json passed:\n" + jsonMovie));
  }

  private String getImage(final String jsonMovie) {
    return this.extractByMatcher(IMAGE_PATTERN.matcher(jsonMovie))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Does not possible extract 'image'. Json passed:\n" + jsonMovie));
  }

  private String getImDbRating(final String jsonMovie) {
    return this.extractByMatcher(IMDB_RATING_PATTERN.matcher(jsonMovie))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Does not possible extract 'imDbRating'. Json passed:\n" + jsonMovie));
  }

  private List<String> extractMovies() {
    final var movies =
        extractByMatcher(MOVIE_PATTERN.matcher(this.jsonMovies))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Does not possible extract 'movies'. Json passed:\n" + jsonMovies));
    return List.of(movies.split(MOVIE_SPLIT_PATTERN));
  }

  @Override
  public List<Movie> parse() {
    final var movies = extractMovies();
    return movies.stream()
        .map(item -> new Movie(getTitle(item), getImage(item), getImDbRating(item), getYear(item)))
        .toList();
  }
}
