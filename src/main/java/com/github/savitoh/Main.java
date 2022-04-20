package com.github.savitoh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ImdbClient imdbClient = new ImdbClient();
        final String top2500Movies = imdbClient.getTop250Movies();
        final ParserJsonMovies parserJsonMovies = new ParserJsonMovies(top2500Movies);
        System.out.println(top2500Movies);
    }
}