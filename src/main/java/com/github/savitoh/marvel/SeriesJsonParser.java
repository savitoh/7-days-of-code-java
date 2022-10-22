package com.github.savitoh.marvel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savitoh.JsonParser;
import java.util.List;
import java.util.Objects;

public class SeriesJsonParser implements JsonParser {

  private final ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private final String jsonSeries;

  public SeriesJsonParser(String json) {
    Objects.requireNonNull(json, "'json' cannot be null.");
    this.jsonSeries = json;
  }

  @Override
  public List<Series> parse() throws JsonProcessingException {
    final var seriesWrapper = mapper.readValue(jsonSeries, SeriesWrapper.class);
    return seriesWrapper.getSeries();
  }

  private record SeriesWrapper(@JsonProperty("data") Data data) {
    public List<Series> getSeries() {
      return data.series;
    }

    private record Data(@JsonProperty("results") List<Series> series) {}
  }
}
