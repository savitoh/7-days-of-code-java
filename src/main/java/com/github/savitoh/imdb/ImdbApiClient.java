package com.github.savitoh.imdb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;

public class ImdbApiClient {

  private final HttpRequest httpRequest;

  private final HttpClient httpClient;

  public ImdbApiClient(final String uri, final String apiKey) {
    Objects.requireNonNull(uri, "'uri' cannot be null.");
    Objects.requireNonNull(apiKey, "'apiKey' cannot be null.");
    this.httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(uri + apiKey))
            .timeout(Duration.ofMinutes(1))
            .header("Accept", "application/json")
            .GET()
            .build();
    this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMinutes(1)).build();
  }

  public String getBody() throws IOException, InterruptedException {
    HttpResponse<String> response =
        this.httpClient.send(this.httpRequest, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
