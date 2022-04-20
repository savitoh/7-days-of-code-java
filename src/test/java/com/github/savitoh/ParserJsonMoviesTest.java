package com.github.savitoh;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ParserJsonMoviesTest {


    private static final String jsonMovie = """
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
                ]
            }""";
    private static final String jsonTwoMovies = """
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
                ]
            }""";


    @Test
    void Should_ThrowNPE_When_InitializeWithJsonNull() {
        assertThrows(NullPointerException.class, () -> new ParserJsonMovies(null), "'json' cannot be null.");
    }

    @Test
    @Disabled("Waiting for: valid json movie format")
    void Should_ThrowIllegalArgumentException_When_InitializeWithJsonNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new ParserJsonMovies("{}"),
                "'json' does not follow structure expected."
        );
    }

    @ParameterizedTest
    @MethodSource("getIds")
    void Should_ParseMoviesIds_When_JsonMovieIsValid(String jsonMovie, List<String> idsExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> ids = parserJsonMovies.getIds();

        assertEquals(idsExpected, ids);
    }

    @ParameterizedTest
    @MethodSource("getRanks")
    void Should_ParseMoviesRanks_When_JsonMovieIsValid(String jsonMovie, List<String> ranksExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> ranks = parserJsonMovies.getRanks();

        assertEquals(ranksExpected, ranks);
    }

    @ParameterizedTest
    @MethodSource("getTitles")
    void Should_ParseMoviesTitles_When_JsonMovieIsValid(String jsonMovie, List<String> titlesExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> titles = parserJsonMovies.getTitles();

        assertEquals(titlesExpected, titles);
    }

    @ParameterizedTest
    @MethodSource("getFullTitles")
    void Should_ParseMoviesFullTitles_When_JsonMovieIsValid(String jsonMovie, List<String> fullTitlesExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> fullTitles = parserJsonMovies.getFullTitles();

        assertEquals(fullTitlesExpected, fullTitles);
    }

    @ParameterizedTest
    @MethodSource("getYears")
    void Should_ParseMoviesYears_When_JsonMovieIsValid(String jsonMovie, List<String> yearsExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> years = parserJsonMovies.getYears();

        assertEquals(yearsExpected, years);
    }

    @ParameterizedTest
    @MethodSource("getImages")
    void Should_ParseMoviesImages_When_JsonMovieIsValid(String jsonMovie, List<String> imagesExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> images = parserJsonMovies.getImages();

        assertEquals(imagesExpected, images);
    }

    @ParameterizedTest
    @MethodSource("getCrews")
    void Should_ParseMoviesCrews_When_JsonMovieIsValid(String jsonMovie, List<String> crewsExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> crews = parserJsonMovies.getCrews();

        assertEquals(crewsExpected, crews);
    }

    @ParameterizedTest
    @MethodSource("getImDbRatings")
    void Should_ParseMoviesImDbRatings_When_JsonMovieIsValid(String jsonMovie, List<String> imDbRatingsExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> imDbRatings = parserJsonMovies.getImDbRatings();

        assertEquals(imDbRatingsExpected, imDbRatings);
    }

    @ParameterizedTest
    @MethodSource("getImDbRatingsCount")
    void Should_ParseMoviesImDbRatingsCount_When_JsonMovieIsValid(String jsonMovie, List<String> imDbRatingsCountExpected) {
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(jsonMovie);

        final List<String> imDbRatingsCount = parserJsonMovies.getImDbRatingsCount();

        assertEquals(imDbRatingsCountExpected, imDbRatingsCount);
    }
    private static Stream<Arguments> getIds() {
        return Stream.of(
                arguments(jsonMovie, List.of("tt0111161")),
                arguments(jsonTwoMovies, List.of("tt0111161", "tt0068646"))
        );
    }

    private static Stream<Arguments> getRanks() {
        return Stream.of(
                arguments(jsonMovie, List.of("1")),
                arguments(jsonTwoMovies, List.of("1", "2"))
        );
    }

    public static Stream<Arguments> getTitles() {
        return Stream.of(
                arguments(jsonMovie, List.of("The Shawshank Redemption")),
                arguments(jsonTwoMovies, List.of("The Shawshank Redemption", "The Godfather"))
        );
    }

    public static Stream<Arguments> getFullTitles() {
        return Stream.of(
                arguments(jsonMovie, List.of("The Shawshank Redemption (1994)")),
                arguments(jsonTwoMovies, List.of("The Shawshank Redemption (1994)", "The Godfather (1972)"))
        );
    }

    public static Stream<Arguments> getYears() {
        return Stream.of(
                arguments(jsonMovie, List.of("1994")),
                arguments(jsonTwoMovies, List.of("1994", "1972"))
        );
    }

    public static Stream<Arguments> getImages() {
        return Stream.of(
                arguments(jsonMovie, List.of("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg")),
                arguments(jsonTwoMovies, List.of("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,3,128,176_AL_.jpg", "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UX128_CR0,1,128,176_AL_.jpg"))
        );
    }

    public static Stream<Arguments> getCrews() {
        return Stream.of(
                arguments(jsonMovie, List.of("Frank Darabont (dir.), Tim Robbins, Morgan Freeman")),
                arguments(jsonTwoMovies, List.of("Frank Darabont (dir.), Tim Robbins, Morgan Freeman", "Francis Ford Coppola (dir.), Marlon Brando, Al Pacino"))
        );
    }

    public static Stream<Arguments> getImDbRatings() {
        return Stream.of(
                arguments(jsonMovie, List.of("9.2")),
                arguments(jsonTwoMovies, List.of("9.2", "9.2"))
        );
    }

    public static Stream<Arguments> getImDbRatingsCount() {
        return Stream.of(
                arguments(jsonMovie, List.of("2574700")),
                arguments(jsonTwoMovies, List.of("2574700", "1772902"))
        );
    }

}