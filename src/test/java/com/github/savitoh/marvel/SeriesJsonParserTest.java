package com.github.savitoh.marvel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.savitoh.Content;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class SeriesJsonParserTest {

  @Test
  void Should_ParseSeries_WhenJsonIsValid() throws IOException {
    var jsonSeries =
        new String(getClass().getClassLoader().getResourceAsStream("series.json").readAllBytes());
    SeriesJsonParser jsonParser = new SeriesJsonParser(jsonSeries);

    List<Content> series = jsonParser.parse();

    assertEquals(20, series.size());
  }
}
