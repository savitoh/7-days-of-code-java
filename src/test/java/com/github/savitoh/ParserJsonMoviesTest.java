package com.github.savitoh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParserJsonMoviesTest {

  public static final String BASE_MESSAGE_ERROR = "Does not possible extract '%s'. Json passed:\n";
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

  private static Stream<Arguments> jsonWithItemsCollectionsErrorMapMessageError() {
    var jsonMovieMissingItemsProperty =
        """
                {
                    "title": "The Shawshank Redemption",
                    "year": "1994",
                    "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                    "imDbRating": "9.2"
                }"""
            .stripIndent();
    var jsonMovieWrongItemsProperty =
        """
                {
                    "item": [
                        {
                            "title": "The Shawshank Redemption",
                            "year": "1994",
                            "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                            "imDbRating": "9.2"
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();

    return Stream.of(
        arguments(jsonMovieMissingItemsProperty, BASE_MESSAGE_ERROR.formatted("movies")),
        arguments(jsonMovieWrongItemsProperty, BASE_MESSAGE_ERROR.formatted("movies")));
  }

  private static Stream<Arguments> jsonWithTitlePropertyErrorMapMessageError() {
    var jsonMovieMissingTitleProperty =
        """
                {
                    "items": [
                        {
                            "year": "1994",
                            "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                            "imDbRating": "9.2"
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();
    var jsonMovieWrongTitleProperty =
        """
                {
                    "items": [
                        {
                            "titl": "The Shawshank Redemption",
                            "year": "1994",
                            "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                            "imDbRating": "9.2"
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();

    return Stream.of(
        arguments(jsonMovieMissingTitleProperty, BASE_MESSAGE_ERROR.formatted("title")),
        arguments(jsonMovieWrongTitleProperty, BASE_MESSAGE_ERROR.formatted("title")));
  }

  private static Stream<Arguments> jsonWithImagePropertyErrorMapMessageError() {
    var jsonMovieMissingImageProperty =
        """
                {
                    "items": [
                        {
                            "title": "The Shawshank Redemption",
                            "year": "1994",
                            "imDbRating": "9.2"
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();
    var jsonMovieWrongImageProperty =
        """
                {
                    "items": [
                        {
                            "title": "The Shawshank Redemption",
                            "year": "1994",
                            "imag": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                            "imDbRating": "9.2"
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();

    return Stream.of(
        arguments(jsonMovieMissingImageProperty, BASE_MESSAGE_ERROR.formatted("image")),
        arguments(jsonMovieWrongImageProperty, BASE_MESSAGE_ERROR.formatted("image")));
  }

  private static Stream<Arguments> jsonWithRatingErrorMapMessageError() {
    var jsonMovieMissingRatingProperty =
        """
                {
                    "items": [
                        {
                            "title": "The Shawshank Redemption",
                            "year": "1994",
                            "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();
    var jsonMovieWrongRatingProperty =
        """
                {
                    "items": [
                        {
                            "title": "The Shawshank Redemption",
                            "year": "1994",
                            "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg",
                            "imDbRatin": "9.2"
                        }
                    ],
                    "errorMessage": ""
                }"""
            .stripIndent();

    return Stream.of(
        arguments(jsonMovieMissingRatingProperty, BASE_MESSAGE_ERROR.formatted("imDbRating")),
        arguments(jsonMovieWrongRatingProperty, BASE_MESSAGE_ERROR.formatted("imDbRating")));
  }

  private static Stream<Arguments> jsonMapMovies() {
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
    Exception exception =
        assertThrows(NullPointerException.class, () -> new ParserJsonMovies(null));

    assertEquals("'json' cannot be null.", exception.getMessage());
  }

  @ParameterizedTest
  @MethodSource({
    "jsonWithItemsCollectionsErrorMapMessageError",
    "jsonWithTitlePropertyErrorMapMessageError",
    "jsonWithImagePropertyErrorMapMessageError",
    "jsonWithRatingErrorMapMessageError"
  })
  void Should_IllegalArgumentException_When_JsonMovieDoesNotFollowExpectedStructure(
      String jsonMovie, String messageErrorExpected) {
    final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

    Exception exception = assertThrows(IllegalArgumentException.class, parserJsonMovies::parse);

    assertTrue(exception.getMessage().contains(messageErrorExpected));
  }

  @ParameterizedTest
  @MethodSource("jsonMapMovies")
  void Should_ParseMovies_When_JsonMovieIsValid(String jsonMovie, List<Movie> moviesExpected) {
    final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

    final List<Movie> movies = parserJsonMovies.parse();

    assertEquals(moviesExpected, movies);
  }
}
