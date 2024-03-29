package com.github.savitoh;

import com.github.savitoh.content.Content;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiResult {

  private static final Logger LOGGER = Logger.getLogger(ApiResult.class.getName());
  private final String response;

  private final Throwable throwable;

  public ApiResult(String response, Throwable throwable) {
    this.response = response;
    this.throwable = throwable;
  }

  public List<Content> parse(Function<String, List<Content>> parser) {
    Objects.requireNonNull(parser, "'parser' cannot be null.");
    if (Objects.isNull(response)) {
      LOGGER.log(Level.SEVERE, throwable.getMessage(), throwable);
      throw new RuntimeException("Can't parse JSON. 'response' is null.", throwable);
    }
    return parser.apply(response);
  }

  public Optional<String> getResponse() {
    return Optional.ofNullable(response);
  }

  public Optional<Throwable> getThrowable() {
    return Optional.ofNullable(throwable);
  }
}
