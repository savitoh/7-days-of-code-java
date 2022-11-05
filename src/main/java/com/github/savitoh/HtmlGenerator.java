package com.github.savitoh;

import com.github.savitoh.content.Content;
import com.github.savitoh.internationalization.Translator;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Locale;
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
            """;

  private static final String HEAD =
      """
                    <head>
                        <title>%s</title>
                        <meta charset="utf-8">
                        <meta name="HandheldFriendly" content="True">
                        <meta name="description" content="%s">
                        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                        <script>
                          $(function () {
                            $('[data-toggle="tooltip"]').tooltip()
                          })
                        </script>
                    </head>
            """;

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
            """;

  private static final String CONTENT_CARD_TEMPLATE =
      """
                <div class="col-6 col-md-4 col-lg-3 mb-4">
                  <div class="card text-white bg-dark mb-3"
                       style="max-width: 18rem;">
                      <h4 class="card-header"
                          style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;"
                          data-toggle="tooltip" data-placement="right" title="%s">
                        %s
                      </h4>
                      <div class="card-body">
                          <h6 class="card-subtitle mb-2 text-muted">%s</h6>
                          <img class="card-img" src="%s" alt="%s">
                          <p class="card-text mt-2">%s: %s - %s: %s</p>
                      </div>
                  </div>
                </div>
            """;

  private final Writer writer;
  private final Translator translator;

  public HtmlGenerator(Writer writer, Translator translator) {
    Objects.requireNonNull(writer, "'writer' cannot be null.");
    Objects.requireNonNull(translator, "'translator' cannot be null.");
    this.writer = writer;
    this.translator = translator;
  }

  private String generateContentCards(List<Content> contents, Locale locale) {
    final String noteTranslation =
        translator.translate(locale, "html_content_cards_note").orElse("Note");
    final String yearTranslation =
        translator.translate(locale, "html_content_cards_year").orElse("Year");
    return contents.stream()
        .map(
            content ->
                CONTENT_CARD_TEMPLATE.formatted(
                    content.title(),
                    content.title(),
                    translator.translate(locale, content.contentType().tagTranslation).orElse(""),
                    content.urlImage(),
                    content.title(),
                    noteTranslation,
                    content.rating(),
                    yearTranslation,
                    content.year()))
        .collect(Collectors.joining("\n"));
  }

  private String generateHead(Locale locale) {
    final String pageTitleTranslation =
        translator.translate(locale, "page_title").orElse("7 days of code Java");
    return HEAD.formatted(pageTitleTranslation, pageTitleTranslation);
  }

  public void generate(List<Content> contents, Locale locale) throws IOException {
    Objects.requireNonNull(contents, "'contents' cannot be null.");
    Objects.requireNonNull(locale, "'locale' cannot be null.");
    final String movieCards = generateContentCards(contents, locale);
    final String body = BODY_TEMPLATE.formatted(movieCards);
    final String head = generateHead(locale);
    final String html = HTML_TEMPLATE.formatted(head, body);
    writer.write(html);
  }
}
