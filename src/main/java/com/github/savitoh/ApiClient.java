package com.github.savitoh;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface ApiClient {

  String getBody() throws IOException, InterruptedException;

  CompletableFuture<ApiResult> getBodyAsync();
}
