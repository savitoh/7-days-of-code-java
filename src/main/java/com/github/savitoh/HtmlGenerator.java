package com.github.savitoh;

import com.github.savitoh.imdb.Movie;
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
                        <meta charset="utf-8">
                        <meta name="HandheldFriendly" content="True">
                        <meta name="description" content="7 days of code - Java (IMDB Challenge)">
                        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                    </head>
            """
          .stripIndent();

  private static final String BODY_TEMPLATE =
      """
               <body>
                    <main>
                        %s
                    </main>
               </body>
            """
          .stripIndent();

  private static final String MOVIE_CARD_TEMPLATE =
      """
                <div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
                    <h4 class="card-header">%s</h4>
                    <div class="card-body">
                        <img class="card-img" src="%s" alt="%s">
                        <p class="card-text mt-2">Nota: %s - Ano: %s</p>
                    </div>
                </div>
            """
          .stripIndent();

  private final Writer writer;

  public HtmlGenerator(Writer writer) {
    Objects.requireNonNull(writer, "'writer' cannot be null.");
    this.writer = writer;
  }

  private String generateMovieCards(List<Movie> movies) {
    return movies.stream()
        .map(
            movie ->
                MOVIE_CARD_TEMPLATE.formatted(
                    movie.title(), movie.urlImage(), movie.title(), movie.rating(), movie.year()))
        .collect(Collectors.joining("\n"))
        .strip();
  }

  public void generate(List<Movie> movies) throws IOException {
    Objects.requireNonNull(movies, "'movies' cannot be null.");
    final var movieCards = generateMovieCards(movies);
    final var body = BODY_TEMPLATE.formatted(movieCards).strip();
    final var html = HTML_TEMPLATE.formatted(HEAD, body).strip();
    writer.write(html);
  }
}
