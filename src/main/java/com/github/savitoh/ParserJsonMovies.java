package com.github.savitoh;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserJsonMovies {

    private static final Pattern BASIC_MOVIES_JSON_STRUCTURE = Pattern.compile("\\s*\\{\\s*(.*?)\\s*}");

    private static final Pattern ID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern RANK_PATTERN = Pattern.compile("\"rank\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"title\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern FULL_TITLE_PATTERN = Pattern.compile("\"fullTitle\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern YEAR_PATTERN = Pattern.compile("\"year\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern IMAGE_PATTERN = Pattern.compile("\"image\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern CREW_PATTERN = Pattern.compile("\"crew\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern IMDB_RATING_PATTERN = Pattern.compile("\"imDbRating\"\\s*:\\s*\"(.*?)\",");
    private static final Pattern IMDB_RATING_COUNT_PATTERN = Pattern.compile("\"imDbRatingCount\"\\s*:\\s*\"(.*?)\"");


    private final String jsonMovies;

    /**
     * TODO valid json format
     */
    public ParserJsonMovies(final String json) {
        Objects.requireNonNull(json, "'json' cannot be null.");
//        if (!BASIC_MOVIES_JSON_STRUCTURE.matcher(json).find()) {
//            throw new IllegalArgumentException("'json' does not follow structure expected.");
//        }
        this.jsonMovies = json;
    }

    public List<String> extractFromMatcher(final Matcher matcher) {
        return matcher.results().map(matchResult -> matchResult.group(1)).toList();
    }

    public List<String> getIds() {
        return this.extractFromMatcher(ID_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getRanks() {
        return this.extractFromMatcher(RANK_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getTitles() {
        return this.extractFromMatcher(TITLE_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getFullTitles() {
        return this.extractFromMatcher(FULL_TITLE_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getYears() {
        return this.extractFromMatcher(YEAR_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getImages() {
        return this.extractFromMatcher(IMAGE_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getCrews() {
        return this.extractFromMatcher(CREW_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getImDbRatings() {
        return this.extractFromMatcher(IMDB_RATING_PATTERN.matcher(this.jsonMovies));
    }

    public List<String> getImDbRatingsCount() {
        return this.extractFromMatcher(IMDB_RATING_COUNT_PATTERN.matcher(this.jsonMovies));
    }

}
