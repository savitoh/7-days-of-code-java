package com.github.savitoh.imdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savitoh.JsonParser;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbMoviesJsonParser implements JsonParser {

  private static final Pattern MOVIE_PATTERN =
      Pattern.compile("\"items\"\\s*:\\s*(\\[.*?\\]),", Pattern.DOTALL);
  private final ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private final String jsonMovies;

  public ImdbMoviesJsonParser(final String json) {
    Objects.requireNonNull(json, "'json' cannot be null.");
    this.jsonMovies = json;
  }

  private String extractMovies() {
    Matcher matcher = MOVIE_PATTERN.matcher(this.jsonMovies);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      throw new IllegalArgumentException(
          "Does not possible extract 'movies'. Json passed:\n" + this.jsonMovies);
    }
  }

  @Override
  public List<Movie> parse() throws JsonProcessingException {
    final var movies = extractMovies();
    return mapper.readValue(movies, new TypeReference<>() {});
  }
}
