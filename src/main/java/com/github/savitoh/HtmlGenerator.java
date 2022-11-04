package com.github.savitoh;

import com.github.savitoh.content.Content;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HtmlGenerator {

  private static final String HTML_TEMPLATE =
      """
                <!doctype html>
                <html lang="en-us">
                    %s
                    %s
                </html>
            """
          .stripIndent();

  private static final String HEAD =
      """
                    <head>
                        <title>7 days of code JAVA</title>
                        <meta charset="utf-8">
                        <meta name="HandheldFriendly" content="True">
                        <meta name="description" content="7 days of code Java">
                        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                    </head>
            """
          .stripIndent();

  private static final String BODY_TEMPLATE =
      """
               <body>
                    <main>
                    <div class="container-fluid">
                      <div class="row">
                        %s
                      </div>
                    </div>
                    </main>
               </body>
            """
          .stripIndent();

  private static final String CONTENT_CARD_TEMPLATE =
      """
                <div class="col-6 col-md-4 col-lg-3 mb-4">
                  <div class="card text-white bg-dark mb-3"
                       style="max-width: 18rem;">
                      <h4 class="card-header"
                          style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
                        %s
                      </h4>
                      <div class="card-body">
                          <h6 class="card-subtitle mb-2 text-muted">%s</h6>
                          <img class="card-img" src="%s" alt="%s">
                          <p class="card-text mt-2">Nota: %s - Ano: %s</p>
                      </div>
                  </div>
                </div>
            """
          .stripIndent();

  private final Writer writer;

  public HtmlGenerator(Writer writer) {
    Objects.requireNonNull(writer, "'writer' cannot be null.");
    this.writer = writer;
  }

  private String generateContentCards(List<Content> contents) {
    return contents.stream()
        .map(
            content ->
                CONTENT_CARD_TEMPLATE.formatted(
                    content.title(),
                    content.contentType().name(),
                    content.urlImage(),
                    content.title(),
                    content.rating(),
                    content.year()))
        .collect(Collectors.joining("\n"))
        .strip();
  }

  public void generate(List<Content> contents) throws IOException {
    Objects.requireNonNull(contents, "'contents' cannot be null.");
    final String movieCards = generateContentCards(contents);
    final String body = BODY_TEMPLATE.formatted(movieCards).strip();
    final String html = HTML_TEMPLATE.formatted(HEAD, body).strip();
    writer.write(html);
  }
}
