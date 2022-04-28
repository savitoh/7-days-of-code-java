package com.github.savitoh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParserJsonMoviesTest {

  private static final String jsonMovie =
      """
          {
              "items": [
                  {
                      "id": "tt0111161",
                      "rank": "1",
                      "title": "The Shawshank Redemption",
                      "fullTitle": "The Shawshank Redemption (1994)",
                      "year": "1994",
                      "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                      "crew": "Frank Darabont (dir.), Tim Robbins, Morgan Freeman",
                      "imDbRating": "9.2",
                      "imDbRatingCount": "2574700"
                  }
              ],
              "errorMessage": ""
          }"""
          .stripIndent();
  private static final String jsonTwoMovies =
      """
          {
              "items": [
                  {
                      "id": "tt0111161",
                      "rank": "1",
                      "title": "The Shawshank Redemption",
                      "fullTitle": "The Shawshank Redemption (1994)",
                      "year": "1994",
                      "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                      "crew": "Frank Darabont (dir.), Tim Robbins, Morgan Freeman",
                      "imDbRating": "9.2",
                      "imDbRatingCount": "2574700"
                  },
                  {
                      "id": "tt0068646",
                      "rank": "2",
                      "title": "The Godfather",
                      "fullTitle": "The Godfather (1972)",
                      "year": "1972",
                      "image": "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UX128_CR0,1,128,176_AL_.jpg",
                      "crew": "Francis Ford Coppola (dir.), Marlon Brando, Al Pacino",
                      "imDbRating": "9.2",
                      "imDbRatingCount": "1772902"
                  }
              ],
              "errorMessage": ""
          }"""
          .stripIndent();

  public static Stream<Arguments> jsonMapMovies() {
    return Stream.of(
        arguments(
            jsonMovie,
            List.of(
                new Movie(
                    "The Shawshank Redemption",
                    "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                    9.2,
                    1994))),
        arguments(
            jsonTwoMovies,
            List.of(
                new Movie(
                    "The Shawshank Redemption",
                    "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                    9.2,
                    1994),
                new Movie(
                    "The Godfather",
                    "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UX128_CR0,1,128,176_AL_.jpg",
                    9.2,
                    1972))));
  }

  @Test
  void Should_ThrowNPE_When_InitializeWithJsonNull() {
    assertThrows(
        NullPointerException.class, () -> new ParserJsonMovies(null), "'json' cannot be null.");
  }

  @Test
  @Disabled("Waiting for: valid json movie format")
  void Should_ThrowIllegalArgumentException_When_InitializeWithJsonNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new ParserJsonMovies("{}"),
        "'json' does not follow structure expected.");
  }

  @ParameterizedTest
  @MethodSource("jsonMapMovies")
  void Should_ParseMovies_When_JsonMovieIsValid(String jsonMovie, List<Movie> moviesExpected) {
    final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

    final List<Movie> movies = parserJsonMovies.parse();

    assertEquals(moviesExpected, movies);
  }
}
