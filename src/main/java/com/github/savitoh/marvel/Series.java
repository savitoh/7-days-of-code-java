package com.github.savitoh.marvel;

import com.github.savitoh.content.Content;
import com.github.savitoh.content.ContentType;

public record Series(String title, String urlImage, String rating, String year)
    implements Content, Comparable<Content> {
  @Override
  public ContentType contentType() {
    return ContentType.SERIES;
  }

  @Override
  public int compareTo(Content other) {
    return 0;
  }
}
