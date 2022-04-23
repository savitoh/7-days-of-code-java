package com.github.savitoh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;

class HtmlGeneratorTest {

    @TempDir
    private Path tempDir;

    private Path tempMoviesHtml;

    @BeforeEach
    void setUp() {
        this.tempMoviesHtml = tempDir.resolve("movies_test.html");
    }

    @Test
    void Should_ThrowNPE_When_InitializeWithWriterNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new HtmlGenerator(null));

        assertEquals("'writer' cannot be null.", exception.getMessage());
    }

    @Test
    void Should_ThrowNPE_When_GenerateHtmlWithMoviesNull() throws IOException {
        final var writer = new FileWriter(this.tempMoviesHtml.toFile());
        final var htmlGenerator = new HtmlGenerator(writer);


        Exception exception = assertThrows(NullPointerException.class, () -> htmlGenerator.generate(null));
        assertEquals("'movies' cannot be null.", exception.getMessage());
    }

    @Test
    void Should_GenerateHtmlWithMultiplesMovies_When_MoviesArgumentsHasMoreOneElement() throws IOException {
        final var writer = new FileWriter(this.tempMoviesHtml.toFile());
        final var htmlGenerator = new HtmlGenerator(writer);
        final var movies = of(
                new Movie("Harry Potter",
                        "https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw",
                        9.0,
                        2001
                )
        );


        htmlGenerator.generate(movies);
        writer.close();


        final String htmlContent = Files.readString(tempMoviesHtml);
        final String htmlContentExpected = """
                <!doctype html>
                    <html lang="en-us">
                                <head>
                            <meta charset="utf-8">
                            <meta name="HandheldFriendly" content="True">
                            <meta name="description" content="7 days of code - Java (IMDB Challenge)">
                            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                        </head>
                                
                        <body>
                        <main>
                            <div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
                        <h4 class="card-header">Harry Potter</h4>
                        <div class="card-body">
                            <img class="card-img" src="https://play-lh.googleusercontent.com/SF5BMT_IsoF7GBl4USjTr4CrNvXkFClA26qvzyKX6chRdGaXr6JDvnTVqO3wv2EF161VC2jD80YTedD-6HI=w200-h300-rw" alt="Harry Potter">
                            <p class="card-text mt-2">Nota: 9.0 - Ano: 2001</p>
                        </div>
                    </div>
                        </main>
                   </body>
                    </html>
                """.stripIndent();
        assertFalse(htmlContent.isBlank());
        assertEquals(htmlContentExpected.strip(), htmlContent.strip());
    }

    @Test
    void Should_GenerateHtmlWithOutMovies_When_MoviesArgumentsHasEmpty() throws IOException {
        final var writer = new FileWriter(this.tempMoviesHtml.toFile());
        final var htmlGenerator = new HtmlGenerator(writer);


        htmlGenerator.generate(List.of());
        writer.close();


        final String htmlContent = Files.readString(tempMoviesHtml);
        final String htmlContentExpected = """
                <!doctype html>
                    <html lang="en-us">
                                <head>
                            <meta charset="utf-8">
                            <meta name="HandheldFriendly" content="True">
                            <meta name="description" content="7 days of code - Java (IMDB Challenge)">
                            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                        </head>

                        <body>
                        <main>
                           \s
                        </main>
                   </body>
                    </html>""";
        assertFalse(htmlContent.isBlank());
        assertEquals(htmlContentExpected.strip(), htmlContent.strip());
    }

}