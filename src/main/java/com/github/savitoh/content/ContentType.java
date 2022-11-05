package com.github.savitoh.content;

public enum ContentType {
  MOVIE("content_type_movies"),
  SERIES("content_type_series");

  public final String tagTranslation;

  ContentType(String tagTranslation) {
    this.tagTranslation = tagTranslation;
  }
}
