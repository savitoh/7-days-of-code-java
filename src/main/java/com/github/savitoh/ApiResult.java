package com.github.savitoh;

import java.util.Optional;

public class ApiResult {

  private final String response;

  private final Throwable throwable;

  public ApiResult(String response, Throwable throwable) {
    this.response = response;
    this.throwable = throwable;
  }

  public Optional<String> getResponse() {
    return Optional.ofNullable(response);
  }

  public Optional<Throwable> getThrowable() {
    return Optional.ofNullable(throwable);
  }
}
