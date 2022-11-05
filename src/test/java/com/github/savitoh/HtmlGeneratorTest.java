package com.github.savitoh;

import static org.junit.jupiter.api.Assertions.*;

import com.github.savitoh.content.Content;
import com.github.savitoh.helpers.mocks.MockDummyTranslator;
import com.github.savitoh.imdb.Movie;
import com.github.savitoh.internationalization.ResourceBundleTranslator;
import com.github.savitoh.internationalization.Translator;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HtmlGeneratorTest {

  @TempDir private Path tempDir;

  private Path tempMoviesHtml;

  private final Translator translator = new ResourceBundleTranslator();

  @BeforeEach
  void setUp() {
    this.tempMoviesHtml = tempDir.resolve("movies_test.html");
  }

  @Test
  void Should_ThrowNPE_When_InitializeWithWriterNull() {
    Exception exception =
        assertThrows(NullPointerException.class, () -> new HtmlGenerator(null, translator));

    assertEquals("'writer' cannot be null.", exception.getMessage());
  }

  @Test
  void Should_ThrowNPE_When_InitializeWithTranslatorNull() throws IOException {
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());

    Exception exception =
        assertThrows(NullPointerException.class, () -> new HtmlGenerator(writer, null));

    assertEquals("'translator' cannot be null.", exception.getMessage());
  }

  @Test
  void Should_ThrowNPE_When_GenerateHtmlWithMoviesNull() throws IOException {
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());

    final var htmlGenerator = new HtmlGenerator(writer, translator);

    Exception exception =
        assertThrows(NullPointerException.class, () -> htmlGenerator.generate(null, Locale.US));
    assertEquals("'contents' cannot be null.", exception.getMessage());
  }

  @Test
  void Should_GenerateHtmlWithCardsTranslateToBr_When_NotEmptyContentsAndBrLocale()
      throws IOException {
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());
    final var htmlGenerator = new HtmlGenerator(writer, translator);
    final List<Content> movies =
        List.of(
            new Movie(
                "Harry Potter",
                "https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw",
                "9.0",
                "2001"));

    htmlGenerator.generate(movies, Locale.forLanguageTag("pt-BR"));
    writer.close();

    final String htmlContent = Files.readString(tempMoviesHtml);
    final String htmlContentExpected =
        """
                <!doctype html>
                    <html lang="en-us">
                                <head>
                            <title>7 dias de código JAVA</title>
                            <meta charset="utf-8">
                            <meta name="HandheldFriendly" content="True">
                            <meta name="description" content="7 dias de código JAVA">
                            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                            <script>
                              $(function () {
                                $('[data-toggle="tooltip"]').tooltip()
                              })
                            </script>
                        </head>

                           <body>
                        <main>
                        <div class="container-fluid">
                          <div class="row">
                                <div class="col-6 col-md-4 col-lg-3 mb-4">
                      <div class="card text-white bg-dark mb-3"
                           style="max-width: 18rem;">
                          <h4 class="card-header"
                              style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;"
                              data-toggle="tooltip" data-placement="right" title="Harry Potter">
                            Harry Potter
                          </h4>
                          <div class="card-body">
                              <h6 class="card-subtitle mb-2 text-muted">FILMES</h6>
                              <img class="card-img" src="https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw" alt="Harry Potter">
                              <p class="card-text mt-2">Nota: 9.0 - Ano: 2001</p>
                          </div>
                      </div>
                    </div>

                          </div>
                        </div>
                        </main>
                   </body>

                    </html>
                """
            .stripIndent();
    assertFalse(htmlContent.isBlank());
    assertEquals(htmlContentExpected.strip(), htmlContent.strip());
  }

  @Test
  void Should_GenerateHtmlWithOutCardsTranslateToBr_When_EmptyContentsAndBrLocale()
      throws IOException {
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());
    final var htmlGenerator = new HtmlGenerator(writer, translator);

    htmlGenerator.generate(List.of(), Locale.forLanguageTag("pt-BR"));
    writer.close();

    final String htmlContent = Files.readString(tempMoviesHtml);
    final var htmlContentExpected =
        """
                <!doctype html>
                    <html lang="en-us">
                                <head>
                            <title>7 dias de código JAVA</title>
                            <meta charset="utf-8">
                            <meta name="HandheldFriendly" content="True">
                            <meta name="description" content="7 dias de código JAVA">
                            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                            <script>
                              $(function () {
                                $('[data-toggle="tooltip"]').tooltip()
                              })
                            </script>
                        </head>

                           <body>
                        <main>
                        <div class="container-fluid">
                          <div class="row">
                           \s
                          </div>
                        </div>
                        </main>
                   </body>

                    </html>"""
            .stripIndent();
    assertFalse(htmlContent.isBlank());
    assertEquals(htmlContentExpected.strip(), htmlContent.strip());
  }

  @Test
  void Should_GenerateHtmlWithCardsTranslateToEn_When_NotEmptyContentsAndLocaleNotFoundInResources()
      throws IOException {
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());
    final var htmlGenerator = new HtmlGenerator(writer, translator);
    final List<Content> movies =
        List.of(
            new Movie(
                "Harry Potter",
                "https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw",
                "9.0",
                "2001"));

    htmlGenerator.generate(movies, Locale.forLanguageTag("de-DE"));
    writer.close();

    final String htmlContent = Files.readString(tempMoviesHtml);
    final String htmlContentExpected =
        """
                    <!doctype html>
                        <html lang="en-us">
                                    <head>
                                <title>7 days of code Java</title>
                                <meta charset="utf-8">
                                <meta name="HandheldFriendly" content="True">
                                <meta name="description" content="7 days of code Java">
                                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                                <script>
                                  $(function () {
                                    $('[data-toggle="tooltip"]').tooltip()
                                  })
                                </script>
                            </head>

                               <body>
                            <main>
                            <div class="container-fluid">
                              <div class="row">
                                    <div class="col-6 col-md-4 col-lg-3 mb-4">
                          <div class="card text-white bg-dark mb-3"
                               style="max-width: 18rem;">
                              <h4 class="card-header"
                                  style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;"
                                  data-toggle="tooltip" data-placement="right" title="Harry Potter">
                                Harry Potter
                              </h4>
                              <div class="card-body">
                                  <h6 class="card-subtitle mb-2 text-muted">MOVIE</h6>
                                  <img class="card-img" src="https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw" alt="Harry Potter">
                                  <p class="card-text mt-2">Note: 9.0 - Year: 2001</p>
                              </div>
                          </div>
                        </div>

                              </div>
                            </div>
                            </main>
                       </body>

                        </html>
                    """
            .stripIndent();
    assertFalse(htmlContent.isBlank());
    assertEquals(htmlContentExpected.strip(), htmlContent.strip());
  }

  @Test
  void Should_GenerateHtmlWithOutCardsTranslateToEn_When_EmptyContentsAndLocaleNotFoundInResources()
      throws IOException {
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());
    final var htmlGenerator = new HtmlGenerator(writer, translator);

    htmlGenerator.generate(List.of(), Locale.forLanguageTag("de-DE"));
    writer.close();

    final String htmlContent = Files.readString(tempMoviesHtml);
    final String htmlContentExpected =
        """
                    <!doctype html>
                        <html lang="en-us">
                                    <head>
                                <title>7 days of code Java</title>
                                <meta charset="utf-8">
                                <meta name="HandheldFriendly" content="True">
                                <meta name="description" content="7 days of code Java">
                                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                                <script>
                                  $(function () {
                                    $('[data-toggle="tooltip"]').tooltip()
                                  })
                                </script>
                            </head>

                               <body>
                            <main>
                            <div class="container-fluid">
                              <div class="row">
                               \s
                              </div>
                            </div>
                            </main>
                       </body>

                        </html>"""
            .stripIndent();
    assertFalse(htmlContent.isBlank());
    assertEquals(htmlContentExpected.strip(), htmlContent.strip());
  }

  @Test
  void Should_GenerateHtmlWithCardsTranslateToEn_When_NotEmptyContentsAndTranslatorDoenstTranslate()
      throws IOException {
    final Translator dummyTranslator = new MockDummyTranslator();
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());
    final var htmlGenerator = new HtmlGenerator(writer, dummyTranslator);
    final List<Content> movies =
        List.of(
            new Movie(
                "Harry Potter",
                "https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw",
                "9.0",
                "2001"));

    htmlGenerator.generate(movies, Locale.forLanguageTag("pt-BR"));
    writer.close();

    final String htmlContent = Files.readString(tempMoviesHtml);
    final String htmlContentExpected =
        """
                        <!doctype html>
                            <html lang="en-us">
                                        <head>
                                    <title>7 days of code Java</title>
                                    <meta charset="utf-8">
                                    <meta name="HandheldFriendly" content="True">
                                    <meta name="description" content="7 days of code Java">
                                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                                    <script>
                                      $(function () {
                                        $('[data-toggle="tooltip"]').tooltip()
                                      })
                                    </script>
                                </head>

                                   <body>
                                <main>
                                <div class="container-fluid">
                                  <div class="row">
                                        <div class="col-6 col-md-4 col-lg-3 mb-4">
                              <div class="card text-white bg-dark mb-3"
                                   style="max-width: 18rem;">
                                  <h4 class="card-header"
                                      style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;"
                                      data-toggle="tooltip" data-placement="right" title="Harry Potter">
                                    Harry Potter
                                  </h4>
                                  <div class="card-body">
                                      <h6 class="card-subtitle mb-2 text-muted"></h6>
                                      <img class="card-img" src="https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw" alt="Harry Potter">
                                      <p class="card-text mt-2">Note: 9.0 - Year: 2001</p>
                                  </div>
                              </div>
                            </div>

                                  </div>
                                </div>
                                </main>
                           </body>

                            </html>
                        """
            .stripIndent();
    assertFalse(htmlContent.isBlank());
    assertEquals(htmlContentExpected.strip(), htmlContent.strip());
  }

  @Test
  void Should_GenerateHtmlWithOutCardsTranslateToEn_When_EmptyContentsAndTranslatorDoenstTranslate()
      throws IOException {
    final Translator dummyTranslator = new MockDummyTranslator();
    final var writer = new FileWriter(this.tempMoviesHtml.toFile());
    final var htmlGenerator = new HtmlGenerator(writer, dummyTranslator);

    htmlGenerator.generate(List.of(), Locale.forLanguageTag("pt-BR"));
    writer.close();

    final String htmlContent = Files.readString(tempMoviesHtml);
    final String htmlContentExpected =
        """
                        <!doctype html>
                            <html lang="en-us">
                                        <head>
                                    <title>7 days of code Java</title>
                                    <meta charset="utf-8">
                                    <meta name="HandheldFriendly" content="True">
                                    <meta name="description" content="7 days of code Java">
                                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                                    <script>
                                      $(function () {
                                        $('[data-toggle="tooltip"]').tooltip()
                                      })
                                    </script>
                                </head>

                                   <body>
                                <main>
                                <div class="container-fluid">
                                  <div class="row">
                                   \s
                                  </div>
                                </div>
                                </main>
                           </body>

                            </html>"""
            .stripIndent();
    assertFalse(htmlContent.isBlank());
    assertEquals(htmlContentExpected.strip(), htmlContent.strip());
  }
}
