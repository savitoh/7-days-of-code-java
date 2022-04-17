package com.github.savitoh;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ImdbClient {

    private static final String IMDB_API = "https://imdb-api.com/en/API/Top250Movies/";
    private static final String IMDB_API_KEY = System.getenv("IMDB_API_KEY");

    private final HttpRequest httpRequest;

    private final HttpClient httpClient;

    public ImdbClient() {
        this.httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(IMDB_API + IMDB_API_KEY))
                .timeout(Duration.ofMinutes(1))
                .header("Accept", "application/json")
                .GET()
                .build();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMinutes(1))
                .build();
    }
    public String getTop250Movies() throws IOException, InterruptedException {
        HttpResponse<String> response = this.httpClient.send(this.httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
