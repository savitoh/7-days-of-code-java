package com.github.savitoh.marvel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.savitoh.Content;

public record Series(
    String title,
    @JsonProperty("image") String urlImage,
    @JsonProperty("rating") String rating,
    @JsonProperty("startYear") String year)
    implements Content {}
