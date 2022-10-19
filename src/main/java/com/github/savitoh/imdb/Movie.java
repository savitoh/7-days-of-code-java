package com.github.savitoh.imdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.savitoh.Content;

public record Movie(
    String title,
    @JsonProperty("image") String urlImage,
    @JsonProperty("imDbRating") String rating,
    String year
) implements Content {}
