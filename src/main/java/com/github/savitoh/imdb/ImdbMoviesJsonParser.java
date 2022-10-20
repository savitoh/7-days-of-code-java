package com.github.savitoh.imdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.savitoh.JsonParser;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbMoviesJsonParser implements JsonParser {

  private static final Pattern MOVIE_PATTERN =
      Pattern.compile("\"items\"\\s*:\\s*(\\[.*?\\]),", Pattern.DOTALL);
  private final ObjectMapper mapper =
      new ObjectMapper()
          .enable(SerializationFeature.INDENT_OUTPUT)
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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

  private String extractMovies() {
    return extractByMatcher(MOVIE_PATTERN.matcher(this.jsonMovies))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Does not possible extract 'movies'. Json passed:\n" + jsonMovies));
  }

  @Override
  public List<Movie> parse() throws JsonProcessingException {
    final var movies = extractMovies();
    return mapper.readValue(movies, new TypeReference<List<Movie>>() {});
  }
}
