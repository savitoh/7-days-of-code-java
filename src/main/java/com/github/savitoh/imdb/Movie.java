package com.github.savitoh.imdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.savitoh.content.Content;
import com.github.savitoh.content.ContentType;

public record Movie(
    String title,
    @JsonProperty("image") String urlImage,
    @JsonProperty("imDbRating") String rating,
    String year)
    implements Content, Comparable<Content> {
  @Override
  public ContentType contentType() {
    return ContentType.MOVIE;
  }

  @Override
  public int compareTo(Content o) {
    return 0;
  }
}
