package com.github.savitoh;

import static org.junit.jupiter.api.Assertions.*;

import com.github.savitoh.content.Content;
import com.github.savitoh.content.ContentComparator;
import com.github.savitoh.imdb.Movie;
import com.github.savitoh.marvel.Series;
import java.util.List;
import org.junit.jupiter.api.Test;

class ContentComparatorTest {

  @Test
  void testSorted() {
    List<Content> contents =
        List.of(
            new Series("1602 Witch Hunter Angela (2015)", "", "10", "2015"),
            new Series("15 Love (2011)", "", "", "2011"),
            new Movie("The Shawshank Redemption", "", "9.2", "1994"),
            new Series("1602 (2003 - 2004)", "", "Marvel Psr", "2003"),
            new Movie("The Godfather", "", "9.2", "1972"));

    var sortedContents = contents.stream().sorted(ContentComparator.comparator).toList();

    List<Content> sortedContentsExpected =
        List.of(
            new Movie("The Godfather", "", "9.2", "1972"),
            new Movie("The Shawshank Redemption", "", "9.2", "1994"),
            new Series("1602 (2003 - 2004)", "", "Marvel Psr", "2003"),
            new Series("1602 Witch Hunter Angela (2015)", "", "10", "2015"),
            new Series("15 Love (2011)", "", "", "2011"));

    assertEquals(sortedContentsExpected, sortedContents);
  }
}
