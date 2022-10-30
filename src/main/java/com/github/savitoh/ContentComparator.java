package com.github.savitoh;

import java.util.Comparator;

public class ContentComparator {

  private ContentComparator() {}

  private static final Comparator<Content> comparatorByType =
      Comparator.comparing(Content::contentType);

  private static final Comparator<Content> comparatorByRating =
      Comparator.comparing(Content::rating).reversed();

  private static final Comparator<Content> comparatorByYear = Comparator.comparing(Content::year);

  public static final Comparator<Content> comparator =
      comparatorByType.thenComparing(comparatorByRating).thenComparing(comparatorByYear);
}
