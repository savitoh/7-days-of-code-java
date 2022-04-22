package com.github.savitoh;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        final ImdbClient imdbClient = new ImdbClient();
        final String top2500Movies = imdbClient.getTop250Movies();
        final List<Movie> movies = new ParserJsonMovies(top2500Movies).parse();
        System.out.println("Top 250 Movies \n" + movies);
    }
}