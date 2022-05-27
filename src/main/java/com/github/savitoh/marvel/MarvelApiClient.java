package com.github.savitoh.marvel;

import com.github.savitoh.ApiClient;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Objects;

public class MarvelApiClient implements ApiClient {

  private final HttpRequest httpRequest;

  private final HttpClient httpClient;

  public MarvelApiClient(
      final String baseUri, final String publicApiKey, final String privateApiKey)
      throws NoSuchAlgorithmException {
    Objects.requireNonNull(baseUri, "'baseUri' cannot be null.");
    Objects.requireNonNull(publicApiKey, "'publicApiKey' cannot be null.");
    Objects.requireNonNull(privateApiKey, "'privateApiKey' cannot be null.");
    final String uri = new UriBuilder(baseUri, publicApiKey, privateApiKey).builder();
    this.httpRequest = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
    this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMinutes(1)).build();
  }

  @Override
  public String getBody() throws IOException, InterruptedException {
    HttpResponse<String> response =
        this.httpClient.send(this.httpRequest, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
