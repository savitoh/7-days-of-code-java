package com.github.savitoh.marvel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savitoh.Content;
import com.github.savitoh.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SeriesJsonParser implements JsonParser {
  private final String jsonSeries;

  public SeriesJsonParser(String json) {
    Objects.requireNonNull(json, "'json' cannot be null.");
    this.jsonSeries = json;
  }

  @Override
  public List<Content> parse() throws JsonProcessingException {
    final JsonNode seriesHolderNode = new ObjectMapper().readTree(jsonSeries);
    final JsonNode dataNode = seriesHolderNode.get("data");
    final Iterator<JsonNode> seriesNodes = dataNode.get("results").elements();
    final List<Content> series = new ArrayList<>();
    while (seriesNodes.hasNext()) {
      JsonNode seriesNode = seriesNodes.next();
      series.add(parseSeriesNode(seriesNode));
    }
    return series;
  }

  private Series parseSeriesNode(JsonNode seriesNode) {
    final var title = seriesNode.get("title").asText();
    final var urlImage = extractUrlImage(seriesNode.get("thumbnail"));
    final var rating = seriesNode.get("rating").asText();
    final var year = seriesNode.get("startYear").asText();
    return new Series(title, urlImage, rating, year);
  }

  private String extractUrlImage(JsonNode thumbnailNode) {
    return thumbnailNode.get("path").asText() + "." + thumbnailNode.get("extension").asText();
  }
}
