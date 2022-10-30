package com.github.savitoh.imdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.savitoh.Content;
import com.github.savitoh.ContentType;

public record Movie(
    String title,
    @JsonProperty("image") String urlImage,
    @JsonProperty("imDbRating") String rating,
    String year)
    implements Content {
  @Override
  public ContentType contentType() {
    return ContentType.MOVIE;
  }
}
