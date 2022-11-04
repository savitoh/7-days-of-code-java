package com.github.savitoh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.savitoh.content.Content;
import com.github.savitoh.imdb.ImdbMoviesJsonParser;
import com.github.savitoh.marvel.SeriesJsonParser;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApiResultTest {

  @Test
  void Should_ParseJsonToContent_When_HasPayloadAndParserUnderstandJson() {
    var imdbMoviesJson =
        """
                {
                  "items": [
                    {
                      "id": "tt0111161",
                      "rank": "1",
                      "title": "The Shawshank Redemption",
                      "fullTitle": "The Shawshank Redemption (1994)",
                      "year": "1994",
                      "image": "",
                      "crew": "Frank Darabont (dir.), Tim Robbins, Morgan Freeman",
                      "imDbRating": "9.2",
                      "imDbRatingCount": "2574700"
                    }
                  ],
                  "errorMessage": ""
                }
                """;
    Function<String, List<Content>> movieJsonParser =
        json -> {
          try {
            return new ImdbMoviesJsonParser(json).parse();
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        };
    ApiResult apiResult = new ApiResult(imdbMoviesJson, null);

    List<Content> contents = apiResult.parse(movieJsonParser);

    assertEquals(1, contents.size());
  }

  @Test
  void Should_TrowsException_When_PayloadIsNull() {
    Function<String, List<Content>> movieJsonParser =
        json -> {
          try {
            return new ImdbMoviesJsonParser(json).parse();
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        };
    ApiResult apiResult =
        new ApiResult(null, new RuntimeException("An error occurred during IMDB API access."));

    RuntimeException runtimeException =
        Assertions.assertThrows(RuntimeException.class, () -> apiResult.parse(movieJsonParser));

    assertEquals("Can't parse JSON. 'response' is null.", runtimeException.getMessage());
  }

  @Test
  void Should_ParseJsonToContent_When_HasPayloadAndParserDoesntUnderstandJson() {
    var seriesJson =
        """
                {
                  "items": [
                    {
                      "id": "tt0111161",
                      "rank": "1",
                      "title": "The Shawshank Redemption",
                      "fullTitle": "The Shawshank Redemption (1994)",
                      "year": "1994",
                      "image": "",
                      "crew": "Frank Darabont (dir.), Tim Robbins, Morgan Freeman",
                      "imDbRating": "9.2",
                      "imDbRatingCount": "2574700"
                    }
                  ],
                  "errorMessage": ""
                }""";
    Function<String, List<Content>> movieJsonParser =
        json -> {
          try {
            return new SeriesJsonParser(json).parse();
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        };
    ApiResult apiResult = new ApiResult(seriesJson, null);

    RuntimeException runtimeException =
        Assertions.assertThrows(RuntimeException.class, () -> apiResult.parse(movieJsonParser));

    assertEquals(
        "Cannot invoke \"com.fasterxml.jackson.databind.JsonNode.get(String)\" because \"dataNode\" is null",
        runtimeException.getMessage());
  }
}
