package com.github.savitoh;

import java.io.IOException;
import java.util.List;

public final class Main {

  private Main() {

  }

  /**
   * Main method responsible for initialize the app.
   *
   * @param args execution args
   * @throws IOException
   * @throws InterruptedException
   */
  public static void main(final String[] args) throws IOException,
      InterruptedException {
    final String uri = "https://imdb-api.com/en/API/Top250Movies/";
    final String apiKey = System.getenv("IMDB_API_KEY");
    final ImdbApiClient imdbApiClient = new ImdbApiClient(uri, apiKey);

    final String top2500Movies = imdbApiClient.getTop250Movies();
    final List<Movie> movies = new ParserJsonMovies(top2500Movies).parse();
    System.out.println("Top 250 Movies \n" + movies);
  }
}
